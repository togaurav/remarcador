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
		$('#btn-continuar').click(function() {
			var usuario = $('#txt-usuario');
			var password = $('#txt-password');
			var erroresAcceso = $('#errores-acceso');
			var listaErrores = '<ul id="lista-errores">';
			
			if(usuario.val() == "")
				listaErrores += '<li>Debe ingresar su nombre de usuario</li>';
			if(password.val() == "")
				listaErrores += '<li>Debe ingresar su password de usuario</li>';
			
			listaErrores += '</ul>';
			erroresAcceso.html(listaErrores);
			
			if($('#lista-errores').find('li').length != 0)
				return false;
			
			$.post('main.htm?perform=validarLogin', {usuario: usuario.val(), password: password.val()}, function(json) {
				if(json.ok) {
					$('#form-login').submit();
				} else {
					erroresAcceso.html('<ul><li>Usted no tiene los permisos suficientes para acceder al sistema.</li></ul>');
				}
			}, 'json');
		});
	});
})(jQuery);