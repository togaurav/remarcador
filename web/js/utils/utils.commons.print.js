var textPrint_;
var winPrint_;

/**
 * retorna el texto HTML para impresion
 * @return
 */
function getTextPrint() {
	return textPrint_;
}

/**
 * cierra la ventana con el contenido de impresion.
 * @return
 */
function closeWinPrint() {
	if (winPrint_) winPrint_.close(); 
	winPrint_ = undefined;
}

/**
 * Solicita la impresion del contenido HTML pasado como parametro.
 * @param html
 * @return
 */
function printHTML(html, tpl) {
	
	textPrint_ = html;
	if (tpl) {
		winPrint_ = window.open(tpl,"","top=0,left=0,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=640,height=600");
	} else {
		winPrint_ = window.open("print.html","","top=0,left=0,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=640,height=480");
	}
	//winPrint_.resizeTo(1,1);
	//winPrint_.moveTo(screen.width, screen.height);
	// setTimeout('closeWinPrint();',3000);
}