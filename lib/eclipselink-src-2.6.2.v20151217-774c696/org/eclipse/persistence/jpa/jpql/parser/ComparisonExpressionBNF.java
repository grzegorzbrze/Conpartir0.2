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
 * The query BNF for a comparison expression.
 * <p>
 * JPA 1.0:
 * <div><b>BNF:</b> <code>comparison_expression ::= string_expression comparison_operator {string_expression | all_or_any_expression} |
 *                                                         boolean_expression { = | {@literal <>} } {boolean_expression | all_or_any_expression} |
 *                                                         enum_expression { = | {@literal <>} } {enum_expression | all_or_any_expression} |
 *                                                         datetime_expression comparison_operator {datetime_expression | all_or_any_expression} |
 *                                                         entity_expression { = | {@literal <>} } {entity_expression | all_or_any_expression} |
 *                                                         arithmetic_expression comparison_operator {arithmetic_expression | all_or_any_expression}</code>
 * <p></div>
 * JPA 2.0, 2.1:
 * <div><b>BNF:</b> <code>comparison_expression ::= string_expression comparison_operator {string_expression | all_or_any_expression} |
 *                                                         boolean_expression { = | {@literal <>} } {boolean_expression | all_or_any_expression} |
 *                                                         enum_expression { = | {@literal <>} } {enum_expression | all_or_any_expression} |
 *                                                         datetime_expression comparison_operator {datetime_expression | all_or_any_expression} |
 *                                                         entity_expression { = | {@literal <>} } {entity_expression | all_or_any_expression} |
 *                                                         arithmetic_expression comparison_operator {arithmetic_expression | all_or_any_expression} |
 *                                                         entity_type_expression { = | {@literal <>} } entity_type_expression}</code>
 * <p></div>
 *
 * @version 2.5
 * @since 2.3
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public final class ComparisonExpressionBNF extends JPQLQueryBNF {

	/**
	 * The unique identifier of this BNF rule.
	 */
	public static final String ID = "comparison_expression";

	/**
	 * Creates a new <code>ComparisonExpressionBNF</code>.
	 */
	public ComparisonExpressionBNF() {
		super(ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		super.initialize();
		setCompound(true);
		setFallbackBNFId(ID);
		setFallbackExpressionFactoryId(LiteralExpressionFactory.ID);
		registerExpressionFactory(ComparisonExpressionFactory.ID);
		registerChild(AllOrAnyExpressionBNF.ID);
		registerChild(ArithmeticExpressionBNF.ID);
		registerChild(BooleanExpressionBNF.ID);
		registerChild(DatetimeExpressionBNF.ID);
		registerChild(EntityExpressionBNF.ID);
		registerChild(EnumExpressionBNF.ID);
		registerChild(StringExpressionBNF.ID);
	}
}