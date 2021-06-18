package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.primefaces.event.SelectEvent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public interface IBaseViewSubQuery<P, T> extends Serializable {

	void init();
	
	void onClean();

	String link(T entity);
	
	T getBean();

	void setBean(T entity);

	List<T> getListBean();

	void setListBean(List<T> listaEntity);
	
	void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException;

	void dialog(P entityParent, T entity);
	
	void onDialogReturn(SelectEvent<T> event);
	
	void dialogClose(T entity);
	
	void dialogMessage();
	
}
