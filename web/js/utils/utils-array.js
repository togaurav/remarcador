/**
 * @autor vutreras
 */


/**
 * Elimina un elemento de un array.
 * @see Array Remove - By John Resig (MIT Licensed)
 * @author John Resig (MIT Licensed) 
 */
Array.prototype.removeElement = function(from, to) {
	try {
		var rest = this.slice((to || from) + 1 || this.length);
		this.length = from < 0 ? this.length + from : from;
		return this.push.apply(this, rest);
	} catch(err) {
	}
};

/**
 * 	retorna el indice del elemento. Realiza diferentes tipos de comparaciones de acuerdo al tipo de dato
 	@param {object} - element - objeto que se desea buscar en el array
 	@param {function} - fcomparacion - funcion de comparacion
 	@example
	 	-- uso normal
	 	var arr = ['a', 'b', '4', 'h', 'l', 'm'];
	
		console.info(arr.indexOfElement('4'));
	 
	 	-- uso con objetos que tienen el atributo id
	 	var arr = [{id:'a'},{id:'b'},{id:'4'},{id:'h'},{id:'l'},{id:'m'}];
	
		console.info(arr.indexOfElement( {id: '4'} ));
		
		--uso con una funcion de comparacion
		var arr = [{id:'a'},{id:'b'},{id:'4'},{id:'h'},{id:'l'},{id:'m'}];
	
		var comp = function(element, el) {
			return element === el.id;
		}
		
		console.info(arr.indexOfElement('4'));
		
	@return indice del elemento en el array, si no encuentra se retorna -1.	
 * 
 */
Array.prototype.indexOfElement = function(element, fcomparacion) {
	var result = -1;
	if (this.length) {
		$.each(this, function (index, el) {
			
			if (fcomparacion) {
				if (fcomparacion(element, el)) {
					result = index;
					return;
				}
			} else {
				if (element.id && Number(element.id) === Number(el.id)) {
					result = index;
					return;
				} else if (element === el) {
					result = index;
					return;
				}
			}
		})
	}
	return result;
};

/**
 * Reemplaza un elemento en un array
 * @param {object} - element - elemento que se desea poner en el array
 * @param {number} - i - indice donde se desea reemplazar el elemento.
 */
Array.prototype.replaceElement = function(element, i) {
	
	if (!i) {
		i = this.indexOfElement(element);
	}
	
	if (i >= 0) {
		this[i] = element;
	}
};

/**
 * refresca un elemento en un array - si no existe lo agrega, si existe lo reeplaza
 * @param {object} - element - elemento que se desea poner en el array
 * @param {number} - i - indice donde se desea reemplazar el elemento.
 */
Array.prototype.refreshElement = function(element, i) {
	
	if (!i) {
		i = this.indexOfElement(element);
	}
	
	if (i >= 0) {
		this[i] = element;
	}else{
		this.push(element);
	}
};

/**
 * 
 * Clase de utilidades de arrays
 */
ArrayUtils = {
	
	/**
	 * comprueba si un array es vacio
	 * @param {Object} arr
	 */
	isEmpty: function(arr) {
		return arr == undefined || arr.length == 0;
	},
	
	/**
	 * comprueba si un array no es vacio
	 * @param {Object} arr
	 */
	isNotEmpty: function(arr) {
		return arr && arr.length && arr.length > 0;
	}
};