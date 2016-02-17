/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
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
 * This {@link TableExpressionFactory} creates a new {@link TableExpression}.
 *
 * @see TableExpression
 *
 * @version 2.4
 * @since 2.4
 * @author James Sutherland
 */
@SuppressWarnings("nls")
public final class TableExpressionFactory extends ExpressionFactory {

	/**
	 * The unique identifier of this {@link TableExpressionFactory}.
	 */
	public static final String ID = "table_expression";

	/**
	 * Creates a new <code>TableExpressionFactory</code>.
	 */
	public TableExpressionFactory() {
		super(ID, Expression.TABLE);
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

		expression = new TableExpression(parent);
		expression.parse(wordParser, tolerant);
		return expression;
	}
}