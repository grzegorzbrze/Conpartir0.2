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
 * This {@link TreatExpressionFactory} creates a new {@link TreatExpression} when the portion of the
 * query to parse starts with <b>TREAT</b>.
 *
 * @see TreatExpression
 *
 * @version 2.4
 * @since 2.3
 * @author Pascal Filion
 */
public final class TreatExpressionFactory extends ExpressionFactory {

	/**
	 * The unique identifier of this {@link TreatExpressionFactory}.
	 */
	public static final String ID = Expression.TREAT;

	/**
	 * Creates a new <code>TreatExpressionFactory</code>.
	 */
	public TreatExpressionFactory() {
		super(ID, Expression.TREAT);
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

		expression = new TreatExpression(parent);
		expression.parse(wordParser, tolerant);

		if (wordParser.character() == AbstractExpression.DOT) {
			ExpressionFactory factory = getExpressionRegistry().getExpressionFactory(StateFieldPathExpressionFactory.ID);
			expression = factory.buildExpression(parent, wordParser, wordParser.word(), queryBNF, expression, tolerant);
		}

		return expression;
	}
}