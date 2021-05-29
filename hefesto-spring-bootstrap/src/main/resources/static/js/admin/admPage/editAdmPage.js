class EditAdmPage extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admPageView');
	
		this._admPagePickListProfiles = $('#admPage_pickListProfiles');
		this._editAdmPageMessageSourceCaptionProfiles = $('#editAdmPage-message-sourceCaptionProfiles').text();
		this._editAdmPageMessageTargetCaptionProfiles = $('#editAdmPage-message-targetCaptionProfiles').text();

		this.buildPickListProfiles(this._editAdmPageMessageSourceCaptionProfiles, 
				this._editAdmPageMessageTargetCaptionProfiles);

	}

	buildPickListProfiles(messageSourceCaptionProfiles, messageTargetCaptionProfiles){
		/*
		this._admPagePickListProfiles.puipicklist({
	        showSourceControls: false,
	        showTargetControls: false,
	        sourceCaption: messageSourceCaptionProfiles,
	        targetCaption: messageTargetCaptionProfiles,
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
	const editAdmPage = new EditAdmPage();
	
	$('#btnCancel').click(editAdmPage.btnCancelClick.bind(editAdmPage));
	
});
