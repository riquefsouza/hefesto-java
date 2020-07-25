class HFSForgotPassword extends HFSLogin {
	constructor()
	{		
		super();
		
		this._dlgForgotPasswordLabelUsername = $('#dlgForgotPassword_label_username').text();
		this._dlgForgotPasswordUsername = $('#dlgForgotPassword_username');
	}
		
	btnSendClick(event) {
		event.preventDefault();
		
		if (this._dlgForgotPasswordUsername.val().trim().length==0){
			this.showDialogAlertMessage(this._messageAlertWarning, "<strong>" + this._dlgForgotPasswordLabelUsername+ "</strong> "+
					this._emptyStringValidator);
			return;
		}
		
		var sUrl = this.getSystemPage() + "/public/forgotPassword?username=" + this._dlgForgotPasswordUsername.val().trim();

		$.ajax({
			method: "GET",
			url: sUrl,
			dataType: "json",
		    contentType: "application/json; charset=utf-8",								
	        context: this
		})
		.done(function(data, status) {
			this.infoShow("Email Sent!");
			this._dlgForgotPassword.puidialog('hide');
		})
		.fail(function(xhr, textStatus, msg){
			this.dangerShow("An error occured on save: " + xhr.status + " " + xhr.statusText);
	    });

	}
	
}

$(function() {
	const hfsForgotPassword = new HFSForgotPassword();

	$('#dlgForgotPassword_btnSend').click(hfsForgotPassword.btnSendClick.bind(hfsForgotPassword));

});
