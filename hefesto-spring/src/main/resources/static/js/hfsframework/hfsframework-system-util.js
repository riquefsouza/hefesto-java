class HFSSystemUtil {
	constructor()
	{
		this._url = window.location.href;
		this._urlAuthServer = $("meta[name='URL-AUTH-SERVER']").attr("content");
		this._urlResourceServer = $("meta[name='URL-RESOURCE-SERVER']").attr("content");
		this._authToken = $("meta[name='X-AUTH-TOKEN']").attr("content");
		
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
			
		this.buildDialogAlertMessage(this._dlgAlertMessage);
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

	removeCookie(cname,cvalue) {
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
	
	buildDialogDelete(urlApiServer, authToken, tableList, 
			dlgDeleteConfirmation, messageButtonYes, messageButtonNo, fieldJsonText){
		this._dlgDeleteConfirmation.puidialog({
		    minimizable: false,
		    maximizable: false,
		    resizable: false,
		    responsive: true,
		    minWidth: 200,
		    modal: true,
		    buttons: [{
		            text: messageButtonYes,
		            icon: 'fa-check',
		            click: function() {
		            	var dataRowSelected = tableList.puidatatable('getSelection');
		            	
		            	if (dataRowSelected.length > 0) {
		        			$.ajax({
		        				method: "DELETE",
		        				url: urlApiServer + "/" + dataRowSelected[0].id,
		        				dataType: "json",
		        			    contentType: "application/json; charset=utf-8",								
		        		        context: this,
		        		        beforeSend: function (xhr) {
		        		            xhr.setRequestHeader("Authorization", "Bearer " + authToken);
		        		        }
		        			})
		        			.done(function(data) {
		        				fieldJsonText.val("");
		        				
		        				tableList.puidatatable('reload');
		        				dlgDeleteConfirmation.puidialog('hide');
			            	})
			    			.fail(function(xhr){
	        		            //alert("An error occured DELETE: " + xhr.status + " " + xhr.statusText);
			    				this.dangerShow("An error occured DELETE: " + xhr.status + " " + xhr.statusText);
	        		        });

		            	}
		            	
		            }
		        },
		        {
		            text: messageButtonNo,
		            icon: 'fa-close',
		            click: function() {
		                dlgDeleteConfirmation.puidialog('hide');
		            }
		        }
		    ]
		});	
	}
	
	buildTable(urlApiServer, authToken, formTitle, responsePage, fieldJsonText, tableColumns) {
		this._tableList.puidatatable({
			caption: formTitle.text(),
			lazy: true,
			responsive: true,	           
			selectionMode: 'single',
			paginator: {
				rows: responsePage.size,
				totalRecords: (responsePage.totalElements-responsePage.size)
			},
			columns: tableColumns,
			datasource: function(callback, ui) {
				//ui.first = Index of the first record
				//ui.rows = Number of rows to load
				//ui.sortField = Field name of the sorted column
				//ui.sortOrder = Sort order of the sorted column
				//ui.sortMeta = Array of field names and sort orders of the sorted columns 
				//ui.filters = Filtering information with field-value pairs like ui.filters['fieldname'] = 'filtervalue'
				var uri = '';
				var ascDesc = 'asc';
				var fieldSort = 'id';
				
				//console.log(ui);
				//if (ui.filters){
				  // if (ui.filters.length > 0){
				   	//	console.log(ui.filters[0].field);
				   		//console.log(ui.filters[0].value);
				   //}	        	   		
				//}					           
				
				ui.sortField ? fieldSort = ui.sortField : fieldSort = 'id'; 	            
				ui.sortOrder == 1 ? ascDesc='asc' : ascDesc='desc';
				
				if (ui.first==0)
					uri = urlApiServer+'?page=0&size='+responsePage.size+'&sort='+fieldSort+','+ascDesc;
				else
					uri = urlApiServer+'?page='+((ui.first/ui.rows)+1)+'&size='+responsePage.size+'&sort='+fieldSort+','+ascDesc;
				
				$.ajax({
					method: "GET",
					url: uri,
					dataType: "json",
				    contentType: "application/json; charset=utf-8",								
			        context: this,
			        beforeSend: function (xhr) {
			            xhr.setRequestHeader("Authorization", "Bearer " + authToken);
			        }
				}).done(function(data) {
		        	callback.call(this, data.content);
				}).fail(function(xhr){
					this.dangerShow("An error occured on buildTable: " + xhr.status + " " + xhr.statusText);
			    });
			},	           
			rowSelect: function(event, data) {
				fieldJsonText.val(JSON.stringify(data));
			}
		});
		
	}
	
	
	buildGetPages(urlApiServer, authToken, formTitle, tableList, dlgDeleteConfirmation, 
			messageButtonYes, messageButtonNo, fieldJsonText, tableColumns) {
		
		$.get({
			url: urlApiServer + "/pages",
			dataType: "json",
		    contentType: "application/json; charset=utf-8",								
	        context: this,
	        beforeSend: function (xhr) {
	            xhr.setRequestHeader("Authorization", "Bearer " + authToken);
	        }
		})
		.done(function(data) {
    		this.buildTable(urlApiServer + "/pages", authToken, formTitle, data,
    				fieldJsonText, tableColumns);
    		this.buildDialogDelete(urlApiServer, authToken, tableList,
    				dlgDeleteConfirmation, messageButtonYes, messageButtonNo,
    				fieldJsonText);	        
		})
		.fail(function(xhr, textStatus, msg){
			this.dangerShow("An error occured on List: " + xhr.status + " " + xhr.statusText);
	    	/*
	    	setTimeout(function() {
	    		//this.dangerHide();
			}, 1500);
			*/
	    })
	    .always(function(){
	    	//$('#spinner').toggle();
	    });
		
	}
	
	buildDialogAlertMessage(dlgAlertMessage) {
		this._dlgAlertMessage.puidialog({
		    minimizable: false,
		    maximizable: false,
		    resizable: false,
		    responsive: true,
		    minWidth: 200,
		    modal: true,
		    buttons: [{
		            text: 'Ok',
		            icon: 'fa-check',
		            click: function() {
		            	dlgAlertMessage.puidialog('hide');
		            }
		        }
		    ],
		    beforeShow: function(event) {
		    	//console.log(event.target);		    	
		    }
		});
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
