PrimeFaces.widget.DataTable.prototype.showEditors = function(element) {
			 
			var element = $(element);
            if (element.get(0).tagName=='TR'){
            	element = element.find('div.ui-row-editor');
            }
            element.parents('tr:first').addClass('ui-state-highlight').children('td.ui-editable-column').each(function() {
                var column = $(this);
                column.find('div.ui-cell-editor-output').hide();
                column.find('div.ui-cell-editor-input').show();
            });
            element.parents('tr:first').children('td:first').find('input:first').focus();
            
            
            element.parents('tr:first').children('td').children('div.ui-row-editor').each(function() {
                var column = $(this);
               
                column.find('span.ui-icon-pencil').hide().siblings().show().css('display','block');
            });
            
            element.parents('tr:first').siblings().removeClass('ui-state-highlight').children('td.ui-editable-column').each(function() {
                var column = $(this);
                column.find('div.ui-cell-editor-output').show();
                column.find('div.ui-cell-editor-input').hide();
            });
            
            element.parents('tr:first').siblings().children('td').children('div.ui-row-editor').each(function() {
                var column = $(this);
                column.find('span.ui-icon-pencil').show().siblings().hide();
            });
            
            element.parents('tr.ui-state-highlight').find(':input').each(function() {
            	var input = $(this);
            	input.die().live('keydown',function(e){
            		if (e.which==13){
            			element.parents('tr:first').children('td').children('div.ui-row-editor').each(function() {
                            var column = $(this);
                            column.find('span.ui-icon-check').click();
                        });
            			e.stopPropagation();
            			return false;
            		}
            		
            	});
            	
            });
            
            
};


$('.ui-datatable tr').live('dblclick.sgf', function(e) {
	PrimeFaces.widget.DataTable.prototype.showEditors(e.target);
});


		
		