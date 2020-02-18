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
package org.eclipselabs.garbagecat.domain.jdk.unified;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipselabs.garbagecat.domain.jdk.ShenandoahCollector;
import org.eclipselabs.garbagecat.util.jdk.JdkMath;
import org.eclipselabs.garbagecat.util.jdk.JdkRegEx;
import org.eclipselabs.garbagecat.util.jdk.JdkUtil;

/**
 * <p>
 * USING_SHENANDOAH
 * </p>
 * 
 * <p>
 * Initial line logging indicating collector family.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <p>
 * 1) With <code>-Xlog:gc:file=&lt;file&gt;</code> (no details).
 * </p>
 * 
 * <pre>
 * [0.006s][info][gc] Using Shenandoah
 * </pre>
 * 
 * <p>
 * 2) With <code>-Xlog:gc*:file=&lt;file&gt;</code> (details).
 * </p>
 * 
 * <pre>
 * [0.005s][info][gc     ] Using Shenandoah
 * </pre>
 * 
 * <p>
 * 3) With <code>-Xlog:gc*:file=&lt;file&gt;:time,uptimemillis</code>.
 * </p>
 * 
 * <pre>
 * [2019-02-05T14:47:31.092-0200][4ms] Using Shenandoah
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class UsingShenandoahEvent extends ShenandoahCollector implements UnifiedLogging {

    /**
     * Regular expressions defining the logging.
     */
    private static final String REGEX = "^" + UnifiedLogging.DECORATOR + " Using Shenandoah[ ]*$";

    private static Pattern pattern = Pattern.compile(REGEX);

    /**
     * The log entry for the event. Can be used for debugging purposes.
     */
    private String logEntry;

    /**
     * The time when the GC event started in milliseconds after JVM startup.
     */
    private long timestamp;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public UsingShenandoahEvent(String logEntry) {
        this.logEntry = logEntry;

        if (logEntry.matches(REGEX)) {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                if (matcher.group(13).matches(JdkRegEx.TIMESTAMP_MILLIS)) {
                    //
                    timestamp = Long.parseLong(matcher.group(15));
                } else {
                    timestamp = JdkMath.convertSecsToMillis(matcher.group(14)).longValue();
                }
            }
        }
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.USING_SHENANDOAH.toString();
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return pattern.matcher(logLine).matches();
    }
}
