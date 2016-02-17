/**
 * ****************************************************************************
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * <p/>
 * Contributors:
 *      Marcel Valovy - initial API and implementation
 * ****************************************************************************
 */
package org.eclipse.persistence.internal.cache;

/**
 * Computable task.
 */
public interface ComputableTask<A, V> {

    V compute(A arg) throws InterruptedException;
}
