class HFSChangePassword extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this._anchorHomePage = $('#anchorHomePage');
		
		this._formChangePassword = $('#formChangePassword');
		this._dlgChangePassword = $('#dlgChangePassword');
		
		
		this.buildDialogChangePassword(this._dlgChangePassword, this._formChangePassword, 
				this._messageButtonYes, this._messageButtonNo);
	}
	
	btnSaveClick(event) {
		event.preventDefault();
		
		this._dlgChangePassword.puidialog('show');
	}

	buildDialogChangePassword(dlgChangePassword, formChangePassword, 
			messageButtonYes, messageButtonNo){
		this._dlgChangePassword.puidialog({
		    minimizable: false,
		    maximizable: false,
		    resizable: false,
		    responsive: true,
		    minWidth: 200,
		    modal: true,
		    buttons: [{
		            text: messageButtonYes,
		            icon: 'fa-check',
		            click: function() {
		            	formChangePassword.submit();
		            	dlgChangePassword.puidialog('hide');
		            }
		        },
		        {
		            text: messageButtonNo,
		            icon: 'fa-close',
		            click: function() {
		            	dlgChangePassword.puidialog('hide');
		            }
		        }
		    ]
		});	
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
