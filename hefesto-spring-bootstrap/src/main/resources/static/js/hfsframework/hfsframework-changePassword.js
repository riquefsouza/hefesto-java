class HFSChangePassword extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this._anchorHomePage = $('#anchorHomePage');
		
		this._formChangePassword = $('#formChangePassword');
		this._dlgChangePassword = $('#dlgChangePassword');		
	}
	
	btnSaveClick(event) {
		event.preventDefault();
		
		this._dlgChangePassword.puidialog('show');
	}

	btnCancelClick(event) {
		event.preventDefault();
		this._anchorHomePage[0].click();
	}

}

$(function() {
	const hfsChangePassword = new HFSChangePassword();
	
	$('#btnSave').click(hfsChangePassword.btnSaveClick.bind(hfsChangePassword));
	$('#btnCancel').click(hfsChangePassword.btnCancelClick.bind(hfsChangePassword));
	
});
