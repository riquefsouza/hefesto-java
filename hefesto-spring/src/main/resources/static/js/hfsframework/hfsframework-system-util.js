class HFSSystemUtil {
	constructor()
	{
		this._url = window.location.href;
		
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
		
	showDialogAlertMessage(title, message) {
		this.hideDialogAlertMessage();
		
		$('#dlgAlertMessage_label').text(title);
		this._dlgAlertMessageText.html(message);
		this._dlgAlertMessage.puidialog('show');
	}

	hideDialogAlertMessage() {
		this._dlgAlertMessage.puidialog('hide');
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
