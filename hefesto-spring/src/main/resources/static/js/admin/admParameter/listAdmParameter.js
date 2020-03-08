class ListAdmParameter extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._urlApiServer = this._urlResourceServer + "/api/v1/admParameter";
		
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmParameter');
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameter = $('#formListAdmParameter');
		
		this.buildGetPages(this._urlApiServer, this._authToken, this._formTitle, this._tableList, 
				this._dlgDeleteConfirmation, this._messageButtonYes, this._messageButtonNo, 
				$('#admParameter_jsonText'),
				[
					{field: 'idAdmParameterCategory', headerText: 'Parameter Category', sortable: true, filter: false},
					{field: 'code', headerText: 'Parameter', sortable: true, filter: false},
					{field: 'value', headerText: 'Value', sortable: true, filter: false},
					{field: 'description', headerText: 'Description', sortable: true, filter: false}
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
			this._formListAdmParameter.submit();	
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
	const listAdmParameter = new ListAdmParameter();
	
	$('#btnExport').click(listAdmParameter.btnExportClick.bind(listAdmParameter));
	$('#btnAdd').click(listAdmParameter.btnAddClick.bind(listAdmParameter));
	$('#btnEdit').click(listAdmParameter.btnEditClick.bind(listAdmParameter));
	$('#btnDelete').click(listAdmParameter.btnDeleteClick.bind(listAdmParameter));
	$('#btnBack').click(listAdmParameter.btnBackClick.bind(listAdmParameter));
	
	
});
