class ListAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmParameterCategory').DataTable( {select: true} );
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameterCategory = $('#formListAdmParameterCategory');

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
		
		//var dataRowSelected = this._tableList.row('.selected').data()[0];
		var dataRowSelected = this._tableList.row('.selected').data();
		
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
		alert('OLHA');
		//var dataRowSelected = this._tableList.puidatatable('getSelection');
		
		//if (dataRowSelected.length > 0) {
			//this._dlgDeleteConfirmation.puidialog('show');
		//} else {
			this.dangerShow(this._messageSelectTable);
		//}
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
