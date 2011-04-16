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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cl.wamtech.remarcador.model.Usuario;



/**
 * @author jars - <a href="mailto:jorge.rodriguez.suarez at gmail.com">Jorge Al. Rodriguez Suarez</a>
 *
 */
public class AccessFilter implements Filter {

	protected static final Log LOG = LogFactory.getLog(AccessFilter.class);

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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpSession session = httpReq.getSession(true);
		Usuario usuario = (Usuario) session.getServletContext().getAttribute("usuario");
		
		if(usuario == null) {
			((HttpServletResponse) resp).sendRedirect("no-tiene-acceso.html");
		} else {
			fc.doFilter(req, resp);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fc) throws ServletException {
	}

}
