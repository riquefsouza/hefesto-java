class HFSSystemObject {

	constructor(paginationNumber, paginationSize, paginationSort, columnOrder, columnTitle)
	{
		this.paginationNumber = paginationNumber;
		this.paginationSize = paginationSize;
		this.paginationSort = paginationSort;
		this.columnOrder = columnOrder;
		this.columnTitle = columnTitle;
	}
	
	setJSON(obj) 
	{
		this.paginationNumber = obj.paginationNumber;
		this.paginationSize = obj.paginationSize;
		this.paginationSort = obj.paginationSort;
		this.columnOrder = obj.columnOrder;
		this.columnTitle = obj.columnTitle;
	}
	
	setPaginationNumber(paginationNumber){
		this.paginationNumber = paginationNumber;
	}
		
	getPaginationNumber(){
		return this.paginationNumber;
	}

	setPaginationSize(paginationSize){
		this.paginationSize = paginationSize;
	}
		
	getPaginationSize(){
		return this.paginationSize;
	}
	
	setPaginationSort(paginationSort){
		this.paginationSort = paginationSort;
	}
		
	getPaginationSort(){
		return this.paginationSort;
	}

	setColumnOrder(columnOrder){
		this.columnOrder = columnOrder;
	}
	
	getColumnOrder(){
		return this.columnOrder;
	}
	
	setColumnTitle(columnTitle){
		this.columnTitle = columnTitle;
	}
	
	getColumnTitle(){
		return this.columnTitle;
	}
	
	toString() {
		return "paginationNumber = " + this.paginationNumber +
		", paginationSize = " + this.paginationSize + 
		", paginationSort = " + this.paginationSort +
		", columnOrder = " + this.columnOrder +
		", columnTitle = " + this.columnTitle;
	}
	
}

class HFSSystemUtil {
	constructor()
	{
		this._url = window.location.href;
		//this._urlServer = $("meta[name='URL-SERVER']").attr("content");
		
		this._anchorHomePage = $('#anchorHomePage');

		this._messageSelectTable = $('#message-select-table').text();
		
		this._messageAlertSuccess = $('#message-alert-success').text();
		this._messageAlertDanger = $('#message-alert-danger').text();
		this._messageAlertWarning = $('#message-alert-warning').text();
		this._messageAlertInfo = $('#message-alert-info').text();	
		
		this._messageButtonYes = $('#message-button-yes').text();
		this._messageButtonNo = $('#message-button-no').text();
		this._emptyStringValidator = $('#validator-emptyStringValidator').text();
		
		this._alertPrimary = $('#alert-primary');
		this._textAlertPrimary = $('#text-alert-primary');
		this._alertSecondary = $('#alert-secondary');
		this._textAlertSecondary = $('#text-alert-secondary');
		this._alertSuccess = $('#alert-success');
		this._textAlertSuccess = $('#text-alert-success');
		this._alertDanger = $('#alert-danger');
		this._textAlertDanger = $('#text-alert-danger');
		this._alertWarning = $('#alert-warning');
		this._textAlertWarning = $('#text-alert-warning');
		this._alertInfo = $('#alert-info');
		this._textAlertInfo = $('#text-alert-info');
		this._alertLight = $('#alert-light');
		this._textAlertLight = $('#text-alert-light');
		this._alertDark = $('#alert-dark');
		this._textAlertDark = $('#text-alert-dark');
		
		this._dlgAlertMessage = $('#dlgAlertMessage');
		this._dlgAlertMessageText = $('#dlgAlertMessage-text');

		$('#btnAlertPrimary').click(this.btnAlertPrimaryClick.bind(this));
		$('#btnAlertSecondary').click(this.btnAlertSecondaryClick.bind(this));
		$('#btnAlertSuccess').click(this.btnAlertSuccessClick.bind(this));
		$('#btnAlertDanger').click(this.btnAlertDangerClick.bind(this));
		$('#btnAlertWarning').click(this.btnAlertWarningClick.bind(this));
		$('#btnAlertInfo').click(this.btnAlertInfoClick.bind(this));
		$('#btnAlertLight').click(this.btnAlertLightClick.bind(this));
		$('#btnAlertDark').click(this.btnAlertDarkClick.bind(this));
	}
	
	getSystemPage() {
		var home = this._anchorHomePage[0].href;
		return home.substring(0, home.lastIndexOf("/"));
	}
	
	hideQueryString() {
		var uri = window.location.toString();
		if (uri.indexOf("?") > 0) {
		    var clean_uri = uri.substring(0, uri.indexOf("?"));
		    window.history.replaceState({}, document.title, clean_uri);
		}		
	}
	
	primaryShow(message) {
		this._alertPrimary.show();
		this._textAlertPrimary.html(message);		
	}
	
	primaryHide() {
		this._textAlertPrimary.html("");
		this._alertPrimary.hide();
	}

	secondaryShow(message) {
		this._alertSecondary.show();
		this._textAlertSecondary.html(message);		
	}
	
	secondaryHide() {
		this._textAlertSecondary.html("");
		this._alertSecondary.hide();
	}

	successShow(message) {
		this._alertSuccess.show();
		this._textAlertSuccess.html(message);		
	}
	
	successHide() {
		this._textAlertSuccess.html("");
		this._alertSuccess.hide();
	}

	dangerShow(message) {
		this._alertDanger.show();
		this._textAlertDanger.html(message);
	}
	
	dangerHide() {
		this._textAlertDanger.html("");
		this._alertDanger.hide();
	}
	
	warningShow(message) {
		this._alertWarning.show();
		this._textAlertWarning.html(message);		
	}
	
	warningHide() {
		this._textAlertWarning.html("");
		this._alertWarning.hide();
	}

	infoShow(message) {
		this._alertInfo.show();
		this._textAlertInfo.html(message);		
	}
	
	infoHide() {
		this._textAlertInfo.html("");
		this._alertInfo.hide();
	}
	
	lightShow(message) {
		this._alertLight.show();
		this._textAlertLight.html(message);		
	}
	
	lightHide() {
		this._textAlertLight.html("");
		this._alertLight.hide();
	}

	darkShow(message) {
		this._alertDark.show();
		this._textAlertDark.html(message);		
	}
	
	darkHide() {
		this._textAlertDark.html("");
		this._alertDark.hide();
	}
	
	setCookie(cname,cvalue) {
		var d = new Date();
		// d.setTime(d.getTime() + (exdays*24*60*60*1000));
		d.setTime(d.getTime() + (30*60*1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}

	getCookie(cname) {
	  var name = cname + "=";
	  var decodedCookie = decodeURIComponent(document.cookie);
	  var ca = decodedCookie.split(';');
	  for(var i = 0; i < ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}

	removeCookie(cname) {
		var d = new Date();
		d.setTime(d.getTime());
		var expires = "expires=" + d.toGMTString();
		document.cookie = cname + "=;" + expires + ";path=/";
	}

	persistItem(key, value){
		if (typeof(Storage) !== "undefined") {
			window.sessionStorage.setItem(key, value);
		} else {
			setCookie(key, value, 1);
		}
	}

	getPersistedItem(key){
		if (typeof(Storage) !== "undefined") {
			return window.sessionStorage.getItem(key);
		} else {
			return getCookie(key);
		}
	}

	removePersistedItem(key){
		if (typeof(Storage) !== "undefined") {
			window.sessionStorage.removeItem(key);
		} else {
			removeCookie(key, value);
		}
	}
	
	persistObj(key, value) {
		window.sessionStorage.setItem(key, JSON.stringify(value));
	}

	getPersistedObj(key) {
		return JSON.parse(window.sessionStorage.getItem(key));
	}

	removePersistedObj(key) {
		window.sessionStorage.removeItem(key);
	}	
		
	showDialogAlertMessage(title, message) {
		this.hideDialogAlertMessage();
		
		$('#dlgAlertMessage_label').text(title);
		this._dlgAlertMessageText.html(message);
		this._dlgAlertMessage.puidialog('show');
	}

	hideDialogAlertMessage() {
		this._dlgAlertMessage.puidialog('hide');
	}
	
	btnAlertPrimaryClick(event){
		event.preventDefault();
		this.primaryHide();
	}

	btnAlertSecondaryClick(event){
		event.preventDefault();
		this.secondaryHide();
	}
	
	btnAlertSuccessClick(event){
		event.preventDefault();
		this.successHide();
	}

	btnAlertDangerClick(event){
		event.preventDefault();
		this.dangerHide();
	}
	
	btnAlertWarningClick(event){
		event.preventDefault();
		this.warningHide();
	}

	btnAlertInfoClick(event){
		event.preventDefault();
		this.infoHide();
	}

	btnAlertLightClick(event){
		event.preventDefault();
		this.lightHide();
	}

	btnAlertDarkClick(event){
		event.preventDefault();
		this.darkHide();
	}
	
	sysTableRowClick(tableRow, selectedRow) {
	 	if ( $(tableRow).hasClass('selected') ) {
            $(tableRow).removeClass('selected');
        } else {
            selectedRow.removeClass('selected');
            $(tableRow).addClass('selected');
        }  		  		  		
	}	  		  		  		
	
	sysDataTableRowClick(tableRow, dataTableList) {
 		if ( $(tableRow).hasClass('selected') ) {
            $(tableRow).removeClass('selected');
        } else {
            dataTableList.$('tr.selected').removeClass('selected');
            $(tableRow).addClass('selected');
        }  		  		  		
	}	
	
	sysCmbPaginationSizeChange(systemObj) {
		this.dangerHide();
				
		if (systemObj && systemObj.getPaginationNumber() && systemObj.getPaginationSize() > 0) {
			window.location.href = window.location.href + "?pageNumber=1&size=" + systemObj.getPaginationSize() 
			+ "&sort=" + systemObj.getPaginationSort() 
			+ "&columnOrder=" + systemObj.getColumnOrder() 
			+ "&columnTitle=" + systemObj.getColumnTitle();
		}
	}	
	
	sysTableHeaderColumnClick(systemObj, tableColumn){
		var up=systemObj.getColumnTitle() + "  <i class='fas fa-sort-alpha-up fa-sm'></i>";
		var down=systemObj.getColumnTitle() + "  <i class='fas fa-sort-alpha-down fa-sm'></i>";
		
		var paginationSort = "ASC,id";
		
		if (tableColumn.innerHTML.includes("fa-sort-alpha-up")) {
			tableColumn.innerHTML=down;
			paginationSort = "DESC," + systemObj.getColumnTitle().toLowerCase();
		} else {
			tableColumn.innerHTML=up;
			paginationSort = "ASC," + systemObj.getColumnTitle().toLowerCase();
		}
			
		if (systemObj && systemObj.getPaginationNumber() && systemObj.getPaginationSize() > 0) {
			systemObj.setPaginationSort(paginationSort);
		
			window.location.href = window.location.href + "?pageNumber=" + systemObj.getPaginationNumber() 
			+ "&size=" + systemObj.getPaginationSize() 
			+ "&sort=" + systemObj.getPaginationSort() 
			+ "&columnOrder=" + systemObj.getColumnOrder() 
			+ "&columnTitle=" + systemObj.getColumnTitle();
		}
	}	

	tableHeaderColumnSetSort(systemObj, tableList) {
		for (var i = 1; i < tableList.rows[0].cells.length; i++) {
			if (systemObj.getColumnOrder() == i) {
				if (systemObj.getPaginationSort().includes("ASC")) {
					tableList.rows[0].cells[i].innerHTML = systemObj.getColumnTitle() + "  <i class='fas fa-sort-alpha-up fa-sm'></i>";
				} else {
					tableList.rows[0].cells[i].innerHTML = systemObj.getColumnTitle() + "  <i class='fas fa-sort-alpha-down fa-sm'></i>";
				}
			} else {
				tableList.rows[0].cells[i].innerHTML = tableList.rows[0].cells[i].innerHTML.replace("-down", "-up");
			}
		}
	}	

	pickListEdtKeyUp(source, bufferSource, svalue) {
	
		var sizeSource = bufferSource.options.length;

		for (var i = sizeSource-1; i >= 0; i--) {		
			source.options.remove(i);
		}

		if (svalue.trim().length > 0) {
			for (var i = 0; i < sizeSource; i++) {
				if (bufferSource.options[i].text.toLowerCase().includes(svalue.toLowerCase())) {
					var newOption = document.createElement("option");
					newOption.text = bufferSource.options[i].text;
					source.options.add(newOption);					
				}	
			}
		} else {
			for (var i = 0; i < sizeSource; i++) {
				var newOption = document.createElement("option");
				newOption.text = bufferSource.options[i].text;
				source.options.add(newOption);					
			}			
		}		
	}
	
	findOptionIndexByText(source, text){
		for (var i = 0; i < source.options.length; i++) {
			if (source.options[i].text === text) {
				return i;	
			}			
		}
		return -1;
	}
	
	pickListBtnAllClick(source, target, bufferSource, bufferTarget) {
				
		var sizeSource = source.options.length; 		

		for (var i = 0; i < sizeSource; i++) {
			var newOption = document.createElement("option");
			newOption.text = source.options[i].text;
			target.options.add(newOption);
			
			var newOption = document.createElement("option");
			newOption.text = source.options[i].text;			
			bufferTarget.options.add(newOption);			
		}

		for (var i = sizeSource-1; i >= 0; i--) {			
			bufferSource.options.remove(
				this.findOptionIndexByText(bufferSource, source.options[i].text));
			source.options.remove(i);
		}	
	}
	
	pickListBtnClick(source, target, bufferSource, bufferTarget) {
				
		var sizeSource = source.options.length;
		
		for (var i = 0; i < sizeSource; i++) {
			if (source.options[i].selected) {		
				var newOption = document.createElement("option");
				newOption.text = source.options[i].text;
				target.options.add(newOption);
				
				var newOption = document.createElement("option");
				newOption.text = source.options[i].text;				
				bufferTarget.options.add(newOption);
			}				
		}

		for (var i = sizeSource-1; i >= 0; i--) {
			if (source.options[i].selected) {
				bufferSource.options.remove(
					this.findOptionIndexByText(bufferSource, source.options[i].text));
				source.options.remove(i);				
			}	
		}		
	}
	
	mountTreeMenu() {
	
		var toggler = document.getElementsByClassName("caret");
	
		for (var i = 0; i < toggler.length; i++) {
			toggler[i].addEventListener("click", function() {
				this.parentElement.querySelector(".nested").classList
						.toggle("active");
				this.classList.toggle("caret-down");
			});
		}	
	}

	removeTreeNode(nodeId){
		var nodes = document.getElementsByClassName("nodeText");

		for (var i = 0; i < nodes.length; i++) {
			var index = nodes[i].parentElement.getAttributeNode("name").value;
		
			if (index == nodeId) {			
				nodes[i].parentElement.remove();
			}	
		}		
	}
			
	
}


$(function() {
	$("#locales").change(function () {
        var selectedOption = $("#locales").val();
        if (selectedOption != ''){
        	var sUrl = window.location.href;
        	
            if (sUrl.lastIndexOf("?") > -1){
            	sUrl = sUrl.substr(0, sUrl.indexOf("?lang="));
            }
        	
            window.location.replace(sUrl + '?lang=' + selectedOption);
        }
    });	
});
