/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface IBaseViewConsulta.
 *
 * @param <T>
 *            the generic type
 */
public interface IBaseViewQuery<T> extends Serializable {

	/**
	 * Inicia o.
	 */
	void init();
	
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
