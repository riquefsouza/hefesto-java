package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
//@ViewScoped
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

	//@Inject
	private T entity;
	
	private Class<T> clazz;
	
	@Inject
	private BaseViewState state;
	
	public BaseViewRegister(Class<T> clazz, String pageList, String pageEdit){
		super();
		this.clazz = clazz;
		
		this.pageList = "/private/" + pageList + ".xhtml?faces-redirect=true";
		this.pageEdit = "/private/" + pageEdit + ".xhtml?faces-redirect=true";
	}
	
	protected void updateDataTableList() {
		this.listEntity = service.findAll();
	}

	@SuppressWarnings("unchecked")
	protected boolean beanInSession() {
		state = BaseViewState.getState(getSession());
		
		T bean = (T) getSession().getAttribute(clazz.getSimpleName() + "_bean");
		if (bean!=null) {
			setEntity(bean);
			return true;
		}
		return false;
	}
	
	public String getPageList() {
		return pageList;
	}

	public String getPageEdit() {
		return pageEdit;
	}
	
	protected String onInsert(T entity) {
		this.state.insertMode(getSession());
		
		getSession().setAttribute(clazz.getSimpleName() + "_bean", entity);
		
		setEntity(entity);

		return getPageEdit();
	}

	public String onEdit(T entity) {
		this.state.editMode(getSession());
		
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return "";
		}
		
		getSession().setAttribute(clazz.getSimpleName() + "_bean", entity);
		
		setEntity(entity);
		return getPageEdit();
	}
	
	public String onVisualize(T entity) {
		this.state.viewMode(getSession());
		
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
	        
	        getSession().removeAttribute(clazz.getSimpleName() + "_bean");
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
			
			if (state.isInsertMode()){
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
			
			this.state.saveMode(getSession(), true);
			
		} catch (Exception e) {
			this.state.saveMode(getSession(), false);
			
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

	public B getService() {
		return service;
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

	public BaseViewState getState() {
		return state;
	}
	
}
