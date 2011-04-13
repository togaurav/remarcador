/*
 * Creado 16/02/2007
 *
 * $Id$
 *
 * Copyright jrodriguez (2006). All rights reserved.
 * Retain all ownership and intellectual property rights in
 * the programs and any source code.
 */
package cl.wamtech.remarcador.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * @author jars - <a href="mailto:jorge.rodriguez.suarez at gmail.com">Jorge Al. Rodriguez Suarez</a>
 *
 */
public class AccessFilter implements Filter {

	// class log
	protected static final Log LOG = LogFactory.getLog(AccessFilter.class);
	
	// servicios del scai
	//private SCAIWrapper scai = new SCAIWrapper(new ServiciosAccesoWebCacheImpl());
	
	// servicios del scai
//	private SCAIWrapper scai;


	/**
	 * 
	 */
	public AccessFilter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// blanco a proposito
	}
	
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain fc) throws IOException, ServletException {
		LOG.info("seteando las variables de seguridad en la session");
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		
//		boolean poseePerfiles = scai.isAdministrador(httpReq);/
//		
//		LOG.info("tiene perfiles: " + poseePerfiles);
//		if (!poseePerfiles) {
//			LOG.info("se esta intentando acceder a la aplicacion sin permisos previos...");
//			LOG.info("redireccionando hacia pagina de no-acceso...");
//			// redireccionar para pagina de mensaje
//			((HttpServletResponse) resp).sendRedirect("no-tiene-acceso.html");
//		} else {
//			LOG.info("Esta tratando de ejecutar la URI: " + httpReq.getRequestURI());
//			// tomamos la session
//			HttpSession session = httpReq.getSession(true);
//			//session.setAttribute(Constantes.PERFIL_USUARIO, new Boolean(scai.isUsuario(httpReq)));
//			// seguimso la cadena
//			fc.doFilter(req, resp);
//		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fc) throws ServletException {
		ApplicationContext wacc = WebApplicationContextUtils.getRequiredWebApplicationContext(fc.getServletContext());
//		scai = (SCAIWrapper) wacc.getBean("scaiWrapper");
	}

}
