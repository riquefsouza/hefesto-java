package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class BaseViewRegister<T, I extends Serializable, 
		B extends BaseService<T, I, ? extends BaseRepository<T, I>>>
		extends BaseViewController implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String pageList;
	
	private String pageEdit;
	
	@Inject
	private B service;

	private List<T> listEntity;

	@Inject
	private T entity;
	
	private boolean insertMode;
	
	private boolean saveMode;
		
	private boolean viewMode;
	
	private boolean showBtnInsert;
	
	private boolean showBtnDelete;
	
	private boolean showBtnUpdate;
	
	private boolean showBtnSave;
	
	private boolean showBtnClean;
	
	public BaseViewRegister(String pageList, String pageEdit){
		super();
		this.insertMode = false;
		this.saveMode = false;
		this.viewMode = false;
		
		this.pageList = pageList + ".xhtml?faces-redirect=true";
		this.pageEdit = pageEdit + ".xhtml?faces-redirect=true";
			
		this.showBtnInsert = true;
		this.showBtnDelete = true;
		this.showBtnUpdate = true;
		this.showBtnSave = true;
		this.showBtnClean = true;
	}
	
	protected void updateDataTableList() {
		this.listEntity = service.findAll();
	}

	public String getPageList() {
		return pageList;
	}

	public String getPageEdit() {
		return pageEdit;
	}
	
	protected String onInsert(T entity) {
		this.insertMode = true;
		this.viewMode = false;
		
		setEntity(entity);

		return getPageEdit();
	}

	public String onEdit(T entity) {
		this.insertMode = false;
		this.viewMode = false;
		
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return "";
		}
		setEntity(entity);
		return getPageEdit();
	}
	
	public String onVisualize(T entity) {
		this.insertMode = false;
		this.saveMode = false;
		this.viewMode = true;
		
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return "";
		}
		setEntity(entity);
		return getPageEdit();
	}
	
	public void prepareToDelete(T entity) {
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return;
		}
		PrimeFaces.current().executeScript("PF('confirmation').show()");
	}
	
	public boolean delete(T entity, String hasError, String messageError) {
	    if (entity == null) {
	    	generateErrorMessage(SELECT_RECORD);
	        return false;
	    }
	    try {
	        service.delete(entity);
	        updateDataTableList();
	        return true;
	    } catch (Exception e) {
	        if (!hasError.isEmpty() && !messageError.isEmpty()){
	            if (e.getCause().toString().contains(hasError)) {
	            	addMessageWarningDialog(messageError);
	            }                
	        } else if (!messageError.isEmpty()){
	        	addMessageWarningDialog(messageError);
	        } else {
	        	generateErrorMessage(e, ERROR_DELETE);
	        }
	        return false;
	    }
	}
	
	public void delete(T entity) {
		this.delete(entity, "", "");
	}
	
	public String cancelEdition() {
		return getPageList();
	}
	
	protected String save(I id, String description, String fieldName, String hasError, 
			String errorMessage, Callable<String> fnc) {
		
		
		if (description!=null){
			if (description.isEmpty()) {
				generateErrorMessage("Field '"+ fieldName +"' cannot be empty.");
				return null;
			}
			
			if (insertMode){
				if (this.service.thereIsFieldNew("description", description)){
					generateErrorMessage("Field '"+ fieldName +"' already exists.");
					return null;					
				}
			} else {				
				if (this.service.thereIsFieldOld("description", id, description)){
					generateErrorMessage("Field '"+ fieldName +"' already exists.");
					return null;										
				}				
			}
		}
		
		
		try {
			if (id == null) {
				setEntity((T) this.service.insert(getEntity()));
			} else {
				setEntity((T) this.service.update(getEntity()));
			}
			
			if (fnc!=null){
				fnc.call();
			}
			
			this.saveMode = true;
			
		} catch (Exception e) {
			this.saveMode = false;
			
			if (e.getMessage().contains(hasError)) {
				generateErrorMessage(e, ERROR_SAVE + errorMessage);
			} else {
				generateErrorMessage(e, ERROR_SAVE);
			}
			return null;
		}
		
		updateDataTableList();
		return getPageList();
	}
	
	protected String save(I id, String descricao) {
		return this.save(id, descricao, "Description", "", "", null);
	}
	
	protected String save(I id, String descricao, String nomeCampo) {
		return this.save(id, descricao, nomeCampo, "", "", null);
	}
	
	protected String save(I id) {
		return this.save(id, null, null, "", "", null);
	}
	
	protected String save(I id, Callable<String> fnc) {
		return this.save(id, null, null, "", "", fnc);
	}
	
	public String cancel() {
		return getDesktopPage();
	}
	
	public List<T> getListEntity() {
		return listEntity;
	}

	public void setListEntity(List<T> listEntity) {
		this.listEntity = listEntity;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public boolean isShowBtnInsert() {
		return showBtnInsert;
	}

	public void setShowBtnInsert(boolean showBtnInsert) {
		this.showBtnInsert = showBtnInsert;
	}

	public boolean isShowBtnDelete() {
		return showBtnDelete;
	}

	public void setShowBtnDelete(boolean showBtnDelete) {
		this.showBtnDelete = showBtnDelete;
	}

	public boolean isShowBtnUpdate() {
		return showBtnUpdate;
	}

	public void setShowBtnUpdate(boolean showBtnUpdate) {
		this.showBtnUpdate = showBtnUpdate;
	}

	public boolean isShowBtnSave() {
		return showBtnSave;
	}

	public void setShowBtnSave(boolean showBtnSave) {
		this.showBtnSave = showBtnSave;
	}

	public boolean isShowBtnClean() {
		return showBtnClean;
	}

	public void setShowBtnClean(boolean showBtnClean) {
		this.showBtnClean = showBtnClean;
	}

	public B getService() {
		return service;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public boolean isSaveMode() {
		return saveMode;
	}

	public boolean isViewMode() {
		return viewMode;
	}
	
}
