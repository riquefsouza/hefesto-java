class ListAdmParameterCategory extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._paginationNumber = $('#admParameterCategory_paginationNumber');
		this._paginationSize = $('#admParameterCategory_paginationSize');
		this._paginationSort = $('#admParameterCategory_paginationSort');
		this._columnOrder = $('#admParameterCategory_columnOrder');
		this._columnTitle = $('#admParameterCategory_columnTitle');
		
		this.systemObj = new HFSSystemObject(this._paginationNumber[0].value, this._paginationSize[0].value,
			this._paginationSort[0].value, this._columnOrder[0].value, this._columnTitle[0].value);
		
		this._form = $('#formListAdmParameterCategory');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');			
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmParameterCategory = $('#formListAdmParameterCategory');
			
		this._tableList = $('#tableAdmParameterCategory');
		this._selectedRow = '#tableAdmParameterCategory tbody tr.selected';
			
		$('#btnExport').click(this.btnExportClick.bind(this));
		$('#btnAdd').click(this.btnAddClick.bind(this));
		$('#btnEdit').click(this.btnEditClick.bind(this));
		$('#btnPreDelete').click(this.btnPreDeleteClick.bind(this));
		$('#btnDelete').click(this.btnDeleteClick.bind(this));
		$('#btnBack').click(this.btnBackClick.bind(this));
	
		$('#cmbPaginationSize').change(this.cmbPaginationSizeChange.bind(this));
		
		this.tableHeaderColumnSetSort(this.systemObj, this._tableList[0]);
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
			this._formListAdmParameterCategory.submit();	
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
		
		this.removePersistedObj(this.systemObjKEY);
		this._anchorHomePage[0].click();
	}

	tableRowClick(tableRow) {
		var selectedRow = $(this._selectedRow);
		this.sysTableRowClick(tableRow, selectedRow);
	}
	
	cmbPaginationSizeChange(event) {
		event.preventDefault();
		
		var paginationSize = event.target.value;
		
		this.systemObj = new HFSSystemObject(this._paginationNumber[0].value, paginationSize,
			this._paginationSort[0].value, this._columnOrder[0].value, this._columnTitle[0].value);
		
		this.sysCmbPaginationSizeChange(this.systemObj);
	}
		
	tableHeaderColumnClick(tableColumn, columnOrder, columnTitle){

		this.systemObj = new HFSSystemObject(this._paginationNumber[0].value, this._paginationSize[0].value,
			this._paginationSort[0].value, columnOrder, columnTitle);

		this.sysTableHeaderColumnClick(this.systemObj, tableColumn);
	}	
}

const listAdmParameterCategory = new ListAdmParameterCategory();

$(function() {
	//const listAdmParameterCategory = new ListAdmParameterCategory();	
});
