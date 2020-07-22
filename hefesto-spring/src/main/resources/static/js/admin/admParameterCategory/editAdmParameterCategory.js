class EditAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._page = $('#admParameterCategoryView');
	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._page[0].click();
	}
	
}

$(function() {
	const editAdmParameterCategory = new EditAdmParameterCategory();
	
	$('#btnCancel').click(editAdmParameterCategory.btnCancelClick.bind(editAdmParameterCategory));
	
});
