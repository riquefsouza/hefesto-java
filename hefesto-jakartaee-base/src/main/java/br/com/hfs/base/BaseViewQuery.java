/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
//@ViewScoped
@HandlingExpectedErrors
public abstract class BaseViewQuery<T, I extends Serializable, 
	B extends BaseService<T, I, ? extends BaseRepository<T, I>>> extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String pageList;
	
	@Inject
	private B service;

	private List<T> listEntity;

	@Inject
	private T entity;
	
	/**
	 * Instantiates a new base view cadastro.
	 *
	 * @param pageList
	 *            the pagina listar
	 */
	public BaseViewQuery(String pageList){
		super();		
		this.pageList = pageList;
	}

	protected void updateDataTableList() {
		this.listEntity = service.findAll();
	}

	public String getPageList() {
		return pageList;
	}

	public String cancel() {
		return getDesktopPage();
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(T entidade) {
		this.entity = entidade;
	}

	public List<T> getListEntity() {
		return this.listEntity;
	}

	public void setListEntity(List<T> listEntity) {
		this.listEntity = listEntity;
	}

	public B getService() {
		return service;
	}

}
