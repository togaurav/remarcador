/**
 * @autor vutreras
 */

/**
 * retorna true si el string es un boolean 'true'
 */
String.prototype.bool = function() {
    return (/^true$/i).test(this);
};

/**
 * retorna true si el string esta vacio
 */
String.prototype.blank = function() {
	return /^\s*$/.test(this);
}

/**
 * retorna true si un string comiensa con un patron
 * @param {string} - str - patron de busqueda
 * @return true o false
 */
String.prototype.startsWith = function(str) {
	return (this.match("^"+str)==str);
}

/**
 * retorna true si un string termina con un patron
 * @param {string} - str - patron de busqueda
 * @return true o false
 */
String.prototype.endsWith = function(str) {
	return (this.match(str+"$")==str);
}

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
String.prototype.reverse = function(){
	var splitext = this.split("");
	var revertext = splitext.reverse();
	return revertext.join("");
}

/**
 * elimina los espacios de los extremos de un string.
 * @return objeto string sin espacios
 */
String.prototype.scapeTagsHTML = function() {
	return (this.replace(/</gi, "&lt;").replace(/>/gi, "&gt;"));
}

/**
 * Realiza una busqueda de string - utiliza el estandar sql que permite utilizar el caracter %
 * @param {string} - str - string que se desea buscar
 * @param {boolean} - casesve - true: case-sensitive
 * @example
 *  	var criterio = '%tinuum%';
 *  	
 *  	console.info( criterio.inStr('Hola continuum') ); 	//resultado true
 *  
 *  	var criterio = '%tinuu';
 *  	
 *  	console.info( criterio.inStr('Hola continuum') ); 	//resultado false
 *  @return true: se encontro el valor, false: no se encontro el valor
 */
String.prototype.inStr = function(str, casesve){
	
	if (str) {
	
		str = (!casesve) ? str.toString().toLowerCase() : str.toString();
		var this_ = this.toString();
		
		if (this_.startsWith('%') && this_.endsWith("%")) { //start and end
			var s = this_.substring(1,this_.length-1);
			var ok = str.search((!casesve) ? s.toLowerCase() : s) != -1;
			return ok;
		}else if (this_.startsWith('%')) {	//start
			var s = this_.substring(1);
			var ok = str.endsWith((!casesve) ? s.toLowerCase() : s);
			return ok;
		} else if (this_.endsWith('%')) { //end
			var s = this_.substring(0,this_.length-1);
			var ok = (str.startsWith((!casesve) ? s.toLowerCase() : s));
			return ok;
		} else {
			var s = this_;
			var ok = str.search((!casesve) ? s.toLowerCase() : s) != -1;
			return ok;
		}
		
	} else {
		return false;
	}
}

/**
 * escapa el string a HTML
 */
String.prototype.scapeToHTML = function() {
	/*
	var stmp = ''; 	
	var s = this;

	for(var i = 0; i < s.length; i++) {

		var c = s.charAt(i);

		if (c == '\u00F1') {
			stmp+='&ntilde;';
		} else if (c == '\u00D1') {
			stmp+='&Ntilde;';	
		} else if (c === 'á') {
			stmp+='&aacute;';
		} else if (c === 'Á') {
			stmp+='&Aacute;';		
		} else if (c === 'é') {
			stmp+='&eacute;';
		} else if (c === 'É') {
			stmp+='&Eacute;';			
		} else if (c === 'í') {
			stmp+='&iacute;';
		} else if (c === 'Í') {
			stmp+='&Iacute;';
		} else if (c === 'ó') {
			stmp+='&oacute;';
		} else if (c === 'Ó') {
			stmp+='&Oacute;';
		} else if (c === 'ú') {
			stmp+='&uacute;';
		} else if (c === 'Ú') {
			stmp+='&Uacute;';			
		} else {
			stmp+=c;
		}
	}
	return stmp;*/
	/*
	Á 	&Aacute; 	\u00C1
	á 	&aacute; 	\u00E1
	É 	&Eacute; 	\u00C9
	é 	&eacute; 	\u00E9
	Í 	&Iacute; 	\u00CD
	í 	&iacute; 	\u00ED
	Ó 	&Oacute; 	\u00D3
	ó 	&oacute; 	\u00F3
	Ú 	&Uacute; 	\u00DA
	ú 	&uacute; 	\u00FA
	Ü 	&Uuml; 		\u00DC
	ü 	&uuml; 		\u00FC
	Ñ 	&Ntilde; 	\u00D1
	ñ 	&ntilde; 	\u00F1
	*/
	return this
	.replace(/\u00C1/gi,'&Aacute;')
	.replace(/\u00E1/gi,'&aacute;')
	.replace(/\u00C9/gi,'&Eacute;')
	.replace(/\u00E9/gi,'&eacute;')
	.replace(/\u00CD/gi,'&Iacute;')
	.replace(/\u00ED/gi,'&iacute;')
	.replace(/\u00D3/gi,'&Oacute;')
	.replace(/\u00F3/gi,'&oacute;')
	.replace(/\u00DA/gi,'&Uacute;')
	.replace(/\u00FA/gi,'&uacute;')
	.replace(/\u00DC/gi,'&Uacute;')
	.replace(/\u00FC/gi,'&uacute;')
	.replace(/\u00D1/gi,'&Ntilde;')
	.replace(/\u00F1/gi,'&ntilde;');
}

/**
 * 
 * @param {Object} obj
 */
String.prototype.populate = function(obj) {
	var str = this;
	for(var property in obj) {
	    var v = '#' + property + '#';
	    str = eval('str.replace(/' + v + '/g,obj[property])');
	}
	return str;
}

/**
 * 
 * @param {Object} str
 * @param {Object} casesve
 */
Number.prototype.inStr = function(str, casesve){
	return this.toString().inStr(str, casesve);
}

 /**
  * Clase de utilidades de strings
  */
StringUtils = {
	
		/**
		 * StringUtils.isEmpty(undefined) = true
	     * StringUtils.isEmpty("") = true
      	 * StringUtils.isEmpty(" ") = false
      	 * StringUtils.isEmpty("bob") = false
      	 * StringUtils.isEmpty(" bob ") = false
		 * @param {Object} s
		 */
		isEmpty: function(s) {
			return s == undefined || s.length == 0;
		},
		
		/**
		 * StringUtils.isNotEmpty(undefined) = false
	     * StringUtils.isNotEmpty("") = false
      	 * StringUtils.isNotEmpty(" ") = true
      	 * StringUtils.isNotEmpty("bob") = true
      	 * StringUtils.isNotEmpty(" bob ") = true
		 * @param {Object} s
		 */
		isNotEmpty: function(s) {
			return s != undefined && s.length > 0;
		},
		
		/**
		 * StringUtils.isBlank(undefined) = true
	     * StringUtils.isBlank("") = true
      	 * StringUtils.isBlank(" ") = true
      	 * StringUtils.isBlank("bob") = false
      	 * StringUtils.isBlank(" bob ") = false
		 * @param {Object} s
		 */
		isBlank: function(s) {
			if (s == undefined) {
				return true;
			} else {	
				return s.blank();
			}
		},
		
		/**
		 * StringUtils.isNotBlank(undefined) = false
	     * StringUtils.isNotBlank("") = false
      	 * StringUtils.isNotBlank(" ") = false
      	 * StringUtils.isNotBlank("bob") = true
      	 * StringUtils.isNotBlank(" bob ") = true
		 * @param {Object} s
		 */
		isNotBlank: function(s) {
			return !StringUtils.isBlank(s);
		},
		
		/**
		 * retorna true si un string comienza con un patron
		 * @param {object} - obj - objeto string
		 * @param {string} - str - patron de busqueda
		 * @return true o false
		 */
		startsWith: function(obj, str) {
			return (obj.toString().match("^"+str)==str);
		},

		/**
		 * retorna true si un string termina con un patron
		 * @param {object} - obj - objeto string
		 * @param {string} - str - patron de busqueda
		 * @return true o false
		 */
		endsWith: function(obj, str) {
			return (obj.toString().match(str+"$")==str);
		},

		/**
		 * elimina los espacios de los extremos de un string.
		 * @param {object} - obj - objeto string
		 * @return objeto string sin espacios
		 */
		trim: function(obj) {
			if (obj == undefined){
				return undefined;
			} else {
				var s = obj.toString();
				return (s.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
			}
		},
		
		/**
		 * escapa los tags html de un string.
		 * @param {object} - obj - objeto string
		 * @return objeto string sin espacios
		 */
		scapeTagsHTML: function(obj) {
			if (obj == undefined) {
				return undefined;
			} else {
				if (StringUtils.isNotBlank(obj)){
					var s = obj.toString();
					return (s.replace(/</gi, "&lt;").replace(/>/gi, "&gt;"));
				} else {
					return obj;
				}
			}
		}
		
		
};