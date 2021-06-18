package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
//@ViewScoped
@HandlingExpectedErrors
public abstract class BaseViewSubQuery<P, T, I extends Serializable, 
		B extends BaseService<T, I, ? extends BaseRepository<T, I>>>
		extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pagina listar. */
	private String pageList;

	/** The business controller. */
	@Inject
	private B service;

	@Inject
	private P entityParent;

	/** The lista entity. */
	private List<T> listEntity;
	
	/** The entity. */
	@Inject
	private T entity;
	
	/**
	 * Instantiates a new base view sub consulta.
	 *
	 * @param pageList the pagina listar
	 */
	public BaseViewSubQuery(String pageList){
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
	 * Vincular.
	 *
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	public String link(T entity){
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return null;
		}
		dialogClose(entity);
		
		return "";
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
	 * Pega o the entity pai.
	 *
	 * @return o the entity pai
	 */
	public P getEntityPai() {
		return this.entityParent;
	}

	/**
	 * Atribui o the entity pai.
	 *
	 * @param entityParent
	 *            o novo the entity pai
	 */
	public void setEntityPai(P entityParent) {
		this.entityParent = entityParent;
	}

	/**
	 * Pega o the business controller.
	 *
	 * @return o the business controller
	 */
	public B getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.BaseViewController#preProcessPDF(java.lang.Object, java.lang.String)
	 */
	public void preProcessPDF(Object document, String tituloRelatorio)
			throws IOException, BadElementException, DocumentException {
		pdfUtils.preProcessaPDF(document, tituloRelatorio);
	}

	/**
	 * Dialogo.
	 *
	 * @param entityParent
	 *            the entity pai
	 * @param entity
	 *            the entity
	 */
	public void dialog(P entityParent, T entity) {
		setEntityPai(entityParent);
		setEntity(entity);

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		//options.put("closable", true);
		// options.put("width", 400);
		// options.put("height", 400);
		// options.put("contentWidth", 640);
		// options.put("contentHeight", "auto");

		PrimeFaces.current().dialog().openDynamic(getPageList(), options, null);
	}
	
	/**
	 * On dialogo retorno.
	 *
	 * @param event
	 *            the event
	 */
	public void onDialogReturn(SelectEvent<T> event) {
		if (event.getObject() == null) {
			return;
		}
		setEntity((T) event.getObject());		
	}

	/**
	 * Dialogo fechar.
	 *
	 * @param entity
	 *            the entity
	 */
	public void dialogClose(T entity) {
		PrimeFaces.current().dialog().closeDynamic(entity);
	}
	
	/**
	 * Dialogo mensagem.
	 *
	 * @param mensagem
	 *            the mensagem
	 */
	protected void dialogMessage(String mensagem) {
		addMessageInfoDialog(mensagem);
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
	
}
