package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
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
public abstract class BaseViewSubRegisterVO<P, T>
		extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pagina editar. */
	private String pageEdit;

	/** The entity pai. */
	@Inject
	private P entityParent;

	/** The entity. */
	@Inject
	private T entity;
	
	/** The modo incluir. */
	private boolean insertMode;
	
	/** The modo salvo. */
	private boolean saveMode;

	/**
	 * Instantiates a new base view sub cadastro.
	 *
	 * @param pageEdit
	 *            the pagina editar
	 */
	public BaseViewSubRegisterVO(String pageEdit) {
		super();
		this.insertMode = false;
		this.saveMode = false;
		
		this.pageEdit = pageEdit + ".xhtml?faces-redirect=true";
	}

	/**
	 * Pega o the pagina editar.
	 *
	 * @return o the pagina editar
	 */
	public String getPaginaEditar() {
		return pageEdit;
	}

	/**
	 * Preparar para delete.
	 *
	 * @param entity
	 *            the entity
	 * @param nomeConfirmacao
	 *            the nome confirmacao
	 */
	public void prepararParaExcluir(T entity, String nomeConfirmacao) {
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return;
		}
		PrimeFaces.current().executeScript("PF('" + nomeConfirmacao + "').show()");
	}

	public void delete(T entity) {
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return;
		}
	}

	protected String save() {
		
		dialogClose(getEntity());
		
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
	 * @param incluir
	 *            the incluir
	 */
	protected void dialog(P entityParent, T entity, boolean incluir) {
		this.insertMode = incluir;
		
		if (!incluir) {
			if (entity == null) {
				generateErrorMessage(SELECT_RECORD);
				return;
			}
		}
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
		PrimeFaces.current().dialog().openDynamic(getPaginaEditar(), options, null);
	}
	
	/**
	 * Dialogo incluir.
	 *
	 * @param entityParent
	 *            the entity pai
	 * @param entity
	 *            the entity
	 */
	public void dialogInsert(P entityParent, T entity) {
		this.dialog(entityParent, entity, true);
	}
	
	/**
	 * Dialogo editar.
	 *
	 * @param entityParent
	 *            the entity pai
	 * @param entity
	 *            the entity
	 */
	public void dialogEdit(P entityParent, T entity) {
		this.dialog(entityParent, entity, false);		
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
		generateInformativeMessage("Successfully registered! " + entity.toString());
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
	 * Checa se é the modo incluir.
	 *
	 * @return o the modo incluir
	 */
	public boolean isInsertMode() {
		return insertMode;
	}
	
	/**
	 * Checa se é the modo salvo.
	 *
	 * @return o the modo salvo
	 */
	public boolean isSaveMode() {
		return saveMode;
	}
	
}
