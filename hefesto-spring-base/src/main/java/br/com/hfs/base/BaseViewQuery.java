/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.ModelAndView;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseViewConsulta.
 *
 * @param <T>
 *            the generic type
 * @param <I>
 *            the generic type
 * @param <B>
 *            the generic type
 * @param <L>
 *            the generic type
 */
public abstract class BaseViewQuery<T, I extends Serializable, 
		B extends BaseService<T, I, ? extends JpaRepository<T, I>>>
		extends BaseViewController implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The mapeamento. */
	private String mapeamento;

	/** The pagina listar. */
	private String paginaListar;

	/** The business controller. */
	@Autowired
	private B service;

	/** The lista entidade. */
	private List<T> listaEntidade;

	/** The entidade. */
	private T entidade;

	/**
	 * Instantiates a new base view cadastro.
	 *
	 * @param paginaListar
	 *            the pagina listar
	 */
	public BaseViewQuery(T entidade, String paginaListar, 
			String mapeamento) {
		super();
		this.entidade = entidade;
		this.paginaListar = paginaListar;
		this.mapeamento = mapeamento;
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
	 * Listar.
	 *
	 * @param lista the lista
	 * @return the model and view
	 */
	public ModelAndView listar(List<T> lista) {
		ModelAndView mv = new ModelAndView(paginaListar);
		mv.addObject("listaBean", lista);
		return mv;
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
	 * @param mapeamento
	 *            the new mapeamento
	 */
	public void setMapeamento(String mapeamento) {
		this.mapeamento = mapeamento;
	}
}
