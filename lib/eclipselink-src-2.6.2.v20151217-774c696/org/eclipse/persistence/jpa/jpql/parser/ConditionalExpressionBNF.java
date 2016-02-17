/*******************************************************************************
 * Copyright (c) 2006, 2014 Oracle and/or its affiliates. All rights reserved.
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
 * The query BNF for a conditional expression.
 *
 * <div><b>BNF:</b> <code>conditional_expression ::= conditional_term | conditional_expression OR conditional_term</code><p></div>
 *
 * @version 2.5
 * @since 2.3
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class ConditionalExpressionBNF extends JPQLQueryBNF {

	/**
	 * The unique identifier of this BNF rule.
	 */
	public static final String ID = "conditional_expression";

	/**
	 * Creates a new <code>ConditionalExpressionBNF</code>.
	 */
	public ConditionalExpressionBNF() {
		super(ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		super.initialize();
		setHandleAggregate(true);
		setFallbackBNFId(ID);
		setFallbackExpressionFactoryId(LiteralExpressionFactory.ID);
		registerExpressionFactory(OrExpressionFactory.ID);
		registerChild(ConditionalTermBNF.ID);
	}
}