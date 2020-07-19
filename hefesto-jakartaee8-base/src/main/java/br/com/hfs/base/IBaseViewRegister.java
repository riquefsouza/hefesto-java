package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public interface IBaseViewRegister <T> extends Serializable {

	void init();

	String onInsert();

	String onEdit(T entity);

	void prepareToDelete(T entity);

	void delete(T entity);

	String cancelEdition();

	String save();

	String cancel();

	T getBean();
	
	void setBean(T entity);
	
	List<T> getListBean();
	
	void setListBean(List<T> listEntity);
	
	void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException;

}
