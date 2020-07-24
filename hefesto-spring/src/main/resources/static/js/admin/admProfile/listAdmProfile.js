class ListAdmProfile extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._form = $('#formListAdmProfile');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmProfile').DataTable({
			select: true, 
			responsive: true
		} );
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmProfile = $('#formListAdmProfile');
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
		window.location.href=this._url.replace("View", "View/add");
	}
	
	btnEditClick(event) {
		event.preventDefault();		
		this.dangerHide();
		
		var dataRowSelected = this._tableList.row('.selected').data();
		
		if (dataRowSelected.length > 0) {		
			this._form[0].action+= '/' + dataRowSelected[0];
			this._formListAdmProfile.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}

	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var dataRowSelected = this._tableList.row('.selected').data();
		
		if (dataRowSelected.length > 0) {
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + dataRowSelected[0],
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function(data) {
				this._tableList.row('.selected').remove().draw( false );
        	})
			.fail(function(xhr){
	            //alert("An error occured DELETE: " + xhr.status + " " + xhr.statusText);
				this.dangerShow("An error occured DELETE: " + xhr.status + " " + xhr.statusText);
	        });	
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
	const listAdmProfile = new ListAdmProfile();
	
	$('#btnExport').click(listAdmProfile.btnExportClick.bind(listAdmProfile));
	$('#btnAdd').click(listAdmProfile.btnAddClick.bind(listAdmProfile));
	$('#btnEdit').click(listAdmProfile.btnEditClick.bind(listAdmProfile));
	$('#btnDelete').click(listAdmProfile.btnDeleteClick.bind(listAdmProfile));
	$('#btnBack').click(listAdmProfile.btnBackClick.bind(listAdmProfile));
	
	
});
