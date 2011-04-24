var parametrosRequestBin;
var winBin;
var confBin;

/**
 * retorna los parametros
 * @return
 */
function getParametrosRequestBin() {
	return parametrosRequestBin;
}

/**
 * cierra la ventana
 * @return
 */
function closePerformXLS() {
	 try {
		if(winBin) winBin.close(); 
		winBin = undefined;
	 } catch(e) {
		 
	 }
}

/**
 * Solicita un request POST para archivos binarios.
 * @param html
 * @return
 */
function requestBinDoc(conf) {
	parametrosRequestBin = conf;
	winBin = window.open("bin.html","","width=100,height=100,scrollbars=NO");
	if (conf.success) {
		if (!conf.timeOutSuccess) {
			conf.timeOutSuccess = 3000;
		}	
		setTimeout('getParametrosRequestBin().success();', conf.timeOutSuccess);
	}
	if (!conf.timeOutClose) {
		conf.timeOutClose = 60000;
	}	
	setTimeout('closePerformXLS();', conf.timeOutClose);
}