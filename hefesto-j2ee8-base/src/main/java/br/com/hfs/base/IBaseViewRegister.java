package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public interface IBaseViewRegister <T> extends Serializable {

	void init();
	
	boolean isInsertMode();
	
	boolean isSaveMode();

	String onInsert();

	String onEdit(T entity);

	void prepareToDelete(T entity);

	void delete(T entity);

	String cancelEdition();

	String save();

	String cancel();

	void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException;

}
