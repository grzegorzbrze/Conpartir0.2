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
 * The query BNF for the parameter of the <code><b>LENGTH</b></code> expression.
 * <p>
 * JPA 1.0:
 * <div><b>BNF</b> ::= SUBSTRING(string_primary, simple_arithmetic_expression, simple_arithmetic_expression)</div>
 * <p>
 * JPA 2.0:
 * <div><b>BNF</b> ::= SUBSTRING(string_primary, simple_arithmetic_expression [, simple_arithmetic_expression])</div>
 * <p>
 * JPA 2.1:
 * <div><b>BNF</b> ::= SUBSTRING(string_expression, arithmetic_expression [, arithmetic_expression])</div>
 * <p>
 *
 * @version 2.5
 * @since 2.4
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class InternalSubstringStringExpressionBNF extends JPQLQueryBNF {

	/**
	 * The unique identifier of this BNF rule.
	 */
	public static final String ID = "substring_item";

	/**
	 * Creates a new <code>InternalSubstringStringExpressionBNF</code>.
	 */
	public InternalSubstringStringExpressionBNF() {
		super(ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		super.initialize();
		setFallbackBNFId(ID);
		setFallbackExpressionFactoryId(LiteralExpressionFactory.ID);
		registerChild(StringPrimaryBNF.ID);
	}
}