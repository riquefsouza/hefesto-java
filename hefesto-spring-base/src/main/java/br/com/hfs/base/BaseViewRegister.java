/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseViewCadastro.
 *
 * @param <T>            the generic type
 * @param <I>            the generic type
 * @param <B>            the generic type
 */
public abstract class BaseViewRegister<T, 
	I extends Serializable, 
	B extends BaseService<T, I, ? extends JpaRepository<T, I>>
		> extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The mapeamento. */
	private String mapeamento;
	
	/** The pagina listar. */
	private String paginaListar;
	
	/** The pagina editar. */
	private String paginaEditar;

	@Autowired
	private B service;

	/** The lista entidade. */
	private List<T> listaEntidade;

	/** The entidade. */
	private T entidade;
	
	/** The modo incluir. */
	private boolean modoIncluir;
	
	/** The modo salvo. */
	private boolean modoSalvo;
	
	/** The mostrar btn incluir. */
	private boolean mostrarBtnIncluir;
	
	/** The mostrar btn excluir. */
	private boolean mostrarBtnExcluir;
	
	/** The mostrar btn editar. */
	private boolean mostrarBtnEditar;
	
	/** The mostrar btn salvar. */
	private boolean mostrarBtnSalvar;
	
	/** The mostrar btn limpar. */
	private boolean mostrarBtnLimpar;
	
	/**
	 * Instantiates a new base view cadastro.
	 *
	 * @param entidade the entidade
	 * @param paginaListar            the pagina listar
	 * @param paginaEditar            the pagina editar
	 * @param mapeamento the mapeamento
	 */
	public BaseViewRegister(T entidade, String paginaListar, 
			String paginaEditar, String mapeamento){
		super();
		this.modoIncluir = false;
		this.modoSalvo = false;
		
		this.entidade = entidade;
		this.paginaListar = paginaListar;
		this.paginaEditar = paginaEditar;
		this.mapeamento = mapeamento;
		
		this.mostrarBtnIncluir = true;
		this.mostrarBtnExcluir = true;
		this.mostrarBtnEditar = true;
		this.mostrarBtnSalvar = true;
		this.mostrarBtnLimpar = true;
	}

	/**
	 * Atualiza lista data table.
	 */
	protected void atualizaListaDataTable() {
		this.listaEntidade = StreamSupport.stream(service.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	/**
	 * Pega o the pagina listar.
	 *
	 * @return o the pagina listar
	 */
	public String getPaginaListar() {
		return paginaListar;
	}

	/**
	 * Pega o the pagina editar.
	 *
	 * @return o the pagina editar
	 */
	public String getPaginaEditar() {
		return paginaEditar;
	}
	
	/**
	 * Listar.
	 *
	 * @return the model and view
	 */
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView(paginaListar);
		List<T> lista = service.findAll();
		mv.addObject("listaBean", lista);
		return mv;
	}
	
	/**
	 * Incluir.
	 *
	 * @return the model and view
	 */
	public ModelAndView incluir(T entidade) {
		this.modoIncluir = true;
		setEntidade(entidade);
		ModelAndView mv = new ModelAndView(getPaginaEditar());
		mv.addObject("bean", this.entidade);
		return mv;
	}
	
	/**
	 * Editar.
	 *
	 * @param id the id
	 * @return the model and view
	 */
	public ModelAndView editar(I id) {
		this.modoIncluir = false;
		
		/*
		if (entidade == null) {
			generateErrorMessage(SELECIONAR_REGISTRO);
			return "";
		}
		*/
		
		Optional<T> bean = service.findById(id);		
		ModelAndView mv = new ModelAndView(getPaginaEditar());
		mv.addObject("bean", bean.get());
		setEntidade(bean.get());
		return mv;
	}

	/**
	 * Excluir.
	 *
	 * @param id the id
	 * @param contemErro the contem erro
	 * @param mensagemErro the mensagem erro
	 * @return the string
	 */
	private RedirectView excluir(I id, String contemErro, String mensagemErro) {
		/*
		if (entidade == null) {
			generateErrorMessage(SELECIONAR_REGISTRO);
			return;
		}
		*/
		try {
			Optional<T> bean = service.findById(id);
			service.delete(bean.get());
			atualizaListaDataTable();
		} catch (Exception e) {
	        if (!contemErro.isEmpty() && !mensagemErro.isEmpty()){
	            if (e.getCause().toString().contains(contemErro)) {
	                //generateErrorMessage(e, ERRO_DELETE + mensagemErro);
	                addMessageWarnDialog(mensagemErro);
	            }                
	        } else if (!mensagemErro.isEmpty()){
	            addMessageWarnDialog(mensagemErro);
	        } else {
	            generateErrorMessage(e, ERRO_DELETE);
	        }
	        //return;
		}
			
		return new RedirectView(getMapeamento()+"/listar");
	}

	/**
	 * Excluir.
	 *
	 * @param id the id
	 */
	public RedirectView excluir(I id) {
		return this.excluir(id, "", "");
	}
	
	
	/**
	 * Salvar.
	 *
	 * @param id the id
	 * @param descricao the descricao
	 * @param contemErro the contem erro
	 * @param mensagemErro the mensagem erro
	 * @param obj the obj
	 * @param result the result
	 * @param attributes the attributes
	 * @param fnc the fnc
	 * @return the redirect view
	 */	
	private RedirectView salvar(I id, String descricao, String contemErro, String mensagemErro, 
			@Valid T obj, BindingResult result, RedirectAttributes attributes, Callable<String> fnc) {
		
		
		if (descricao!=null){
			if (descricao.isEmpty()) {
				generateErrorMessage("Campo 'Descrição' não pode ser vazio.");
				if (modoIncluir)
					return new RedirectView(getMapeamento()+"/incluir");
				else
					return new RedirectView(getMapeamento()+"/editar/{id}");
			}
			
			/*
			if (modoIncluir){
				if (this.businessController.existeNovo(descricao)){
					generateErrorMessage("Campo 'Descrição' já existe.");
					return new RedirectView(getMapeamento()+"/incluir");					
				}
			} else {				
				if (this.businessController.existeAntigo(id, descricao)){
					generateErrorMessage("Campo 'Descrição' já existe.");
					return new RedirectView(getMapeamento()+"/editar/{id}");										
				}				
			}
			*/
		}
		
		if (result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			if (modoIncluir)
				return new RedirectView(getMapeamento()+"/incluir");
			else
				return new RedirectView(getMapeamento()+"/editar/{id}");
		}
		
		try {
			if (id == null)
				service.insert(obj);
			else
				service.update(obj);
			
			if (fnc!=null){
				fnc.call();
			}
			
			this.modoSalvo = true;
			
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
		} catch (Exception e) {
			this.modoSalvo = false;
			
			if (e.getMessage().contains(contemErro)) {
				generateErrorMessage(e, ERRO_SALVAR + mensagemErro);
			} else {
				generateErrorMessage(e, ERRO_SALVAR);
			}
			if (modoIncluir)
				return new RedirectView(getMapeamento()+"/incluir");
			else
				return new RedirectView(getMapeamento()+"/editar/{id}");
		}
		atualizaListaDataTable();
		
		return new RedirectView(getMapeamento()+"/listar");
	}
	

	/**
	 * Salvar.
	 *
	 * @param id            the id
	 * @param descricao            the descricao
	 * @param obj the obj
	 * @param result the result
	 * @param attributes the attributes
	 * @return the string
	 */
	protected RedirectView salvar(I id, String descricao,
			@Valid T obj, BindingResult result, RedirectAttributes attributes) {
		return this.salvar(id, descricao, "", "", obj, result, attributes, null);
	}

	/**
	 * Salvar.
	 *
	 * @param id            the id
	 * @param obj the obj
	 * @param result the result
	 * @param attributes the attributes
	 * @return the string
	 */
	protected RedirectView salvar(I id,
			@Valid T obj, BindingResult result, RedirectAttributes attributes) {
		return this.salvar(id, null, "", "", obj, result, attributes, null);
	}

	/**
	 * Cancelar edicao.
	 *
	 * @return the string
	 */
	public String cancelarEdicao() {
		return getPaginaListar();
	}
	
	/**
	 * Cancelar.
	 *
	 * @return the string
	 */
	public String cancelar() {
		return getDesktopPage();
	}

	/**
	 * Pega o the entidade.
	 *
	 * @return o the entidade
	 */
	public T getEntidade() {
		return this.entidade;
	}

	/**
	 * Atribui o the entidade.
	 *
	 * @param entidade
	 *            o novo the entidade
	 */
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	/**
	 * Pega o the lista entidade.
	 *
	 * @return o the lista entidade
	 */
	public List<T> getListaEntidade() {
		return this.listaEntidade;
	}

	/**
	 * Atribui o the lista entidade.
	 *
	 * @param listaEntidade
	 *            o novo the lista entidade
	 */
	public void setListaEntidade(List<T> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

	/**
	 * Pega o the business controller.
	 *
	 * @return o the business controller
	 */
	public B getBusinessController() {
		return service;
	}

	/**
	 * Checa se é the modo incluir.
	 *
	 * @return o the modo incluir
	 */
	public boolean isModoIncluir() {
		return modoIncluir;
	}

	/**
	 * Checa se é the modo salvo.
	 *
	 * @return o the modo salvo
	 */
	public boolean isModoSalvo() {
		return modoSalvo;
	}

	/**
	 * Checa se é the mostrar btn excluir.
	 *
	 * @return o the mostrar btn excluir
	 */
	public boolean isMostrarBtnExcluir() {
		return mostrarBtnExcluir;
	}

	/**
	 * Atribui o the mostrar btn excluir.
	 *
	 * @param mostrarBtnExcluir
	 *            o novo the mostrar btn excluir
	 */
	public void setMostrarBtnExcluir(boolean mostrarBtnExcluir) {
		this.mostrarBtnExcluir = mostrarBtnExcluir;
	}

	/**
	 * Checa se é the mostrar btn editar.
	 *
	 * @return o the mostrar btn editar
	 */
	public boolean isMostrarBtnEditar() {
		return mostrarBtnEditar;
	}

	/**
	 * Atribui o the mostrar btn editar.
	 *
	 * @param mostrarBtnEditar
	 *            o novo the mostrar btn editar
	 */
	public void setMostrarBtnEditar(boolean mostrarBtnEditar) {
		this.mostrarBtnEditar = mostrarBtnEditar;
	}

	/**
	 * Checks if is mostrar btn incluir.
	 *
	 * @return true, if is mostrar btn incluir
	 */
	public boolean isMostrarBtnIncluir() {
		return mostrarBtnIncluir;
	}

	/**
	 * Sets the mostrar btn incluir.
	 *
	 * @param mostrarBtnIncluir
	 *            the new mostrar btn incluir
	 */
	public void setMostrarBtnIncluir(boolean mostrarBtnIncluir) {
		this.mostrarBtnIncluir = mostrarBtnIncluir;
	}

	/**
	 * Checks if is mostrar btn salvar.
	 *
	 * @return true, if is mostrar btn salvar
	 */
	public boolean isMostrarBtnSalvar() {
		return mostrarBtnSalvar;
	}

	/**
	 * Sets the mostrar btn salvar.
	 *
	 * @param mostrarBtnSalvar
	 *            the new mostrar btn salvar
	 */
	public void setMostrarBtnSalvar(boolean mostrarBtnSalvar) {
		this.mostrarBtnSalvar = mostrarBtnSalvar;
	}

	/**
	 * Checks if is mostrar btn limpar.
	 *
	 * @return true, if is mostrar btn limpar
	 */
	public boolean isMostrarBtnLimpar() {
		return mostrarBtnLimpar;
	}

	/**
	 * Sets the mostrar btn limpar.
	 *
	 * @param mostrarBtnLimpar
	 *            the new mostrar btn limpar
	 */
	public void setMostrarBtnLimpar(boolean mostrarBtnLimpar) {
		this.mostrarBtnLimpar = mostrarBtnLimpar;
	}

	/**
	 * Gets the mapeamento.
	 *
	 * @return the mapeamento
	 */
	public String getMapeamento() {
		return mapeamento;
	}

	/**
	 * Sets the mapeamento.
	 *
	 * @param mapeamento the new mapeamento
	 */
	public void setMapeamento(String mapeamento) {
		this.mapeamento = mapeamento;
	}

}
