class EditAdmMenu extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admMenuView');		
	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();		
	}
	
}

$(function() {
	const editAdmMenu = new EditAdmMenu();
	
	$('#btnCancel').click(editAdmMenu.btnCancelClick.bind(editAdmMenu));
	
});
