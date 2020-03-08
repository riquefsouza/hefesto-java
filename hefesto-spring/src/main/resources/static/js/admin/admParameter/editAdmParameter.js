class EditAdmParameter extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		//this._dropboxAdmParameterCategory = $('#admParameter_category');
		
		this._saveMethod = this.getPersistedItem("saveMethod");
				
		//this.buildDropboxAdmParameterCategory();
	}
	
	buildDropboxAdmParameterCategory(){
		this._dropboxAdmParameterCategory.puidropdown({
            filter: true,
            style: "width: 100% !important;",
            styleClasss: "form-control"
        });
	}
	
	
	btnCancelClick(event) {
		event.preventDefault();
		
		if (this._saveMethod==="PUT")
			window.location.href=this._url.replace("View/edit", "View");
		else
			window.location.href=this._url.replace("View/add", "View");		
	}
	
}

$(function() {
	const editAdmParameter = new EditAdmParameter();
	
	$('#btnCancel').click(editAdmParameter.btnCancelClick.bind(editAdmParameter));
	
});
