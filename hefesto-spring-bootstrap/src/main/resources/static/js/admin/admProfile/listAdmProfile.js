class ListAdmProfile extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._form = $('#formListAdmProfile');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmProfile = $('#formListAdmProfile');
		
		this._tableList = $('#tableAdmProfile');
		this._dataTableList = this._tableList.DataTable( {
	        "dom": '<"top"flp>rt<"bottom"i><"clear">',
	        "language": { "url": '/js/hfsframework/dataTables.pt_br.json' }
	    } );			 
	    this._selectedRow = '#tableAdmProfile tbody tr.selected';
		
		$('#btnExport').click(this.btnExportClick.bind(this));
		$('#btnAdd').click(this.btnAddClick.bind(this));
		$('#btnEdit').click(this.btnEditClick.bind(this));
		$('#btnPreDelete').click(this.btnPreDeleteClick.bind(this));
		$('#btnDelete').click(this.btnDeleteClick.bind(this));
		$('#btnBack').click(this.btnBackClick.bind(this));		
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
		
		var selectedRow = $(this._selectedRow)[0];
		
		if (selectedRow && selectedRow.id > 0) {		
			this._form[0].action+= '/' + selectedRow.id;
			this._formListAdmProfile.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}

	btnPreDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var selectedRow = $(this._selectedRow)[0];
		
		if (selectedRow && selectedRow.id > 0) {		
			this._dlgDeleteConfirmation.modal("show");
		} else {
			this.dangerShow(this._messageSelectTable);
		}			
	}	

	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var selectedRow = $(this._selectedRow)[0];
		
		if (selectedRow && selectedRow.id > 0) {		
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + selectedRow.id,
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function() {
				//this._tableList[0].deleteRow(selectedRow.rowIndex);
				this._dataTableList.row('.selected').remove().draw( false );
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
			
	tableRowClick(tableRow) {
		this.sysDataTableRowClick(tableRow, this._dataTableList);  		  		  		
	}
			
}

const listAdmProfile = new ListAdmProfile();

$(function() {
	//const listAdmProfile = new ListAdmProfile();
});
