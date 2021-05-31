class ListAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._paginationNumber = $('#admParameterCategory_paginationNumber');
		
		this.systemObjKEY = 'admParameterCategory_systemObj';
		this.systemObj = new HFSSystemObject(this._paginationNumber[0].value, "Description", "ASC,description");
		
		var obj = this.getPersistedObj(this.systemObjKEY);
				
		if (obj) {			
			this.systemObj.setJSON(obj);
			this.systemObj.setPaginationNumber(this._paginationNumber[0].value);
		} else {			
			this.persistObj(this.systemObjKEY, this.systemObj);
		}
		
		this._form = $('#formListAdmParameterCategory');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._tableList = $('#tableAdmParameterCategory');			
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameterCategory = $('#formListAdmParameterCategory');
			
		$('#btnExport').click(this.btnExportClick.bind(this));
		$('#btnAdd').click(this.btnAddClick.bind(this));
		$('#btnEdit').click(this.btnEditClick.bind(this));
		$('#btnDelete').click(this.btnDeleteClick.bind(this));
		$('#btnBack').click(this.btnBackClick.bind(this));
	
		$('#cmbPaginationSize').change(this.cmbPaginationSizeChange.bind(this));
		
		this.tableHeaderColumnSetSort(this._tableList[0], this.systemObj);
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
	
	/*
	getSystemObj(){
		var obj = this.getPersistedObj(this.systemObjKEY);
		this.systemObj.setJSON(obj);
		return this.systemObj; 
	}
	*/
	
	btnEditClick(event) {
		event.preventDefault();		
		this.dangerHide();
			
		var rowId = this.systemObj.getRowId();
		
		if (rowId >= 0) {		
			this._form[0].action+= '/' + rowId;
			
			this._formListAdmParameterCategory.submit();	
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}
	
	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var rowId = this.systemObj.getRowId();
		var tableRow = this.systemObj.getTableRowIndex();
		
		if (rowId >= 0) {
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + rowId,
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function(data) {
				this._tableList[0].deleteRow(tableRow);
				location.reload();
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
		
		this.removePersistedObj(this.systemObjKEY);
		this._anchorHomePage[0].click();
	}

	tableRowClick(tableRow, rowId) {
		this.sysTableRowClick(this.systemObjKEY, this.systemObj, this._tableList[0], tableRow, rowId);
	}
	
	cmbPaginationSizeChange(event) {
		event.preventDefault();
		
		this.sysCmbPaginationSizeChange(this.systemObjKEY, this.systemObj, this._paginationNumber[0].value, event.target.value);
	}
		
	tableHeaderColumnClick(tableColumn, columnOrder, columnTitle){
		this.sysTableHeaderColumnClick(this.systemObjKEY, this.systemObj, tableColumn, columnOrder, columnTitle);
	}	
}

const listAdmParameterCategory = new ListAdmParameterCategory();

$(function() {
	//const listAdmParameterCategory = new ListAdmParameterCategory();	
});
