class EditAdmPage extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admPageView');
	
		this._bufferSourceProfiles = $('#bufferSource_pickListProfiles');
		this._bufferTargetProfiles = $('#bufferTarget_pickListProfiles');
	
		this._sourceProfiles = $('#source_pickListProfiles');
		this._targetProfiles = $('#target_pickListProfiles');
		
		$('#edtSource_pickListProfiles').keyup(this.edtSourceProfilesKeyUp.bind(this));
		$('#edtTarget_pickListProfiles').keyup(this.edtTargetProfilesKeyUp.bind(this));
		
		$('#btnRight_pickListProfiles').click(this.btnRightProfilesClick.bind(this));
		$('#btnAllRight_pickListProfiles').click(this.btnAllRightProfilesClick.bind(this));
		$('#btnLeft_pickListProfiles').click(this.btnLeftProfilesClick.bind(this));
		$('#btnAllLeft_pickListProfiles').click(this.btnAllLeftProfilesClick.bind(this));
		
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
