package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
//@ViewScoped
@HandlingExpectedErrors
public abstract class BaseViewQueryDTO<T, I extends Serializable, B extends IBaseCrud<T, I>> 
	extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pagina listar. */
	private String pageList;
	
	/** The business controller. */
	@Inject
	private B service;

	/** The lista entity. */
	private List<T> listEntity;

	/** The entity. */
	@Inject
	private T entity;
	
	/**
	 * Instantiates a new base view cadastro.
	 *
	 * @param pageList
	 *            the pagina listar
	 */
	public BaseViewQueryDTO(String pageList){
		super();		
		this.pageList = pageList + ".xhtml?faces-redirect=true";
	}

	/**
	 * Atualiza lista data table.
	 */
	protected void updateDataTableList() {
		this.listEntity = service.findAll();
	}

	/**
	 * Pega o the pagina listar.
	 *
	 * @return o the pagina listar
	 */
	public String getPageList() {
		return pageList;
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
	 * Pega o the entity.
	 *
	 * @return o the entity
	 */
	public T getEntity() {
		return this.entity;
	}

	/**
	 * Atribui o the entity.
	 *
	 * @param entity
	 *            o novo the entity
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * Pega o the lista entity.
	 *
	 * @return o the lista entity
	 */
	public List<T> getListEntity() {
		return this.listEntity;
	}

	/**
	 * Atribui o the lista entity.
	 *
	 * @param listEntity
	 *            o novo the lista entity
	 */
	public void setListEntity(List<T> listEntity) {
		this.listEntity = listEntity;
	}

	/**
	 * Pega o the business controller.
	 *
	 * @return o the business controller
	 */
	public B getService() {
		return service;
	}


}
