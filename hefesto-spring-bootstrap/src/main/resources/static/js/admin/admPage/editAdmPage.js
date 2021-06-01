class EditAdmPage extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admPageView');
	
		this._bufferSourceProfiles = $('#admPage_bufferSourceProfiles');
		this._bufferTargetProfiles = $('#admPage_bufferTargetProfiles');
	
		this._sourceProfiles = $('#admPage_sourceProfiles');
		this._targetProfiles = $('#admPage_targetProfiles');
		
		this._editAdmPageMessageSourceCaptionProfiles = $('#editAdmPage-message-sourceCaptionProfiles').text();
		this._editAdmPageMessageTargetCaptionProfiles = $('#editAdmPage-message-targetCaptionProfiles').text();
		
		$('#admPage_edtSourceProfiles').keyup(this.edtSourceProfilesKeyUp.bind(this));
		$('#admPage_edtTargetProfiles').keyup(this.edtTargetProfilesKeyUp.bind(this));
		
		$('#admPage_pickListProfiles_btnRight').click(this.btnRightProfilesClick.bind(this));
		$('#admPage_pickListProfiles_btnAllRight').click(this.btnAllRightProfilesClick.bind(this));
		$('#admPage_pickListProfiles_btnLeft').click(this.btnLeftProfilesClick.bind(this));
		$('#admPage_pickListProfiles_btnAllLeft').click(this.btnAllLeftProfilesClick.bind(this));
		
	}

	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();		
	}
	
	edtSourceProfilesKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._sourceProfiles[0], 
			this._bufferSourceProfiles[0], svalue);		
	}


	edtTargetProfilesKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._targetProfiles[0], 
			this._bufferTargetProfiles[0], svalue);		
	}
	
	btnRightProfilesClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._sourceProfiles[0], this._targetProfiles[0],
			this._bufferSourceProfiles[0], this._bufferTargetProfiles[0]);
	}
	
	btnAllRightProfilesClick(event) {
		event.preventDefault();			
		this.pickListBtnAllClick(this._sourceProfiles[0], this._targetProfiles[0],
			this._bufferSourceProfiles[0], this._bufferTargetProfiles[0]);			
	}

	btnLeftProfilesClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._targetProfiles[0], this._sourceProfiles[0],
			this._bufferTargetProfiles[0], this._bufferSourceProfiles[0]);
	}

	btnAllLeftProfilesClick(event) {
		event.preventDefault();
		this.pickListBtnAllClick(this._targetProfiles[0], this._sourceProfiles[0],
			this._bufferTargetProfiles[0], this._bufferSourceProfiles[0]);
	}
}

$(function() {
	const editAdmPage = new EditAdmPage();
	
	$('#btnCancel').click(editAdmPage.btnCancelClick.bind(editAdmPage));
	
});
