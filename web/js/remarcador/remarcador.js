/*
 * remarcador.js
 *
 * Script for index page
 * http://continuum.cl/
 *
 * Copyright (c) 2011 Continuum
 *
 */
(function ($) {
	$(document).ready(function () {
		function timestampToDate(cellvalue, options, rowObject) {
			var date = new Date((parseInt(cellvalue) * 1000) + (14400000));
		    var _mes = date.getMonth()+1;
		    var _dia = date.getDate();
		    var _anio = date.getFullYear();
		    var _horas = date.getHours().toString() == "0" ? "00" : date.getHours().toString();;
		    var _minutos = date.getMinutes().toString() == "0" ? "00" : date.getMinutes().toString();
		    return _dia+"/"+_mes+"/"+_anio + " - " + _horas + ":" + _minutos;
		} 

		function editarFila(cellvalue, options, rowObject) {
			return '<div align="center"><img src="images/editar.jpeg" id="editar-'+rowObject.id+'"></div>';
		}
		
		var perfil = $('#hdn-perfil-usuario')
		var noEsEditable = perfil.val() == 'OPERADOR' ? true : false;
		var hdnFechaInicial = $('#hdn-fecha-inicial');
		var hdnFechaFinal = $('#hdn-fecha-final');
		var tabla = $("#tbl-remarcador");

		$("#txt-fecha-inicial").datepicker({
			changeYear: true,
			changeMonth: true,
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true,
			minDate: new Date(2010, 8, 1),
			onSelect: function(dateText, inst) {
				var fechaInicial = $(this).datepicker("getDate");
				$("#txt-fecha-final").datepicker('option', {	
					minDate: fechaInicial.toIntDate().toDate('yyyymmdd'),//TODO
					maxDate: fechaInicial.toIntDate().toDate()
				});
			}
		});
		
		$("#txt-fecha-final").datepicker({
			changeYear: true,
			changeMonth: true,
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true
		});

		tabla.jqGrid({
			datatype: "local",
		   	colNames: ['Editar', 'Nombre', 'Local', 'Tablero', 'Nº medidor', 
		   	           'C&aacute;lculo medici&oacute;n', 'Centro de costo', 'Cuenta', 'Nodo', 'Observaci&oacute;n'],
		   	colModel: [
		   		{name: 'id', index: 'id', hidden: noEsEditable, formatter: editarFila, width: 50},
		   		{name: 'nombre', index: 'nombre', width: 100},
		   		{name: 'local', index: 'local', width: 100},
		   		{name: 'tablero', index: 'tablero', width: 100},
		   		{name: 'numeroMedidor', index: 'numeroMedidor', width: 100},
		   		{name: 'calculoMedicion', index: 'calculoMedicion', width: 100},
		   		{name: 'centroCosto', index: 'centroCosto', width: 100},
		   		{name: 'cuenta', index: 'cuenta', width: 100},
		   		{name: 'nodo', index: 'nodo', width: 100},
		   		{name: 'observacion', index: 'observacion', width: 100}
		   	],
		   	pager: '#div-opciones-pagina',
		    caption: "Remarcadores",
		    height: '100%',
		    subGrid: true,
		    viewrecords: true,
		    subGridRowExpanded: function(subgrid_id, row_id) {
				var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id + "_t";
				pager_id = "p_" + subgrid_table_id;
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table><div id='"+pager_id+"'></div>");
				var params = {
						perform: 'getDetalleRemarcador', 
						idRemarcador: row_id,
						fechaInicial: hdnFechaInicial.val(),
						fechaFinal: hdnFechaFinal.val()
				};
				$.ajaxMsgPostJSON('Cargando detalle...', 'main.htm', params, function(json) {
					$("#"+subgrid_table_id).jqGrid({
						data: json.resultado,
						datatype: "local",
						colNames: ['Fecha de medici&oacute;n', 'Dato'],
						colModel: [
							{name: "fecha_ts", index: "fecha_ts", formatter: timestampToDate},
							{name: "dato_bigint", index: "dato_bigint"}
						],
						height: 'auto',
						rowNum: 5,
					   	pager: pager_id,
					    caption: "Detalle",
					    viewrecords: true,
					    width: 600
					});	
				});
			}
		});

		$('#btn-buscar-remarcador').click(function(event) {
//			objeto.fechaLecturaInicial = params.fechaInicial + ' - ' + params.horaFechaInicial + ':' + params.minutosFechaInicial;
//			objeto.fechaLecturaFinal = params.fechaFinal + ' - ' + params.horaFechaFinal + ':' + params.minutosFechaFinal;
			var fechaInicial = $("#txt-fecha-inicial");
			var horaFechaInicial = $('#cmb-horas-lectura-inicial');
			var minutosFechaInicial = $('#cmb-minutos-lectura-inicial');
			var fechaFinal = $("#txt-fecha-final");
			var horaFechaFinal = $('#cmb-horas-lectura-final');
			var minutosFechaFinal = $('#cmb-minutos-lectura-final');
			var centroCosto = $('#cmb-centro-costo');
			var cuenta = $('#cmb-cuenta');
			var listaErrores = '<ul>';
			var modalListaErrores = $('#modal-lista-errores');
			
			if(fechaInicial.val() == "")
				listaErrores += '<li>Debes seleccionar una fecha inicial.</li>';
			if(horaFechaInicial.val() == "-1")
				listaErrores += '<li>Debes seleccionar una hora inicial.</li>';
			if($('#cmb-minutos-lectura-inicial').val() == "-1")
				listaErrores += '<li>Debes seleccionar los minutos de la hora inicial.</li>';
			if(fechaFinal.val() == "")
				listaErrores += '<li>Debes seleccionar una fecha final.</li>';
			if(horaFechaFinal.val() == "-1")
				listaErrores += '<li>Debes seleccionar una hora final.</li>';
			if(minutosFechaFinal.val() == "-1")
				listaErrores += '<li>Debes seleccionar los minutos de la hora final.</li>';
			
			listaErrores += '</ul>';
			modalListaErrores.html(listaErrores);
			if(modalListaErrores.find('li').length != 0) {
				modalListaErrores.dialog({
					modal: true,
					width: 450,
					buttons: {
						Cerrar: function() {
							$(this).dialog("close");
							modalListaErrores.empty();//TODO
						}
					}
				});
			} else {
				tabla.jqGrid('clearGridData');
				var params = {
						perform: 'getRemarcadorCalculado',
						fechaInicial: fechaInicial.val(),
						horaFechaInicial: horaFechaInicial.val(),
						minutosFechaInicial: minutosFechaInicial.val(),
						fechaFinal: fechaFinal.val(),
						horaFechaFinal: horaFechaFinal.val(),
						minutosFechaFinal: minutosFechaFinal.val(),
						idCentroCosto: centroCosto.val(),
						idCuenta: cuenta.val()
				};
				$.ajaxMsgPostJSON('Cargando...', 'main.htm', params, function(json) {
					hdnFechaInicial.val(json.timeInMillisFechaInicial);
					hdnFechaFinal.val(json.timeInMillisFechaFinal);
					$(json.resultado).each(function(indice, elemento) {
						var multiplicador = elemento.remarcador.multiplicador;
						var calculoMedicion = (elemento.remarcadorFinal.dato_bigint * multiplicador) - (elemento.remarcadorIncial.dato_bigint * multiplicador);
						var objeto = {};
						objeto.id = elemento.remarcador.id;
						objeto.nombre = elemento.remarcador.nombre;
						objeto.local = elemento.remarcador.local;
						objeto.tablero = elemento.remarcador.tablero;
						objeto.numeroMedidor = elemento.remarcador.numeroMedidor;
						objeto.calculoMedicion = calculoMedicion;
						objeto.centroCosto = elemento.remarcador.centroCosto.nombre;
						objeto.cuenta = elemento.remarcador.cuenta.nombre;
						objeto.nodo = elemento.remarcador.nodo;
						tabla.addRowData(objeto.id, objeto);
						$('#editar-'+objeto.id).click(function() {
							var elemento = tabla.jqGrid('getRowData', objeto.id);
							var modalEditar = $('#modal-editar-remarcador');
							modalEditar.dialog({
								modal: true,
								width: 450,
								buttons: {
									Editar: function() {
										var nombre = $('#txt-nombre');
										var local = $('#txt-local');
										var tablero = $('#txt-tablero');
										var numeroMedidor = $('#txt-numero-medidor');
										var centroCosto = $('#cmb-editar-centro-costo');
										var cuenta = $('#cmb-editar-cuenta');
										var nodo = $('#txt-nodo');
										var observacion = $('#txt-observacion');
										var params = {
												id: objeto.id,
												nombre: nombre.val(),
												local: local.val(),
												tablero: tablero.val(),
												numeroMedidor: numeroMedidor.val(),
												centroCosto: centroCosto.val(),
												cuenta: cuenta.val(),
												nodo: nodo.val(),
												observacion: observacion.val()
										};
										$.ajaxMsgPostJSON('Editando remarcador...', 'main.htm?perform=editarRemarcador', {remarcador: JSON.stringify(params)}, function(json) {});
									},
									Cerrar: function() {
										$(this).dialog("close");
										modalEditar.empty();//TODO
									}
								},
								open: function(event, ui) {
									modalEditar.html(
										'<table>' +
										'<tr><td>Nombre:</td><td><input class="disabled" type="text" id="txt-nombre" value="'+elemento.nombre+'"></td></tr>'+
										'<tr><td>Local:</td><td><input class="disabled" type="text" id="txt-local" value="'+elemento.local+'"></td></tr>'+
										'<tr><td>Tablero:</td><td><input class="disabled" type="text" id="txt-tablero" value="'+elemento.tablero+'"></td></tr>'+
										'<tr><td>Nº medidor:</td><td><input class="disabled" type="text" id="txt-numero-medidor" value="'+elemento.numeroMedidor+'"></td></tr>'+
										'<tr><td>Centro costo:</td><td><select class="disabled" id="cmb-editar-centro-costo">'+$('#cmb-centro-costo').html()+'</select></td></tr>'+
										'<tr><td>Cuenta:</td><td><select class="disabled" id="cmb-editar-cuenta">'+$('#cmb-cuenta').html()+'</select></td></tr>'+
										'<tr><td>Nodo:</td><td><input type="text" id="txt-nodo" value="'+elemento.nodo+'"></td></tr>'+
										'<tr><td>Observaci&oacute;n:</td><td><input class="disabled" type="text" id="txt-observacion" value="'+elemento.observacion+'"></td></tr>'+
										'</table>'
									);
									$('#cmb-editar-centro-costo option:contains("'+elemento.centroCosto+'")').attr('selected', true);
									$('#cmb-editar-cuenta option:contains("'+elemento.cuenta+'")').attr('selected', true);
									if(perfil.val() == 'ADMINISTRADOR_CENCOSUD_1')
										$(".disabled#cmb-editar-centro-costo").attr('disabled', true);
									else if(perfil.val() == 'ADMINISTRADOR_CENCOSUD_2')
										$(".disabled").attr('disabled', true);
								}
							});
						});
					});
				});
			}
		});
	});
})(jQuery);