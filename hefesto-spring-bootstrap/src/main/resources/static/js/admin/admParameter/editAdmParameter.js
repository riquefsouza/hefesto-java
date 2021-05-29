class EditAdmParameter extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admParameterView');
	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();
	}
	
}

$(function() {
	const editAdmParameter = new EditAdmParameter();
	
	$('#btnCancel').click(editAdmParameter.btnCancelClick.bind(editAdmParameter));
	
});
