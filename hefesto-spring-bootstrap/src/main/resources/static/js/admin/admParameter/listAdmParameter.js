class ListAdmParameter extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._form = $('#formListAdmParameter');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');		
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameter = $('#formListAdmParameter');
		
		this._tableList = $('#tableAdmParameter');		
		this._tableList.DataTable( {
	        "dom": '<"top"flp>rt<"bottom"i><"clear">'
	    } );		
		
		this._tableRowIndex = $('#admParameter_tableRowIndex');
		this._rowId = $('#admParameter_rowId');
		
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
		
		var rowId = this._rowId[0].value;
		
		if (rowId > 0) {		
			this._form[0].action+= '/' + rowId;
			
			this._formListAdmParameter.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}

	}

	btnPreDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var rowId = this._rowId[0].value;
		
		if (rowId && rowId > 0) {					
			this._dlgDeleteConfirmation.modal("show");
		} else {
			this.dangerShow(this._messageSelectTable);
		}			
	}	

	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();

		var rowId = this._rowId[0].value;
		var tableRow = this._tableRowIndex[0].value;
		
		if (rowId && rowId > 0) {
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + rowId,
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function() {
				this._tableList[0].deleteRow(tableRow);
				//location.reload();
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
	
	tableRowClick(tableRow, rowId) {
		var oldTableRowIndex = this._tableRowIndex[0].value;
		
		if (oldTableRowIndex && oldTableRowIndex > 0) {
			this._tableList[0].rows[oldTableRowIndex].style.backgroundColor = "transparent";
		}
		
		this._tableRowIndex[0].value = tableRow.rowIndex;
		this._rowId[0].value = rowId;  		
  		
  		if (tableRow.rowIndex > 0){
	  		this._tableList[0].rows[tableRow.rowIndex].style.backgroundColor = "lightblue";
  		}
	}
	
}

const listAdmParameter = new ListAdmParameter();

$(function() {
	//const listAdmParameter = new ListAdmParameter();
});
