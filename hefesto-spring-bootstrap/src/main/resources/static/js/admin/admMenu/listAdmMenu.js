class ListAdmMenu extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this.hideQueryString();
		
		this._form = $('#formListAdmMenu');
		this._cmbReportType = $('#cmbReportType');
		this._forceDownload = $('#forceDownload');
		this._dlgDeleteConfirmation = $('#dlgDeleteConfirmation');
		this._formTitle = $('#formTitle');
		this._formListAdmMenu = $('#formListAdmMenu');
		
		this._nodeId = $('#admMenu_nodeId');
			
		$('#btnExport').click(this.btnExportClick.bind(this));
		$('#btnAdd').click(this.btnAddClick.bind(this));
		$('#btnEdit').click(this.btnEditClick.bind(this));
		$('#btnPreDelete').click(this.btnPreDeleteClick.bind(this));
		$('#btnDelete').click(this.btnDeleteClick.bind(this));
		$('#btnBack').click(this.btnBackClick.bind(this));
			
		this.mountTreeMenu();
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
		
		var nodeId = this._nodeId[0].value;
		
		if (nodeId) {					
			this._form[0].action+= '/' + nodeId;
			this._formListAdmMenu.submit();				
		} else {
			this.dangerShow(this._messageSelectTable);
		}
	}

	btnPreDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var nodeId = this._nodeId[0].value;
		
		if (nodeId) {					
			this._dlgDeleteConfirmation.modal("show");
		} else {
			this.dangerShow(this._messageSelectTable);
		}			
	}
				
	btnDeleteClick(event) {
		event.preventDefault();
		this.dangerHide();
		
		var nodeId = this._nodeId[0].value;
		
		if (nodeId) {					
			
			$.ajax({
				method: "DELETE",
				url: window.location.href + "/" + dataRowSelected[0],
				dataType: "json",
			    contentType: "application/json; charset=utf-8",								
		        context: this
			})
			.done(function() {
				this.removeTreeNode(nodeId);
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
	
	treeNodeClick(nodeText, menuId) {
		this._nodeId[0].value = menuId;
		
		var nodes = document.getElementsByClassName("nodeText");
		
		for (var i = 0; i < nodes.length; i++) {
			nodes[i].style.backgroundColor = "transparent";
		}
		
		nodeText.style.backgroundColor = "lightblue";
		
	}
	
}

const listAdmMenu = new ListAdmMenu();

$(function() {
	//const listAdmMenu = new ListAdmMenu();
});
