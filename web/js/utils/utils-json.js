/**
 * @autor vutreras
 */

//@jsfn R121.SectionMgr.params2json(parray)
//Converts parameter array received from serializing the form into JSON

//agrego esto para extender la libreria json2.js y agregarle la funcion "params2json"
if (!this.JSON) {
  JSON = {};
}

/**
* transforma un array a objeto.
* @param {array} - d - array de valores
* @return objeto
*/
JSON.params2json = function(d) {
	if (d.constructor != Array) {
	    return d;
	}
	var data={};
	for(var i=0;i<d.length;i++) {
		     if (typeof data[d[i].name] != 'undefined') {
		         if (data[d[i].name].constructor!= Array) {
		             data[d[i].name]=[data[d[i].name],d[i].value];
		         } else {
		             data[d[i].name].push(d[i].value);
		         }
		     } else {
		         data[d[i].name]=d[i].value;
		     }
	}
	return data;
};

/**
* transforma un form a un objeto
* @param {object} - form - formulario
* @return objeto
*/
JSON.form2json = function(form) {
	return JSON.params2json(form.serializeArray());
};

/**
* transforma un form a json
* @param {object} - form - formulario
* @return formulario en json
*/
JSON.form2json_stringify = function(form) {
	return JSON.stringify(JSON.params2json(form.serializeArray()));
};

/**
 * JSON.stringify simplificado
 * @param obj
 * @return
 */
function toJson(obj) {
	return (obj != undefined) ? JSON.stringify(obj) : undefined;
}

/**
 * realiza un eval de un json
 * @param json
 * @return
 */
function jsonToObject(json) {
	return eval('(' + json + ')');
}

/*
 * Clona todos los atributos de un objeto ademas de las funciones miembros del objeto
 */
JSON.clone = function clone(o) {
	function OneShotConstructor(){}
	OneShotConstructor.prototype = o;
	return new OneShotConstructor();
};