class ListAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._urlApiServer = this._urlResourceServer + "/api/v1/admParameterCategory";
		
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmParameterCategory');
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameterCategory = $('#formListAdmParameterCategory');
		
		this.buildGetPages(this._urlApiServer, this._authToken, this._formTitle, this._tableList, 
				this._dlgDeleteConfirmation, this._messageButtonYes, this._messageButtonNo, 
				$('#admParameterCategory_jsonText'),
				[
					{field: 'description', headerText: 'Description', sortable: true, filter: false},
					{field: 'order', headerText: 'Order', sortable: true, filter: false}
				]);
		
	}
	
	btnExportClick(event) {
		event.preventDefault();
		
		var sUrl = this._url + "/export";
		sUrl += "?reportType=" + this._cmbReportType.val() + 
				"&forceDownload=" + this._forceDownload[0].checked + 
				"&params=1,2,3";
		
		window.open(sUrl,'_blank');
	}
	
	btnAddClick(event) {
		event.preventDefault();
		
		this.persistItem("saveMethod", "POST");
		window.location.href=this._url.replace("View", "View/add");
	}
	
	btnEditClick(event) {
		event.preventDefault();		
		this.dangerHide();
		
		var dataRowSelected = this._tableList.puidatatable('getSelection');		
		
		if (dataRowSelected.length > 0) {
			this.persistItem("saveMethod", "PUT");
			this._formListAdmParameterCategory.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}

	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var dataRowSelected = this._tableList.puidatatable('getSelection');
		
		if (dataRowSelected.length > 0) {
			this._dlgDeleteConfirmation.puidialog('show');
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}

	btnBackClick(event) {
		event.preventDefault();
		this._anchorHomePage[0].click();
	}
			
}

$(function() {
	const listAdmParameterCategory = new ListAdmParameterCategory();
	
	$('#btnExport').click(listAdmParameterCategory.btnExportClick.bind(listAdmParameterCategory));
	$('#btnAdd').click(listAdmParameterCategory.btnAddClick.bind(listAdmParameterCategory));
	$('#btnEdit').click(listAdmParameterCategory.btnEditClick.bind(listAdmParameterCategory));
	$('#btnDelete').click(listAdmParameterCategory.btnDeleteClick.bind(listAdmParameterCategory));
	$('#btnBack').click(listAdmParameterCategory.btnBackClick.bind(listAdmParameterCategory));
	
	
});
