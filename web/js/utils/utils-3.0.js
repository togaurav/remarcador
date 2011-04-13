
var REGX_NUMERO_TELEFONICO = /^[0-9+-]$/;

/**
* Definicion de las funciones de log.
*/
if (!this.console) {
  var console = {
  	info: function(s){
			var text = '';
			for( var i = 0; i < arguments.length; i++ ) {
				var arg = arguments[i];
				if(typeof(arg) != "object"){
					text+= arg + ', ';
				}else{
					text+= JSON.stringify(arg) + ', ';
				}
			}
			if (window['log']){ log.info(text); } //else { alert('INFO: ' + text); }
		},	
  	debug: function(s){
			var text = '';
			for( var i = 0; i < arguments.length; i++ ) {
				var arg = arguments[i];
				if(typeof(arg) != "object"){
					text+= arg + ', ';
				}else{
					text+= JSON.stringify(arg) + ', ';
				}
			}
			if (window['log']) { log.debug(text); } //else { alert('DEBUG: ' + text); }
		},	
  	warn: function(s){
			var text = '';
			for( var i = 0; i < arguments.length; i++ ) {
				var arg = arguments[i];
				if(typeof(arg) != "object"){
					text+= arg + ', ';
				}else{
					text+= JSON.stringify(arg) + ', ';
				}
			}
			if (window['log']) { log.warn(text); } //else { alert('WARN: ' + text); }
		},	
  	error: function(s){
			var text = '';
			for( var i = 0; i < arguments.length; i++ ) {
				var arg = arguments[i];
				if(typeof(arg) != "object"){
					text+= arg + ', ';
				}else{
					text+= JSON.stringify(arg) + ', ';
				}
			}
			if (window['log']) { log.error(text); } //else { alert('ERROR: ' + text); }
		}
  }
}

// CONSTANTES

var cantidad_registros_pagina = 20;

/**
 * 
 * @param {Object} id
 */
function noneDiv(id) {
	$(id).style.display = 'none';
}

/**
 * 
 * @param {Object} id
 */
function inlineDiv(id) {
	$(id).style.display = 'inline';
}

/**
 * cierra todos los divs con clase
 * @param {Object} clazz
 */
function noneDivs(clazz) {
	// cerramos todos los divs con class 'divBlock'
	var divC = 'div.' + clazz;
	$$(divC).each(
		function(id) {
			$(id).style.display = 'none';
		}
	)
}

/**
 * agrega una fila con el objeto datos (array) y le coloca el clazz como className
 * @param {Object} data
 * @param {Object} className
 * @param {Object} align
 */
function addRow(data, className, align) {

    // creamos una fila
    var row = document.createElement("tr");

    for (var i = 0; i < data.length; i++) {
    
    	// creamos un <td> para cada columna de datos
    	// colocamos el valor del td
    	// y ponemos el td al final del <tr> creado
        var cell = document.createElement("td");
        
        // seteamos el class como dataRow y center
        cell.className = className;
        cell.align = align;
        
        var cellText = document.createTextNode(data[i]);
        cell.appendChild(cellText);
        row.appendChild(cell);
        
	}
	return row;
}

/**
 * permite popular una tabla con un objeto consulta
 * @param {Object} tableId
 * @param {Object} consulta
 * @param {Object} opciones
 */
function populateTable(tableId, consulta, opciones) {

    // obtenermos la referencia a la tabla
    var table = $(tableId);

    // obtenemos la referencia al body de la tabla
    var tbody = table.tBodies[0];

	// agregamos la fila de encabezados
    tbody.appendChild(addRow(consulta.columnas, opciones.columnClassName, opciones.columnAlign));

    // sumamos las filas de datos
    for (var j = 0; j < consulta.valores.length ; j++) {

	    tbody.appendChild(addRow(consulta.valores[j], opciones.dataClassName, opciones.dataAlign));

    }

}

/**
 * limpia una tabla completa
 * @param {Object} tableId
 */
function clearTable(tableId) {
	
	var table = $(tableId);
	var tbody = table.tBodies[0];
	// para cada fila
	var rowsLength = tbody.rows.length;
	for(var i = 0; i < rowsLength; i++) {
		tbody.deleteRow(0);
	}
	
}

/**
 * 
 * @param {Object} box
 */
function clearBox(box) {

	while (box.firstChild) {
		box.removeChild(box.firstChild);
	}
	
}

/**
 * rellena un objeto select desde un objeto
 * @param {Object} receiver
 * @param {Object} dom
 * @param {Object} selectedCode
 */ 
function fillSelectFromObject(receiver, dom, selectedCode) {

	var box = $(receiver);
	
	// limpiamos el box
	clearBox(box);
	
	// dominios dentro del objeto
	var dominios = dom.dominios;
	
	for( var i = 0; i < dominios.length; i++ ) {
		
		// creamos un opt group para el encabezado
		var optG = document.createElement("OptGroup");
		optG.label = '--- ' + dominios[i].tipo + ' ---';
		
		box.appendChild(optG);

		// creamos los options para los valores de dominio
		var valores = dominios[i].valores;

		for (var j = 0; j < valores.length; j++) {
			
			var opt = document.createElement("Option");
			opt.value = dominios[i].tipo.toUpperCase().substring(0,1) + ';' + valores[j].codigo;
			opt.innerHTML = valores[j].descripcion;
			
			box.appendChild(opt);
			
			if (selectedCode.toUpperCase() == dominios[i].tipo.toUpperCase()) {
				opt.selected = true;
				//box.selectedIndex = opt.index;
			}
			
		}
	}

}

/**
 * busca un option dentro del select, 
 * retorna null si no lo encuentra
 * @param {Object} select
 * @param {Object} value
 */
function lookUpOptionByValue(select, value) {
	var result = null;
	var encontrado = false;
	
	for (var i = 0; !encontrado && i < select.options.length; i++) {
		// referenciamos el option
		var opt = select.options[i];
		// veamos si lo encontramos
		if (opt.value == value) {
			encontrado = true;
			result = opt;
		}
	}
	
	return result;
}

/**
 * agrega una option a un select si esta no existe
 * @param {Object} selId
 * @param {Object} valor
 * @param {Object} texto
 */
function agregaOptionToSelect(selId, valor, texto) {

	// referenciamos el select
	var sel = $(selId);

	if (null == lookUpOptionByValue(sel, valor)) {
		// agregamos el option
		var opt = document.createElement("option");
		opt.value = valor;
		opt.innerHTML  = texto;
		
		// lo agregamos al final
		sel.appendChild(opt);	
	} else {
		alert('Ya se encuentra en la lista!');
	}
}