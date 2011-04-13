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

/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};