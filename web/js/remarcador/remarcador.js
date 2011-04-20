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
		/**
		 * 
		 */
		function editarFila(cellvalue, options, rowObject) {
			return '<div align="center"><img src="images/editar.jpeg" id="editar-'+rowObject.id+'"></div>';
		}
		
		var perfil = $('#hdn-perfil-usuario');
		var noEsEditable = perfil.val() == 'OPERADOR' ? true : false;
		var hdnFechaInicial = $('#hdn-fecha-inicial');
		var hdnFechaFinal = $('#hdn-fecha-final');
		var tabla = $("#tbl-remarcador");
		var modalErrores = $('#modal-lista-errores');
		var divInfoResultadoBusqueda = $('#div-info-resultado-buesqueda');
		var divErroresResultadoBusqueda = $('#div-errores-resultado-buesqueda');
		var remarcadores = [];
		var datosBitacora = {};
		var idUsuario = $('#hdn-id-usuario');

		/**
		 * 
		 */
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
		
		/**
		 * 
		 */
		$("#txt-fecha-final").datepicker({
			changeYear: true,
			changeMonth: true,
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true
		});

		/**
		 * 
		 */
		tabla.jqGrid({
			datatype: "local",
		   	colNames: ['Editar', 'Nombre', 'Local', 'Tablero', 'N\xB0 medidor', 
		   	           'C&aacute;lculo medici&oacute;n (kWh)', 'Centro de costo', 'Cuenta', 'Nodo', 'Observaci&oacute;n', 'Multiplicador'],
		   	colModel: [
		   		{name: 'id', index: 'id', hidden: noEsEditable, formatter: editarFila, width: 50, sortable: false},
		   		{name: 'nombre', index: 'nombre', width: 300},
		   		{name: 'localRemarcador', index: 'localRemarcador', width: 70},
		   		{name: 'tablero', index: 'tablero', width: 80},
		   		{name: 'numeroMedidor', index: 'numeroMedidor', width: 110},
		   		{name: 'calculoMedicion', index: 'calculoMedicion'},
		   		{name: 'centroCosto', index: 'centroCosto'},
		   		{name: 'cuenta', index: 'cuenta', width: 80},
		   		{name: 'nodo', index: 'nodo', width: 80},
		   		{name: 'observacion', index: 'observacion'},
		   		{name: 'multiplicador', index: 'multiplicador', hidden: true}
		   	],
		   	pager: '#div-opciones-pagina',
		    caption: "Remarcadores",
		    width: 960,
		    height: '100%',
		    subGrid: true,
		    viewrecords: true,
		    afterInsertRow: function(rowid, rowdata, rowelem) {editarRemarcador(rowid);},
		    subGridRowExpanded: function(subgrid_id, row_id) {
		    	var remarcadorSeleccionado = tabla.jqGrid('getRowData', row_id);
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
					var detalleRemarcador = [];
					$(json.resultado).each(function(indice, elemento) {
						var objeto = {};
						objeto.indice = indice + 1;
						objeto.fecha_ts = timestampToStringDate(elemento.fecha_ts);
						objeto.dato_bigint = parseInt(elemento.dato_bigint) * parseInt(remarcadorSeleccionado.multiplicador);
						detalleRemarcador.push(objeto);
					});
					
					$("#"+subgrid_table_id).jqGrid({
						data: detalleRemarcador,
						datatype: "local",
						colNames: ['Fecha de medici&oacute;n', 'Medici&oacute;n (kWh)'],
						colModel: [
							{name: "fecha_ts", index: "fecha_ts"},
							{name: "dato_bigint", index: "dato_bigint"}
						],
						height: 'auto',
						rowNum: 20,
					   	pager: pager_id,
					    caption: "Detalle",
					    viewrecords: true,
					    width: 600
					});
					tabla.jqGrid('navGrid', '#'+pager_id, {edit: false, add: false, del: false})
					.navButtonAdd('#'+pager_id, {
						caption: 'Exportar CSV',
						onClickButton: function() {
							var titulos = 'N\xB0, Fecha medici\xF3n, Dato\r\n';
							var data = Base64.encode(DownloadJSON2CSV(detalleRemarcador, titulos));
							requestBinDoc(getConfExportCVS({nombreArchivo: 'detalle-remarcador', data: data}));
						}
					});
					$('.ui-icon-search').remove();
					$('.ui-icon-refresh').remove();
				});
			}
		});

		/**
		 * 
		 */
		$('#btn-buscar-remarcador').click(function(event) {
			remarcadores = [];
			var fechaInicial = $("#txt-fecha-inicial");
			var horaFechaInicial = $('#cmb-horas-lectura-inicial');
			var minutosFechaInicial = $('#cmb-minutos-lectura-inicial');
			var fechaFinal = $("#txt-fecha-final");
			var horaFechaFinal = $('#cmb-horas-lectura-final');
			var minutosFechaFinal = $('#cmb-minutos-lectura-final');
			var centroCosto = $('#cmb-centro-costo');
			var cuenta = $('#cmb-cuenta');
			var listaErrores = '<ul>';
			
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
			modalErrores.html(listaErrores);
			if(modalErrores.find('li').length != 0) {
				modalErrores.dialog({
					modal: true,
					resizable: false,
					width: 450,
					buttons: {
						Cerrar: function() {
							$(this).dialog("close");
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
				var infoFechaLecturaInicial = params.fechaInicial + ' - ' + params.horaFechaInicial + ':' + params.minutosFechaInicial;
				var infoFechaLecturaFinal = params.fechaFinal + ' - ' + params.horaFechaFinal + ':' + params.minutosFechaFinal;
				divInfoResultadoBusqueda.html('<div>Fecha lectura inicial: <b>'+infoFechaLecturaInicial+'</b></div><div>Fecha lectura Final: <b>'+infoFechaLecturaFinal+'</b></div>');
				$.ajaxMsgPostJSON('Cargando...', 'main.htm', params, function(json) {
					datosBitacora = params;
					hdnFechaInicial.val(json.timeInMillisFechaInicial);
					hdnFechaFinal.val(json.timeInMillisFechaFinal);
					var resultados = json.resultado;
					if(resultados.length != 0) {
						divErroresResultadoBusqueda.empty();
						$(resultados).each(function(indice, elemento) {
							var multiplicador = elemento.remarcador.multiplicador;
							var calculoMedicion = 'Sin dato';
							var medicionInicial = elemento.remarcadorIncial == undefined ? 'Sin dato' : parseInt(elemento.remarcadorIncial.dato_bigint);
							var medicionFinal = elemento.remarcadorFinal == undefined ? 'Sin dato' : parseInt(elemento.remarcadorFinal.dato_bigint);
							if(medicionInicial != 'Sin dato' && medicionFinal != 'Sin dato') {
								calculoMedicion = (medicionFinal * multiplicador) - (medicionInicial * multiplicador);
								calculoMedicion = calculoMedicion.toString().indexOf('.') > -1 ? new Number(calculoMedicion).toFixed(0) : calculoMedicion;
							}
							var objeto = {};
							objeto.id = elemento.remarcador.id;
							objeto.nombre = elemento.remarcador.nombre;
							objeto.localRemarcador = elemento.remarcador.localRemarcador;
							objeto.tablero = elemento.remarcador.tablero;
							objeto.numeroMedidor = elemento.remarcador.numeroMedidor;
							objeto.calculoMedicion = calculoMedicion;
							objeto.centroCosto = elemento.remarcador.centroCosto.nombre;
							objeto.cuenta = elemento.remarcador.cuenta.nombre;
							objeto.nodo = elemento.remarcador.nodo;
							objeto.observacion = elemento.remarcador.observacion;
							objeto.multiplicador = elemento.remarcador.multiplicador;
							tabla.addRowData(objeto.id, objeto);
							objeto.id = indice + 1;
							remarcadores.push(objeto);
						});
					} else {
						divErroresResultadoBusqueda.html('<b>No existen registros para la b&uacute;squeda realizada.<b>');
					}
				});
			}
		});
		
		/**
		 * 
		 */
		var editarRemarcador = function(id) {
			$('#editar-'+id).click(function() {
				var elemento = tabla.jqGrid('getRowData', id);
				var modalEditar = $('#modal-editar-remarcador');
				modalEditar.dialog({
					modal: true,
					resizable: false,
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
									id: id,
									nombre: nombre.val(),
									localRemarcador: local.val(),
									tablero: tablero.val(),
									numeroMedidor: numeroMedidor.val(),
									nodo: nodo.val(),
									observacion: observacion.val()
							};
							$.ajaxMsgPostJSON('Editando remarcador...', 'main.htm?perform=editarRemarcador', 
									{remarcador: JSON.stringify(params), idCentroCosto: centroCosto.val(), idCuenta: cuenta.val()}, 
									function(json) {
										tabla
										.setCell (params.id, 2, params.nombre)
										.setCell (params.id, 3, params.localRemarcador)
										.setCell (params.id, 4, params.tablero)
										.setCell (params.id, 5, params.numeroMedidor)
										.setCell (params.id, 7, $("#cmb-editar-centro-costo option:selected").text())
										.setCell (params.id, 8, $("#cmb-editar-cuenta option:selected").text())
										.setCell (params.id, 9, params.nodo)
										.setCell (params.id, 10, params.observacion);
									});
						},
						Cerrar: function() {
							$(this).dialog("close");
						}
					},
					open: function(event, ui) {
						modalEditar.html(
							'<table>' +
							'<tr><td>Nombre:</td><td><input class="disabled" type="text" id="txt-nombre" value="'+elemento.nombre+'"></td></tr>'+
							'<tr><td>Local:</td><td><input class="disabled" type="text" id="txt-local" value="'+elemento.localRemarcador+'"></td></tr>'+
							'<tr><td>Tablero:</td><td><input class="disabled" type="text" id="txt-tablero" value="'+elemento.tablero+'"></td></tr>'+
							'<tr><td>N\xB0 medidor:</td><td><input class="disabled" type="text" id="txt-numero-medidor" value="'+elemento.numeroMedidor+'"></td></tr>'+
							'<tr><td>Centro costo:</td><td><select class="disabled" id="cmb-editar-centro-costo">'+$('#cmb-centro-costo').html()+'</select></td></tr>'+
							'<tr><td>Cuenta:</td><td><select class="disabled" id="cmb-editar-cuenta">'+$('#cmb-cuenta').html()+'</select></td></tr>'+
							'<tr><td>Nodo:</td><td><input type="text" id="txt-nodo" value="'+elemento.nodo+'"></td></tr>'+
							'<tr><td>Observaci&oacute;n:</td><td><input class="disabled" type="text" id="txt-observacion" value="'+elemento.observacion+'"></td></tr>'+
							'</table>'
						);
						$('#cmb-editar-centro-costo option:contains("Seleccione")').remove();
						$('#cmb-editar-cuenta option:contains("Seleccione")').remove()
						$('#cmb-editar-centro-costo option:contains("'+elemento.centroCosto+'")').attr('selected', true);
						$('#cmb-editar-cuenta option:contains("'+elemento.cuenta+'")').attr('selected', true);						
						if(perfil.val() == 'ADMINISTRADOR_CENCOSUD_1') {
							$(".disabled#cmb-editar-centro-costo").attr('disabled', true);
							$('#txt-nodo').attr('disabled', true);
						} else if(perfil.val() == 'ADMINISTRADOR_CENCOSUD_2')
							$(".disabled").attr('disabled', true);
					}
				});
			});
		}
		
		/**
		 * 
		 */
		$('#btn-exportar-remarcadores-csv').click(function() {
			if(remarcadores.length==0) {
				modalErrores.dialog({
					modal: true,
					resizable: false,
					width: 450,
					buttons: {
						Cerrar: function() {
							$(this).dialog("close");
						}
					}
				}).html('No existen registros a exportar. Debe realizar una b&uacute;squeda de remarcadores.');
				return false;
			}
			var titulos = 'N\xB0, Nombre, Local, Tablero, N\xB0 medidor, C\xE1lculo medici\xF3n (kWh), Centro de costo, Cuenta, Nodo, Observaci\xF3n\r\n';
			var data = Base64.encode(DownloadJSON2CSV(remarcadores, titulos));
			requestBinDoc(getConfExportCVS({nombreArchivo: 'remarcadores', data: data}));
		});
		
		/**
		 * 
		 */
		var getConfExportCVS = function(opt) {
			return {action: 'main.htm', 
					params: {
					perform: 'exportarCSV', 
					data: opt.data,
					nombreArchivo: opt.nombreArchivo,
					idUsuario: idUsuario.val(), 
					fechaLecturaInicial: datosBitacora.fechaInicial,
					fechaLecturaFin: datosBitacora.fechaFinal,
					horaLecturaInicial: datosBitacora.horaFechaInicial.toString() + datosBitacora.minutosFechaInicial.toString(),
					horaLecturaFin: datosBitacora.horaFechaFinal.toString() + datosBitacora.minutosFechaFinal.toString()
				}
			}
		}
	});
})(jQuery);