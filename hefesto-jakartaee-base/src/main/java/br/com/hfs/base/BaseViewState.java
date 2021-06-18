package br.com.hfs.base;

import java.io.Serializable;

import jakarta.servlet.http.HttpSession;

public class BaseViewState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String NAME_STATE = "state_bean";

	private boolean insertMode;

	private boolean editMode;

	private boolean saveMode;

	private boolean viewMode;

	private boolean showBtnInsert;

	private boolean showBtnDelete;

	private boolean showBtnUpdate;

	private boolean showBtnSave;

	private boolean showBtnClean;
	
	public BaseViewState() {
		super();
		this.insertMode = false;
		this.editMode = false;
		this.saveMode = false;
		this.viewMode = false;
		this.showBtnInsert = true;
		this.showBtnDelete = true;
		this.showBtnUpdate = true;
		this.showBtnSave = true;
		this.showBtnClean = true;
	}
	
	public static BaseViewState getState(HttpSession session) { 
		BaseViewState state = (BaseViewState) session.getAttribute(NAME_STATE);
		if (state == null) {
			state = new BaseViewState();
			session.setAttribute(NAME_STATE, state);
		}
		return state;
	}

	public void insertMode(HttpSession session) {
		this.insertMode = true;
		this.editMode = false;
		this.viewMode = false;
		session.setAttribute(NAME_STATE, this);
	}

	public void editMode(HttpSession session) {
		this.insertMode = false;
		this.editMode = true;
		this.viewMode = false;
		session.setAttribute(NAME_STATE, this);
	}

	public void viewMode(HttpSession session) {
		this.insertMode = false;
		this.editMode = false;
		this.viewMode = true;
		session.setAttribute(NAME_STATE, this);
	}

	public void saveMode(HttpSession session, boolean saveMode) {
		this.saveMode = saveMode;
		session.setAttribute(NAME_STATE, this);
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public boolean isSaveMode() {
		return saveMode;
	}

	public boolean isViewMode() {
		return viewMode;
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

}
