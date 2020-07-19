//Configuração de localização para os campos temporais do Primefaces
PrimeFaces.locales['pt'] = {
	closeText : 'Fechar',
	prevText : 'Anterior',
	nextText : 'Próximo',
	currentText : 'Hoje',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sábado' ],
	dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
	dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	weekHeader : 'Semana',
	firstDay : 0,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Só Horas',
	timeText : 'Tempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	ampm : false,
	month : 'Mês',
	week : 'Semana',
	day : 'Dia',
	allDayText : 'Todo o Dia',
    hourText: 'Horas',
    minuteText: 'Minutos',
    amPmText: ['AM', 'PM'],
    closeButtonText: 'Fechar',
    nowButtonText: 'Agora',
    deselectButtonText: 'Limpar seleção',
    'MONTHS': ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
    'MONTHS_SHORT': ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
    'DAYS': ["Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"],
    'DAYS_SHORT': ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
    'ZOOM_IN': "Aumentar zoom",
    'ZOOM_OUT': "Diminuir zoom",
    'MOVE_LEFT': "Mover esquerda",
    'MOVE_RIGHT': "Mover direita",
    'NEW': "Novo",
    'CREATE_NEW_EVENT': "Criar novo evento"
};

var $jq = jQuery.noConflict();
this.jq = jQuery.noConflict();
var $j = jQuery.noConflict();

$j(document)
		.ready(
				function() {

					configureDisableSubmit();

					// Seta o foco para o primeiro campo ou botao ativo.
					$j(
							"form :input:visible:enabled:not([readonly='readonly']):not(.hasDatepicker):first, form .btn:visible:first")
							.first().focus();

					// Impede que, durante o click do mouse, os menus que
					// possuam submenus desapareçam.
					$j('.dropdown-submenu > a').click(function(e) {
						e.stopPropagation();
						return false;
					});

					configureDatePickerMask();

					configurePositiveInteger();

					// Exibe topo da página.
					window.scrollTo(0, 0);

					$("form").submit(function() {
						var $btn = $(document.activeElement);
					    if(!$btn.hasClass('skipModalLoading')) {
					    	var attrClick = $btn.attr("onclick");
					    	if(attrClick === undefined || attrClick.indexOf("PrimeFaces.monitorDownload") < 0) {
					    		start();
					    	}
						}
					});
					
					$("a").not("ul.ui-tabs-nav > li > a").not("a.ui-dialog-titlebar-close").not("a[onclick*='PrimeFaces.confirm']")
						.not("a.ui-selectcheckboxmenu-close")
						.not("legend").click(function() {
						if(!$(this).hasClass('skipModalLoading') && !$(this).hasClass('ui-spinner-button') && !$(this).hasClass('ui-messages-close')) {
					    	start();
						}	
					});
				});

jQuery.fn.preventDoubleSubmit = function() {
    jQuery(this).submit(function() {
      if (this.beenSubmitted)
        return false;
      else
        this.beenSubmitted = true;
    });
  };

  
function configureDisableSubmit() {
	// Cria dois handlers para o evento 'click' do elemento
	// 'disableSubmit'. O primeiro cria a lógica
	// para impedir a execução de mais de um click; o segundo é
	// a propriedade 'onclick' do botão
	$j('input.disableSubmit').each(function() {
		$j(this).on('click', null, function(event) {
			var evt = event || window.event;
			return disableSubmit(evt);
		});

		var handler = $j(this).prop('onclick');
		if (handler) {
			$j(this).removeProp('onclick');
			$j(this).click(handler);
		}
	});
}

// Funções necessárias para funcionalidade de disableSubmit
var desabilitado = false;
function disableSubmit(evt) {
	// executa somente uma vez
	if (desabilitado) {
		// evita que os demais handlers de evento (A4J, no caso de
		// a4j:commandButton) sejam executados
		evt.stopImmediatePropagation();
		return false;
	}
	desabilitado = true;
	return true;
}

function configureDatePickerMask() {
	$('.hasDatepicker').datepicker(
			{
				showButtonPanel : true,
				changeMonth : true,
				changeYear : true,
				dateFormat : 'mm/dd/yyyy',
				onSelect : function(dateText, inst) {
					UIUtilities_jQueryDatePickerOnCloseEventIfAlsoMasked(this,
							'hasDatepicker', dateText);
				},
				onClose : function(dateText, inst) {
					UIUtilities_jQueryDatePickerOnCloseEventIfAlsoMasked(this,
							'hasDatepicker', dateText);
				}
			});

	$('.hasDatepicker').mask("99/99/9999", {
		placeholder : "_"
	});

}

function configurePositiveInteger() {
	// Permite a digitação de apenas inteiros positivos nos campos.
	$j(".positive-integer")
			.numeric(
					{
						decimal : false,
						negative : false
					},
					function() {
						alert("Este campo aceita apenas valores numéricos (inteiros positivos).");
						this.value = "";
						this.focus();
					});
}

function copyHtml(idDiv, idFacesVar) {
	var div = $('#idDiv').html();
    $j('#idFacesVar').val(div);
}


function start() {
	//PF('statusDialog').show();
}

function stop() {
	//PF('statusDialog').hide();
}
