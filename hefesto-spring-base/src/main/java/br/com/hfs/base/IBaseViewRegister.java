/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

// TODO: Auto-generated Javadoc
/**
 * The Interface IBaseViewCadastro.
 *
 * @param <T> the generic type
 * @param <I> the generic type
 */
public interface IBaseViewRegister<T, I> extends Serializable {

	/**
	 * Inicia o.
	 */
	void init();
	
	/**
	 * Checks if is modo incluir.
	 *
	 * @return true, if is modo incluir
	 */
	boolean isModoIncluir();
	
	/**
	 * Checks if is modo salvo.
	 *
	 * @return true, if is modo salvo
	 */
	boolean isModoSalvo();

	/**
	 * Gets the pagina listar.
	 *
	 * @return the pagina listar
	 */
	String getPaginaListar();
	
	/**
	 * Gets the pagina editar.
	 *
	 * @return the pagina editar
	 */
	String getPaginaEditar();
	
	/**
	 * Listar.
	 *
	 * @return the model and view
	 */
	ModelAndView listar();
	
	/**
	 * On incluir.
	 *
	 * @return the string
	 */
	ModelAndView incluir();

	/**
	 * On editar.
	 *
	 * @param id the id
	 * @return the string
	 */
	ModelAndView editar(@PathVariable("id") I id);

	/**
	 * Excluir.
	 *
	 * @param id the id
	 * @return the redirect view
	 */
	RedirectView excluir(@PathVariable("id") I id);

	/**
	 * Cancelar edicao.
	 *
	 * @return the string
	 */
	String cancelarEdicao();

	/**
	 * Salvar.
	 *
	 * @param obj the obj
	 * @param result the result
	 * @param attributes the attributes
	 * @return the string
	 */
	RedirectView salvar(@Valid T obj, BindingResult result, RedirectAttributes attributes);

	/**
	 * Cancelar.
	 *
	 * @return the string
	 */
	String cancelar();
	
	/**
	 * Gets the bean.
	 *
	 * @return the bean
	 */
	T getBean();

	/**
	 * Sets the bean.
	 *
	 * @param entidade
	 *            the new bean
	 */
	void setBean(T entidade);

	/**
	 * Gets the lista bean.
	 *
	 * @return the lista bean
	 */
	List<T> getListaBean();

	/**
	 * Sets the lista bean.
	 *
	 * @param listaEntidade
	 *            the new lista bean
	 */
	void setListaBean(List<T> listaEntidade);

}
