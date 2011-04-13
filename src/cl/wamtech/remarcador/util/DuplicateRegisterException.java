/*
 * Creado 04/03/2009
 *
 * $Id$
 *
 * Copyright Continuum Ltda. (2009).
 *
 */
package cl.wamtech.remarcador.util;

import org.springframework.dao.DataAccessException;

/**
 * @author jars <a href="mailto:jorge.rodriguez at continuum.cl">Jorge Al. Rodriguez Suarez</a>
 *
 */
public class DuplicateRegisterException extends DataAccessException {

	/**
	 * unique serial id
	 */
	private static final long serialVersionUID = 2261182435302480790L;

	/**
	 * @param msg
	 */
	public DuplicateRegisterException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param ex
	 */
	public DuplicateRegisterException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
