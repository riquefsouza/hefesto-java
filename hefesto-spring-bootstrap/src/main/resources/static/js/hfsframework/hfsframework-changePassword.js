class HFSChangePassword extends HFSSystemUtil {
	constructor()
	{
		super();
		
		this._anchorHomePage = $('#anchorHomePage');
		
	}
	
	btnCancelClick(event) {
		event.preventDefault();
		this._anchorHomePage[0].click();
	}

}

$(function() {
	const hfsChangePassword = new HFSChangePassword();
	
	$('#btnCancel').click(hfsChangePassword.btnCancelClick.bind(hfsChangePassword));
	
});
