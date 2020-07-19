/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public interface IBaseViewQuery<T> extends Serializable {

	void init();
	
	String cancel();

	T getBean();

	void setBean(T entity);

	List<T> getListBean();

	void setListBean(List<T> listEntity);

	void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException;

}
