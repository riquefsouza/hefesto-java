class EditAdmProfile extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admProfileView');

		this._bufferSourceUsers = $('#bufferSource_pickListUsers');
		this._bufferTargetUsers = $('#bufferTarget_pickListUsers');
	
		this._sourceUsers = $('#source_pickListUsers');
		this._targetUsers = $('#target_pickListUsers');

		$('#edtSource_pickListUsers').keyup(this.edtSourceUsersKeyUp.bind(this));
		$('#edtTarget_pickListUsers').keyup(this.edtTargetUsersKeyUp.bind(this));
		
		$('#btnRight_pickListUsers').click(this.btnRightUsersClick.bind(this));
		$('#btnAllRight_pickListUsers').click(this.btnAllRightUsersClick.bind(this));
		$('#btnLeft_pickListUsers').click(this.btnLeftUsersClick.bind(this));
		$('#btnAllLeft_pickListUsers').click(this.btnAllLeftUsersClick.bind(this));


		this._bufferSourcePages = $('#bufferSource_pickListPages');
		this._bufferTargetPages = $('#bufferTarget_pickListPages');
	
		this._sourcePages = $('#source_pickListPages');
		this._targetPages = $('#target_pickListPages');

		$('#edtSource_pickListPages').keyup(this.edtSourcePagesKeyUp.bind(this));
		$('#edtTarget_pickListPages').keyup(this.edtTargetPagesKeyUp.bind(this));
		
		$('#btnRight_pickListPages').click(this.btnRightPagesClick.bind(this));
		$('#btnAllRight_pickListPages').click(this.btnAllRightPagesClick.bind(this));
		$('#btnLeft_pickListPages').click(this.btnLeftPagesClick.bind(this));
		$('#btnAllLeft_pickListPages').click(this.btnAllLeftPagesClick.bind(this));

	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();		
	}

	edtSourceUsersKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._sourceUsers[0], 
			this._bufferSourceUsers[0], svalue);		
	}

	edtTargetUsersKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._targetUsers[0], 
			this._bufferTargetUsers[0], svalue);		
	}
	
	btnRightUsersClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._sourceUsers[0], this._targetUsers[0],
			this._bufferSourceUsers[0], this._bufferTargetUsers[0]);
	}
	
	btnAllRightUsersClick(event) {
		event.preventDefault();			
		this.pickListBtnAllClick(this._sourceUsers[0], this._targetUsers[0],
			this._bufferSourceUsers[0], this._bufferTargetUsers[0]);			
	}

	btnLeftUsersClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._targetUsers[0], this._sourceUsers[0],
			this._bufferTargetUsers[0], this._bufferSourceUsers[0]);
	}

	btnAllLeftUsersClick(event) {
		event.preventDefault();
		this.pickListBtnAllClick(this._targetUsers[0], this._sourceUsers[0],
			this._bufferTargetUsers[0], this._bufferSourceUsers[0]);
	}
	
	
	edtSourcePagesKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._sourcePages[0], 
			this._bufferSourcePages[0], svalue);		
	}


	edtTargetPagesKeyUp(event) {
		event.preventDefault();
		var svalue = event.target.value;

		this.pickListEdtKeyUp(this._targetPages[0], 
			this._bufferTargetPages[0], svalue);		
	}
	
	btnRightPagesClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._sourcePages[0], this._targetPages[0],
			this._bufferSourcePages[0], this._bufferTargetPages[0]);
	}
	
	btnAllRightPagesClick(event) {
		event.preventDefault();			
		this.pickListBtnAllClick(this._sourcePages[0], this._targetPages[0],
			this._bufferSourcePages[0], this._bufferTargetPages[0]);			
	}

	btnLeftPagesClick(event) {
		event.preventDefault();
		this.pickListBtnClick(this._targetPages[0], this._sourcePages[0],
			this._bufferTargetPages[0], this._bufferSourcePages[0]);
	}

	btnAllLeftPagesClick(event) {
		event.preventDefault();
		this.pickListBtnAllClick(this._targetPages[0], this._sourcePages[0],
			this._bufferTargetPages[0], this._bufferSourcePages[0]);
	}	
}

$(function() {
	const editAdmProfile = new EditAdmProfile();
	
	$('#btnCancel').click(editAdmProfile.btnCancelClick.bind(editAdmProfile));
	
});
