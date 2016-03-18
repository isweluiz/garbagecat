/**********************************************************************************************************************
 * garbagecat                                                                                                         *
 *                                                                                                                    *
 * Copyright (c) 2008-2016 Red Hat, Inc.                                                                              *
 *                                                                                                                    * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse *
 * Public License v1.0 which accompanies this distribution, and is available at                                       *
 * http://www.eclipse.org/legal/epl-v10.html.                                                                         *
 *                                                                                                                    *
 * Contributors:                                                                                                      *
 *    Red Hat, Inc. - initial API and implementation                                                                  *
 *********************************************************************************************************************/
package org.eclipselabs.garbagecat.domain.jdk;

import org.eclipselabs.garbagecat.util.jdk.JdkUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestHeaderCommandLineFlagsEvent extends TestCase {

    public void testLine() {
        String logLine = "CommandLine flags: -XX:+CMSClassUnloadingEnabled -XX:CMSInitiatingOccupancyFraction=75 "
                + "-XX:+CMSScavengeBeforeRemark -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses "
                + "-XX:GCLogFileSize=8388608 -XX:InitialHeapSize=13958643712 -XX:MaxHeapSize=13958643712 "
                + "-XX:MaxPermSize=402653184 -XX:MaxTenuringThreshold=6 -XX:NewRatio=2 -XX:NumberOfGCLogFiles=8 "
                + "-XX:OldPLABSize=16 -XX:PermSize=402653184 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails "
                + "-XX:+PrintGCTimeStamps -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseGCLogFileRotation "
                + "-XX:+UseParNewGC";
        Assert.assertTrue(
                "Log line not recognized as " + JdkUtil.LogEventType.HEADER_COMMAND_LINE_FLAGS.toString() + ".",
                HeaderCommandLineFlagsEvent.match(logLine));
        HeaderCommandLineFlagsEvent event = new HeaderCommandLineFlagsEvent(logLine);
        String jvmOptions = "-XX:+CMSClassUnloadingEnabled -XX:CMSInitiatingOccupancyFraction=75 "
                + "-XX:+CMSScavengeBeforeRemark -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses "
                + "-XX:GCLogFileSize=8388608 -XX:InitialHeapSize=13958643712 -XX:MaxHeapSize=13958643712 "
                + "-XX:MaxPermSize=402653184 -XX:MaxTenuringThreshold=6 -XX:NewRatio=2 -XX:NumberOfGCLogFiles=8 "
                + "-XX:OldPLABSize=16 -XX:PermSize=402653184 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails "
                + "-XX:+PrintGCTimeStamps -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseGCLogFileRotation "
                + "-XX:+UseParNewGC";
        Assert.assertEquals("Flags not parsed correctly.", jvmOptions, event.getJvmOptions());
    }
}
