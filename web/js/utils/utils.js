/**
 * @autor vutreras
 */

/**
 * transforma un date a int en el formato (yyyyMMdd) ej: 20090526
 */
Date.prototype.toIntDate = function() {
	var d = this.getDate().toString();
	if (d.length == 1) {
		d = '0' + d;
	}
	var m = this.getMonth() + 1;
	m = m.toString();
	if (m.length == 1) {
		m = '0' + m;
	}
	return Number(this.getFullYear() + '' + m + '' + d);
}

/**
 * transforma un date a String en el formato (dd/mm/yyyy) ej: 20090526
 */
String.prototype.toFormatDate = function() {
	var fecha = this;
	var anio = fecha.substring(0,4);
	var mes = fecha.substring(4,6);
	var dia = fecha.substring(6);	
	
	return dia + '/' + mes + '/'+ anio;
}

/**
 * transforma un numero en el formato yyyymmdd ej: (20090526) en un objeto Date.
 * @param {Object} format
 */
Number.prototype.toDate = function(format) {
	var fecha = this.toString();
	return fecha.toDate(format);
}


/**
 * transforma un string en el formato yyyymmdd ej: (20090526) en un objeto Date.
 * @param {Object} format
 */
String.prototype.toDate = function(format) {
	var fecha = this.toString();
	if (!format) {
		var anio = parseInt(fecha.substring(0,4), 10);
		var mes = parseInt(fecha.substring(4,6), 10);
		var dia = parseInt(fecha.substring(6), 10);	
		var d = new Date();
		d.setFullYear(anio, mes, dia);
		return d;
	} else { 
		format = format.toLowerCase();
		if (format === 'yyyymmdd'){
			var anio = parseInt(fecha.substring(0,4), 10);
			var mes = parseInt(fecha.substring(4,6), 10) - 1;
			var dia = parseInt(fecha.substring(6), 10);	
			var d = new Date();
			d.setFullYear(anio, mes, dia);
			return d;
		} else if (format === 'dd/mm/yyyy'){
			var partes = fecha.split('/');
			var dia = parseInt(partes[0], 10);
			var mes = parseInt(partes[1], 10) - 1;
			var anio = parseInt(partes[2], 10);	
			var d = new Date();
			d.setFullYear(anio, mes, dia);
			return d;
		}  else {
			return undefined;
		}
	}
}

/**
 * 
 * Clase de utilidades de fechas
 */
DateUtils = {
	
	/**
	 * transforma un date a int en el formato (yyyyMMdd) ej: 20090526
	 * @param {Object} fecha
	 */
	toIntDate: function(fecha) {
		return fecha.toIntDate();
	},

	/**
	 * transforma un date a String en el formato (dd/mm/yyyy) ej: 20090526
	 * @param {Object} fecha
	 */
	toFormatDate: function(fecha) {
		return fecha.toString().toFormatDate();
	},

	/**
	 * transforma un string en el formato yyyymmdd ej: (20090526) en un objeto Date.
	 * @param {Object} fecha
	 * @param {Object} format
	 */
	toDate: function(fecha, format) {
		return fecha.toString().toDate();
	},
	
	/**
	 * calcula la diferencia en dias y segundos entre la fecha 1 y fecha2, NOTA: si la fecha1 es mayor a la fecha2 la diferencia es positiva pero si la fecha1 es menor a la fecha2 la diferencia es negativa
	 * @param {Object} fecha1
	 * @param {Object} fecha2
	 */
	diferencia: function(fecha1, fecha2) {
		var diferencia = fecha1.toString().toDate().getTime() - fecha2.toString().toDate().getTime();
		var dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));  
		var segundos = Math.floor(diferencia / 1000);
		return {dias: dias, segundos: segundos};
	}
};

/*
 * util para pasar un string en formato yyyymmdd a dd/mm/yyyy
 */
 function stringToStringDate(fecha) {
	 if (/^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/.test(fecha))
		 return fecha;
	 
	 if(fecha.blank() || fecha == 0) {
		 return '--';
	 } else {
		 anio = fecha.substring(0,4);
		 mes = fecha.substring(4,6);
		 dia = fecha.substring(6,8);
			
		 var fechaFormateada = dia + '/' + mes + '/' + anio;
			
		 return fechaFormateada;
	 }

}
 
function stringToStringHour(hour) {
	if (/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/.test(hour))
		 return hour;
	
	 if (hour.length < 6){
		 var ceros = '';
		 for (var i = 0; i < (6 - hour.length); i++)
			 ceros = ceros + '0';
		 hour = ceros + hour;
	 }
	 hora 	= hour.substring(0,2);
	 min	= hour.substring(2,4);
	 seg	= hour.substring(4,6);
	 return hora + ':' + min + ':' + seg;
 }
 
 /*
  * obtiene fecha actual
  */
 function getFechaActual(contador) {
	 
     if(contador==undefined)
         contador={}            
         
	 var fecha=new Date();
	 var diames=fecha.getDate();
	 var mes=fecha.getMonth() +1 ;
	 var ano= (contador.masAno==undefined) ? fecha.getFullYear() : fecha.getFullYear() + contador.masAno;
	 
	 return diames + "/" + mes + "/" + ano;
	 
};

/**
 * elimina los espacios de los extremos de un string.
 * @return objeto string sin espacios
 */
String.prototype.trim = function() {
	return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
}

/**
 * 
 */
function DownloadJSON2XLS(objArray) {
	var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
	var str = "<table>";
	for(var i = 0; i < array.length; i++) {
		var line = '<tr>';
		for(var index in array[i]) {
			line += '<td>' + array[i][index] + '</td>';
		}
		line.slice(0,line.Length-1);
		str += line + '</tr>';
	}
	str += "</table>";
	return str;
}

/**
 * 
 */
function DownloadJSON2CSV(objArray, titulos) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = titulos;
    for(var i = 0; i < array.length; i++) {
        var line = "";
    	for(var index in array[i]) {
			var dato = array[i][index];
    		if(index != 'multiplicador') {
    			if(dato != undefined && dato.toString().trim() != "")
        			line += array[i][index] + "; ";
        		else
        			line += "Sin dato; ";	
    		}
        }
        str += line.slice(0, -2) + "\r\n";
	}
    return str;
}

/**
 * 
 */
function timestampToStringDate(cellvalue, options, rowObject) {
	var date = new Date((parseInt(cellvalue) * 1000) + (14400000));
    var _mes = date.getMonth()+1;
    var _dia = date.getDate();
    var _anio = date.getFullYear();
    var _horas = date.getHours().toString() == "0" ? "00" : date.getHours().toString();;
    var _minutos = date.getMinutes().toString() == "0" ? "00" : date.getMinutes().toString();
    return _dia+"/"+_mes+"/"+_anio + " - " + _horas + ":" + _minutos;
}