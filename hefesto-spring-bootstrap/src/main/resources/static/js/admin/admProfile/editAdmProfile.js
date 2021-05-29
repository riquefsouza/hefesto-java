class EditAdmProfile extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admProfileView');

		this._admProfilePickListUsers = $('#admProfile_pickListUsers');
		this._editAdmProfileMessageSourceCaptionUsers = $('#editAdmProfile-message-sourceCaptionUsers').text();
		this._editAdmProfileMessageTargetCaptionUsers = $('#editAdmProfile-message-targetCaptionUsers').text();

		this._admProfilePickListPages = $('#admProfile_pickListPages');
		this._editAdmProfileMessageSourceCaptionPages = $('#editAdmProfile-message-sourceCaptionPages').text();
		this._editAdmProfileMessageTargetCaptionPages = $('#editAdmProfile-message-targetCaptionPages').text();

		this.buildPickListUsers(this._editAdmProfileMessageSourceCaptionUsers, 
				this._editAdmProfileMessageTargetCaptionUsers);

		this.buildPickListPages(this._editAdmProfileMessageSourceCaptionPages, 
				this._editAdmProfileMessageTargetCaptionPages);
		
	}
	
	buildPickListUsers(messageSourceCaptionUsers, messageTargetCaptionUsers){
		/*
		this._admProfilePickListUsers.puipicklist({
	        showSourceControls: false,
	        showTargetControls: false,
	        sourceCaption: messageSourceCaptionUsers,
	        targetCaption: messageTargetCaptionUsers,
	        filter: true,
	        filterMatchMode: "contains",
	        responsive: true
	        //sourceData: data
	    });
		*/		
	}

	buildPickListPages(messageSourceCaptionPages, messageTargetCaptionPages){
		/*
		this._admProfilePickListPages.puipicklist({
	        showSourceControls: false,
	        showTargetControls: false,
	        sourceCaption: messageSourceCaptionPages,
	        targetCaption: messageTargetCaptionPages,
	        filter: true,
	        filterMatchMode: "contains",
	        responsive: true
	        //sourceData: data
	    });
		*/		
	}

	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();		
	}
	
}

$(function() {
	const editAdmProfile = new EditAdmProfile();
	
	$('#btnCancel').click(editAdmProfile.btnCancelClick.bind(editAdmProfile));
	
});
