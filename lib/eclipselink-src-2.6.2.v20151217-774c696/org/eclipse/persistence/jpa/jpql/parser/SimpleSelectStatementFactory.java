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

import org.eclipse.persistence.jpa.jpql.WordParser;

/**
 * This {@link SimpleSelectStatementFactory} creates a new {@link SimpleSelectStatement} when the
 * portion of the query to parse starts with <b>SELECT</b>.
 *
 * @see SimpleSelectStatement
 *
 * @version 2.4
 * @since 2.3
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class SimpleSelectStatementFactory extends ExpressionFactory {

	/**
	 * The unique identifier of this {@link SimpleSelectStatementFactory}.
	 */
	public static final String ID = "simple-select";

	/**
	 * Creates a new <code>SimpleSelectStatementFactory</code>.
	 */
	public SimpleSelectStatementFactory() {
		super(ID, Expression.SELECT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractExpression buildExpression(AbstractExpression parent,
	                                             WordParser wordParser,
	                                             String word,
	                                             JPQLQueryBNF queryBNF,
	                                             AbstractExpression expression,
	                                             boolean tolerant) {

		expression = new SimpleSelectStatement(parent);
		expression.parse(wordParser, tolerant);
		return expression;
	}
}