class EditAdmUser extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admUserView');	
	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();		
	}
	
}

$(function() {
	const editAdmUser = new EditAdmUser();
	
	$('#btnCancel').click(editAdmUser.btnCancelClick.bind(editAdmUser));
	
});
