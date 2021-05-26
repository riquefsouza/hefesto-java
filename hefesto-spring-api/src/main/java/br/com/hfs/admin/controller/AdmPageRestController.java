package br.com.hfs.admin.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hfs.admin.controller.dto.AdmPageDTO;
import br.com.hfs.admin.controller.dto.ParamDTO;
import br.com.hfs.admin.controller.form.AdmPageForm;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.base.report.BaseViewReportController;
import br.com.hfs.base.report.ReportParamsDTO;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/admPage")
public class AdmPageRestController extends BaseViewReportController {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private AdmPageService admPageService;

	@GetMapping("/paged")
	@Cacheable(value = "admPageControllerList")
	public Page<AdmPageDTO> listPaged(@RequestParam(required = false) String description, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (description == null) {
			Page<AdmPage> pages = admPageService.findAll(pagination);
			return AdmPageDTO.convert(pages);
		} else {
			Page<AdmPage> pages = admPageService.findByDescriptionLike(description + "%", pagination);
			return AdmPageDTO.convert(pages);
		}
	}
	
	@GetMapping()
	@Cacheable(value = "admPageControllerList")
	public List<AdmPageDTO> listAll(@RequestParam(required = false) String description) {		
		if (description == null) {
			List<AdmPage> pages = admPageService.findAll();
			return AdmPageDTO.convert(pages);
		} else {
			List<AdmPage> pages = admPageService.findByDescriptionLike(description + "%");
			return AdmPageDTO.convert(pages);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "admPageControllerList", allEntries = true)
	public ResponseEntity<AdmPageDTO> save(@RequestBody @Valid AdmPageForm form, UriComponentsBuilder uriBuilder) {
		AdmPage obj = form.convert();
		admPageService.insert(obj);
		
		URI uri = uriBuilder.path("/api/v1/admPage/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new AdmPageDTO(obj));
	}

	@GetMapping("{id}")
	public ResponseEntity<AdmPageDTO> get(@PathVariable Long id) {
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new AdmPageDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "admPageControllerList", allEntries = true)
	public ResponseEntity<AdmPageDTO> update(@PathVariable Long id, @RequestBody @Valid AdmPageForm form){
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			AdmPage page = form.update(id, admPageService);
			return ResponseEntity.ok(new AdmPageDTO(page));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "admPageControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			admPageService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@ApiOperation("Export Report")
	@PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ByteArrayResource> report(HttpServletRequest request, @RequestBody ReportParamsDTO reportParamsDTO) {
		reportParamsDTO.getParams().add(new ParamDTO("PARAMETER1", ""));
		reportParamsDTO.setReportName("AdmPage");
		return exportReport(reportParamsDTO, admPageService.findAll());
	}	
	
}
