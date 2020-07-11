package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.event.SelectEvent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public interface IBaseViewSubRegister<P, T> extends Serializable {

	void init();
	
	boolean isInsertMode();
	
	boolean isSaveMode();

	void onClean();
	
	void prepareToDelete(T entity, String nameConfirmation);

	void delete(P entityParent, T entity);

	String save();

	T getBean();

	void setBean(T entity);

	void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException;

	void dialogInsert(P entityParent);
	
	void dialogEdit(P entityParent, T entity);
	
	void onDialogReturn(SelectEvent<T> event);
	
	void dialogClose(T entity);
	
	void dialogMessage();
}
