class HFSBecomeMember extends HFSLogin {
	constructor()
	{		
		super();
		
		this._dlgBecomeMemberLabelUsername = $('#dlgBecomeMember_label_username').text();
		this._dlgBecomeMemberLabelEmail = $('#dlgBecomeMember_label_email').text();
		this._dlgBecomeMemberLabelNewPassword = $('#dlgBecomeMember_label_newPassword').text();
		this._dlgBecomeMemberLabelConfirmNewPassword = $('#dlgBecomeMember_label_confirmNewPassword').text();
				
		this._dlgBecomeMemberUsername = $('#dlgBecomeMember_username');
		this._dlgBecomeMemberEmail = $('#dlgBecomeMember_email');
		this._dlgBecomeMemberNewPassword = $('#dlgBecomeMember_newPassword');
		this._dlgBecomeMemberConfirmNewPassword = $('#dlgBecomeMember_confirmNewPassword');
		
	}
	
	btnSignUpClick(event) {
		event.preventDefault();
		
		if (this._dlgBecomeMemberUsername.val().trim().length==0){
			this.showDialogAlertMessage(this._messageAlertWarning, "<strong>" + this._dlgBecomeMemberLabelUsername+ "</strong> "+
					this._emptyStringValidator);			
			return;
		}
		
		if (this._dlgBecomeMemberEmail.val().trim().length==0){
			this.showDialogAlertMessage(this._messageAlertWarning, "<strong>" + this._dlgBecomeMemberLabelEmail+ "</strong> "+
					this._emptyStringValidator);				
			return;
		}

		if (this._dlgBecomeMemberNewPassword.val().trim().length==0){
			this.showDialogAlertMessage(this._messageAlertWarning, "<strong>" + this._dlgBecomeMemberLabelNewPassword+ "</strong> "+
					this._emptyStringValidator);			
			return;
		}
				
		if (this._dlgBecomeMemberConfirmNewPassword.val().trim().length==0){
			this.showDialogAlertMessage(this._messageAlertWarning, "<strong>" + this._dlgBecomeMemberLabelConfirmNewPassword+ "</strong> "+
					this._emptyStringValidator)			
			return;
		}
		
		
		var sUrl = this.getSystemPage() + "/public/becomeMember?username=" + this._dlgBecomeMemberUsername.val().trim() +
		"&email=" + this._dlgBecomeMemberEmail.val().trim() +
		"&new=" + this._dlgBecomeMemberNewPassword.val().trim() +
		"&confirm=" + this._dlgBecomeMemberConfirmNewPassword.val().trim();
		
		$.ajax({
			method: "GET",
			url: sUrl,
			dataType: "json",
		    contentType: "application/json; charset=utf-8",								
	        context: this
		})
		.done(function(data, status) {
			this.infoShow(data);
			this._dlgBecomeMember.puidialog('hide');
		})
		.fail(function(xhr, textStatus, msg){
			this.dangerShow("An error occured on save: " + xhr.status + " " + xhr.statusText);
	    });
		
	}
	
}

$(function() {
	const hfsBecomeMember = new HFSBecomeMember();

	$('#dlgBecomeMember_btnSignUp').click(hfsBecomeMember.btnSignUpClick.bind(hfsBecomeMember));

});
