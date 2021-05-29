class ListAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._form = $('#formListAdmParameterCategory');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmParameterCategory');			
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameterCategory = $('#formListAdmParameterCategory');
		
		this._admParameterCategoryId = $('#admParameterCategoryId');
		this._admParameterCategoryTableRowIndex = $('#admParameterCategoryTableRowIndex');
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
	
	btnEditClick(rowId) {
		//event.preventDefault();		
		this.dangerHide();
		
		if (rowId) {		
			this._form[0].action+= '/' + rowId;
			
			this._formListAdmParameterCategory.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}
	
	tableRowClick(tableRow, rowId) {
		var oldTableRowIndex = this._admParameterCategoryTableRowIndex.value;
		
		if (oldTableRowIndex && oldTableRowIndex > 0) {
			this._tableList[0].rows[oldTableRowIndex].style.backgroundColor = "transparent";
		}
	
		this._admParameterCategoryTableRowIndex.value = tableRow.rowIndex;
  		this._admParameterCategoryId.value = rowId;
  		
  		if (tableRow.rowIndex > 0){
	  		this._tableList[0].rows[tableRow.rowIndex].style.backgroundColor = "lightblue";
  		}  		  		
	}

	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var rowId = this._admParameterCategoryId.value;
		var tableRow = this._admParameterCategoryTableRowIndex.value;
		
		if (rowId) {
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + rowId,
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function(data) {
				this._tableList[0].deleteRow(tableRow);
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

const listAdmParameterCategory = new ListAdmParameterCategory();

$(function() {
	//const listAdmParameterCategory = new ListAdmParameterCategory();
	
	$('#btnExport').click(listAdmParameterCategory.btnExportClick.bind(listAdmParameterCategory));
	$('#btnAdd').click(listAdmParameterCategory.btnAddClick.bind(listAdmParameterCategory));
	//$('#btnEdit').click(listAdmParameterCategory.btnEditClick.bind(listAdmParameterCategory));
	$('#btnDelete').click(listAdmParameterCategory.btnDeleteClick.bind(listAdmParameterCategory));
	$('#btnBack').click(listAdmParameterCategory.btnBackClick.bind(listAdmParameterCategory));
	
	
});
