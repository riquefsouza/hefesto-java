package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.base.report.BaseReportImpl;
import br.com.hfs.base.report.BaseViewReportController;
import br.com.hfs.base.report.IBaseReport;
import br.com.hfs.base.report.IBaseViewReport;
import br.com.hfs.base.report.ReportGroupVO;

public abstract class BaseViewRegister<T, 
	I extends Serializable, 
	B extends BaseService<T, I, ? extends JpaRepository<T, I>>
		> extends BaseViewReportController implements IBaseViewReport {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Autowired
	private B service;

	private String listPage;

	private String editPage;
	
	private String reportName;
	
	private Boolean forceDownload;
	
	private Class<T> clazz;
	
	public BaseViewRegister(String listPage, String editPage, String reportName, Class<T> clazz) {
		super();
		this.forceDownload = false;
		
		log = LoggerFactory.getLogger(BaseViewRegister.class);
		
		this.listPage = listPage;
		this.editPage = editPage;
		this.reportName = reportName;
		this.clazz = clazz;
	}

	@GetMapping
	public ModelAndView list(T bean) {
		Optional<ModelAndView> mv = getPage(getListPage());
		if (mv.isPresent()) {
			mv.get().addObject("bean", bean);
			
			List<T> lista = service.findAll();
			mv.get().addObject("listBean", lista);
		}
		return mv.get();
	}

	@GetMapping("/add")
	public ModelAndView add(T bean) throws Exception {
		Optional<ModelAndView> mv = getPage(getEditPage());
		
		if (mv.isPresent()) {
			bean = clazz.getDeclaredConstructor().newInstance();
			mv.get().addObject("bean", bean);
		}
		
		return mv.get();
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable I id) {
		Optional<ModelAndView> mv = getPage(getEditPage());
		
		Optional<T> obj = service.findById(id);
		mv.get().addObject("bean", obj.get());
		
		return mv.get();
	}

	@PostMapping
	public ModelAndView save(@Valid @ModelAttribute T bean, 
			BindingResult result, RedirectAttributes attributes) {
		Optional<ModelAndView> mv = getPage(this.listPage);

		if (result.hasErrors()){
			mv.get().setViewName(this.editPage);
			return mv.get();
		}
		
		try {
			
			//if (bean.getId()==null) 
				//this.service.insert(bean);
			//else
				this.service.update(bean);
			
			mv.get().addObject("bean", bean);
			
			List<T> lista = service.findAll();
			mv.get().addObject("listBean", lista);
			
		} catch (RestClientException e) {
			this.showDangerMessage(mv.get(), e);
			return mv.get();
		}
		
		return mv.get();
	}

	protected ModelAndView saveCallableBefore(@Valid @ModelAttribute T bean, 
			BindingResult result, RedirectAttributes attributes, Callable<String> fnc) {
		Optional<ModelAndView> mv = getPage(this.listPage);

		if (result.hasErrors()){
			mv.get().setViewName(this.editPage);
			return mv.get();
		}
		
		try {
			
			if (fnc!=null){
				fnc.call();
			}
			
			//if (bean.getId()==null) 
				//this.service.insert(bean);
			//else
				this.service.update(bean);
			
			mv.get().addObject("bean", bean);
			
		} catch (Exception e) {
			this.showDangerMessage(mv.get(), e);
			return mv.get();
		}
		
		return mv.get();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<T> delete(@PathVariable I id) {
		
		Optional<T> obj = service.findById(id);

		if (!obj.isPresent()) {
			log.info("DELETE NOT FOUND: " + id);
			return ResponseEntity.notFound().build();
		}

		service.delete(obj.get());

		return ResponseEntity.ok(obj.get());
		
		//return getListPage();
	}

	@ResponseBody
	@GetMapping(value = "/export")	
	public String export(HttpServletResponse response,
			@RequestParam(name = "reportType", required = true, defaultValue = "PDF") String reportType,
			@RequestParam(name = "forceDownload", required = true, defaultValue = "false") String forceDownload,
			@RequestParam(name = "params", required = false) List<String> params) {
		
		Map<String, Object> params1 = this.getParametros();
		params1.put("PARAMETER1", "");

		IBaseReport report = new BaseReportImpl(reportName.isEmpty() ? "report" : reportName);
		this.setReportType(reportType);	
		this.export(report, service.findAll(), 
					params1, Boolean.parseBoolean(forceDownload));
		return "";
	}
	
	@ModelAttribute("listReportType")
	public List<ReportGroupVO> getListReportType() {
		return super.getListReportType();
	}

	public String getListPage() {
		return listPage;
	}

	public String getEditPage() {
		return editPage;
	}

	public Boolean getForceDownload() {
		return forceDownload;
	}

	public void setForceDownload(Boolean forceDownload) {
		this.forceDownload = forceDownload;
	}

}
