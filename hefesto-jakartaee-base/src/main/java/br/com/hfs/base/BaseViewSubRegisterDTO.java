package br.com.hfs.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

public abstract class BaseViewSubRegisterDTO<P, T, I extends Serializable, B extends IBaseCrud<T, I>> 
	extends BaseViewController implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pagina editar. */
	private String pageEdit;

	/** The business controller. */
	@Inject
	private B service;

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
	public BaseViewSubRegisterDTO(String pageEdit) {
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

	/**
	 * Excluir.
	 *
	 * @param entity
	 *            the entity
	 * @param contemErro
	 *            the contem erro
	 * @param mensagemErro
	 *            the mensagem erro
	 */
	public void delete(T entity, String contemErro, String mensagemErro) {
		if (entity == null) {
			generateErrorMessage(SELECT_RECORD);
			return;
		}
		try {
			service.delete(entity);
		} catch (Exception e) {
			if (e.getCause().toString().contains(contemErro)) {
				generateErrorMessage(e, ERROR_DELETE + mensagemErro);
			} else {
				generateErrorMessage(e, ERROR_DELETE);
			}
			return;
		}
	}

	/**
	 * Excluir.
	 *
	 * @param entity
	 *            the entity
	 */
	public void delete(T entity) {
		this.delete(entity, "", "");
	}

	/**
	 * Salvar.
	 *
	 * @param id
	 *            the id
	 * @param descricao
	 *            the descricao
	 * @param contemErro
	 *            the contem erro
	 * @param mensagemErro
	 *            the mensagem erro
	 * @return the string
	 */
	protected String save(I id, String descricao, String contemErro, String mensagemErro) {			
		if (descricao != null) {
			if (descricao.isEmpty()) {
				generateErrorMessage("Campo 'Descrição' não pode ser vazio.");
				return null;
			}
		}

		try {
			if (id == null) {
				this.service.insert(getEntity());
			} else {
				setEntity((T) this.service.update(getEntity()));
			}
			
			this.saveMode = true;
			
		} catch (Exception e) {
			this.saveMode = false;
			
			if (e.getMessage().contains(contemErro)) {
				generateErrorMessage(e, ERROR_SAVE + mensagemErro);
			} else {
				generateErrorMessage(e, ERROR_SAVE);
			}
			return null;
		}
		//updateDataTableList();
		dialogClose(getEntity());
		
		return "";
	}

	/**
	 * Salvar.
	 *
	 * @param id
	 *            the id
	 * @param descricao
	 *            the descricao
	 * @return the string
	 */
	protected String save(I id, String descricao) {
		return this.save(id, descricao, "", "");
	}

	/**
	 * Salvar.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	protected String save(I id) {
		return this.save(id, null, "", "");
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

		//if (incluir)
			//PrimeFaces.current().executeScript("PF('dialogGarantia').show()");
		//else
			PrimeFaces.current().dialog().openDynamic(getPaginaEditar(), options, null);
			
/*
		<p:dialog widgetVar="dialogGarantia" modal="true" draggable="false" resizable="false">
			<ui:include src="/private/contratoGarantia/editarContratoGarantia2.xhtml" >
				<ui:param name="contratoGarantiaMB" value="#{contratoGarantiaMB}" />
			</ui:include>
		</p:dialog>
		
 */
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
