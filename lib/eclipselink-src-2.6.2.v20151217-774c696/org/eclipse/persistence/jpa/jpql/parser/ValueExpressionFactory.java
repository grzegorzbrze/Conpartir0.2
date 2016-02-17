/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.persistence.jpa.jpql.parser;

/**
 * This {@link ValueExpressionFactory} creates a new {@link ValueExpression} when the portion of the
 * query to parse starts with <b>VALUE</b>.
 *
 * @see ValueExpression
 *
 * @version 2.4
 * @since 2.3
 * @author Pascal Filion
 */
public final class ValueExpressionFactory extends GeneralIdentificationExpressionFactory {

	/**
	 * The unique identifier of this {@link ValueExpressionFactory}.
	 */
	public static final String ID = Expression.VALUE;

	/**
	 * Creates a new <code>ValueExpressionFactory</code>.
	 */
	public ValueExpressionFactory() {
		super(ID, Expression.VALUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractExpression buildExpression(AbstractExpression parent) {
		return new ValueExpression(parent);
	}
}