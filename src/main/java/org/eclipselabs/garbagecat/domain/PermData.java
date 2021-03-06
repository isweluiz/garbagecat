/**********************************************************************************************************************
 * garbagecat                                                                                                         *
 *                                                                                                                    *
 * Copyright (c) 2008-2020 Red Hat, Inc.                                                                              *
 *                                                                                                                    * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse *
 * Public License v1.0 which accompanies this distribution, and is available at                                       *
 * http://www.eclipse.org/legal/epl-v10.html.                                                                         *
 *                                                                                                                    *
 * Contributors:                                                                                                      *
 *    Red Hat, Inc. - initial API and implementation                                                                  *
 *********************************************************************************************************************/
package org.eclipselabs.garbagecat.domain;

/**
 * Permanent generation or metaspace data.
 * 
 * <p>
 * The decommissioning of the perm gen space began in JDK7 when interned strings and class static variables were moved
 * to the Java heap, and symbols were moved to the native heap.
 * </p>
 * 
 * <p>
 * In JDK8 the perm gen space was fully replaced by the metaspace, a native space holding only class metadata.
 * </p>
 * 
 * <p>
 * Unlike perm gen, it is typically not necessary to set a max/min metaspace size:
 * </p>
 * 
 * <ul>
 * <li>Metaspace size is unlimited by default. Since it only holds class metadata, size requirements are minimal.</li>
 * <li>It does not require a full gc to resize the metaspace, as it did with the perm generation.</li>
 * </ul>
 * 
 * <p>
 * Reference: JEP 122: Remove the Permanent Generation, https://openjdk.java.net/jeps/122.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public interface PermData {

    /**
     * @return Perm generation initial occupancy in kilobytes.
     */
    int getPermOccupancyInit();

    /**
     * @return Total perm generation space at the end of the event (i.e. it reflects any resizing) in kilobytes.
     */
    int getPermSpace();

    /**
     * @return Perm generation end occupancy in kilobytes.
     */
    int getPermOccupancyEnd();
}
