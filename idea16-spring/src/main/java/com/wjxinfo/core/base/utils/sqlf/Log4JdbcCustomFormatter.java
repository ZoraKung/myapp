package com.wjxinfo.core.base.utils.sqlf;

import net.sf.log4jdbc.Properties;
import net.sf.log4jdbc.log.log4j2.Log4j2SpyLogDelegator;
import net.sf.log4jdbc.sql.Spy;
import net.sf.log4jdbc.sql.resultsetcollector.ResultSetCollector;
import net.sf.log4jdbc.sql.resultsetcollector.ResultSetCollectorPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.regex.Pattern;

/**
 * @author Chen Yan Yi
 */
public class Log4JdbcCustomFormatter extends Log4j2SpyLogDelegator {

    private static final Marker NON_STATEMENT_MARKER = MarkerManager.getMarker("LOG4JDBC_NON_STATEMENT");
    private static final Marker OTHER_MARKER = MarkerManager.getMarker("LOG4JDBC_OTHER", NON_STATEMENT_MARKER);

    // logs for sql and jdbc
    private static final Marker RESULTSETTABLE_MARKER = MarkerManager.getMarker("LOG4JDBC_RESULTSETTABLE",
            OTHER_MARKER);
    private static String nl = System.getProperty("line.separator");
    /**
     * Logger that shows all JDBC calls on INFO level (exception ResultSet calls)
     */
    private final Logger jdbcLogger = LogManager.getLogger("jdbc.audit");
    /**
     * Logger that shows JDBC calls for ResultSet operations
     */
    private final Logger resultSetLogger = LogManager.getLogger("jdbc.resultset");
    /**
     * Logger that shows only the SQL that is occuring
     */
    private final Logger sqlOnlyLogger = LogManager.getLogger("jdbc.sqlonly");

    // admin/setup logging for log4jdbc.
    /**
     * Logger that shows the SQL timing, post execution
     */
    private final Logger sqlTimingLogger = LogManager.getLogger("jdbc.sqltiming");
    /**
     * Logger that shows connection open and close events as well as current number
     * of open connections.
     */
    private final Logger connectionLogger = LogManager.getLogger("jdbc.connection");
    /**
     * Logger just for debugging things within log4jdbc itself (admin, setup, etc.)
     */
    private final Logger debugLogger = LogManager.getLogger("log4jdbc.debug");
    /**
     * Logger that shows the forward scrolled result sets in a table
     */
    private final Logger resultSetTableLogger = LogManager.getLogger("jdbc.resultsettable");
    private String format;
    public Log4JdbcCustomFormatter() {
    }

    /**
     * Get debugging info - the module and line number that called the logger
     * version that prints the stack trace information from the point just before
     * we got it (net.sf.log4jdbc)
     * <p>
     * if the optional log4jdbc.debug.stack.prefix system property is defined then
     * the last call point from an application is shown in the debug
     * trace output, instead of the last direct caller into log4jdbc
     *
     * @return debugging info for whoever called into JDBC from within the application.
     */
    private static String getDebugInfo() {
        Throwable t = new Throwable();
        t.fillInStackTrace();

        StackTraceElement[] stackTrace = t.getStackTrace();

        if (stackTrace != null) {
            String className;

            StringBuilder dump = new StringBuilder();

            /**
             * The DumpFullDebugStackTrace option is useful in some situations when
             * we want to see the full stack trace in the debug info-  watch out
             * though as this will make the logs HUGE!
             */
            if (Properties.isDumpFullDebugStackTrace()) {
                boolean first = true;
                for (StackTraceElement aStackTrace : stackTrace) {
                    className = aStackTrace.getClassName();
                    if (!className.startsWith("net.sf.log4jdbc")) {
                        if (first) {
                            first = false;
                        } else {
                            dump.append("  ");
                        }
                        dump.append("at ");
                        dump.append(aStackTrace);
                        dump.append(nl);
                    }
                }
            } else {
                dump.append(" ");
                int firstLog4jdbcCall = 0;
                int lastApplicationCall = 0;

                for (int i = 0; i < stackTrace.length; i++) {
                    className = stackTrace[i].getClassName();
                    if (className.startsWith("net.sf.log4jdbc")) {
                        firstLog4jdbcCall = i;
                    } else if (Properties.isTraceFromApplication() && Pattern.matches(Properties.getDebugStackPrefix
                            (), className)) {
                        lastApplicationCall = i;
                        break;
                    }
                }
                int j = lastApplicationCall;

                if (j == 0)  // if app not found, then use whoever was the last guy that called a log4jdbc class.
                {
                    j = 1 + firstLog4jdbcCall;
                }

                dump.append(stackTrace[j].getClassName()).append(".").append(stackTrace[j].getMethodName()).append("(").
                        append(stackTrace[j].getFileName()).append(":").append(stackTrace[j].getLineNumber()).append
                        (")");
            }

            return dump.toString();
        }
        return null;
    }

    @Override
    public void sqlTimingOccurred(Spy spy, long execTime, String methodCall, String sql) {
        if (sqlTimingLogger.isErrorEnabled() && (!Properties.isDumpSqlFilteringOn() || shouldSqlBeLogged(sql))) {
            if (Properties.isSqlTimingErrorThresholdEnabled() && execTime >= Properties
                    .getSqlTimingErrorThresholdMsec()) {
                sqlTimingLogger.error(buildSqlTimingDump(spy, execTime, methodCall, sql, sqlTimingLogger
                        .isDebugEnabled()));
            } else if (sqlTimingLogger.isWarnEnabled()) {
                if (Properties.isSqlTimingWarnThresholdEnabled() && execTime >= Properties
                        .getSqlTimingWarnThresholdMsec()) {
                    sqlTimingLogger.warn(buildSqlTimingDump(spy, execTime, methodCall, sql, sqlTimingLogger
                            .isDebugEnabled()));
                } else if (sqlTimingLogger.isDebugEnabled()) {
                    sqlTimingLogger.debug(buildSqlTimingDump(spy, execTime, methodCall, sql, true));
                } else if (sqlTimingLogger.isInfoEnabled()) {
                    sqlTimingLogger.info(buildSqlTimingDump(spy, execTime, methodCall, sql, false));
                }
            }
        }
    }

    private String buildSqlTimingDump(Spy spy, long execTime, String methodCall, String sql, boolean debugInfo) {
        StringBuilder out = new StringBuilder();

        if (debugInfo) {
            out.append(getDebugInfo());
            out.append(nl);
            out.append(spy.getConnectionNumber());
            out.append(". ");
        }

        // NOTE: if both sql dump and sql timing dump are on, the processSql
        // algorithm will run TWICE once at the beginning and once at the end
        // this is not very efficient but usually
        // only one or the other dump should be on and not both.

        sql = processSql(sql);

        out.append(sql);
        out.append(" {executed in ");
        out.append(execTime);
        out.append(" msec}");

        return out.toString();
    }

    @Override
    public void sqlOccurred(Spy spy, String methodCall, String sql) {
        {
            if (!Properties.isDumpSqlFilteringOn() || shouldSqlBeLogged(sql)) {
                if (sqlOnlyLogger.isDebugEnabled()) {
                    sqlOnlyLogger.debug(getDebugInfo() + nl + spy.getConnectionNumber() +
                            ". " + processSql(sql));
                } else if (sqlOnlyLogger.isInfoEnabled()) {
                    sqlOnlyLogger.info(processSql(sql));
                }
            }
        }
    }

    private boolean shouldSqlBeLogged(String sql) {
        if (sql == null) {
            return false;
        }
        sql = sql.trim();

        if (sql.length() < 6) {
            return false;
        }
        sql = sql.substring(0, 6).toLowerCase();
        return (Properties.isDumpSqlSelect() && "select".equals(sql)) ||
                (Properties.isDumpSqlInsert() && "insert".equals(sql)) ||
                (Properties.isDumpSqlUpdate() && "update".equals(sql)) ||
                (Properties.isDumpSqlDelete() && "delete".equals(sql)) ||
                (Properties.isDumpSqlCreate() && "create".equals(sql));
    }

    /**
     * Break an SQL statement up into multiple lines in an attempt to make it
     * more readable
     *
     * @param sql SQL to break up.
     * @return SQL broken up into multiple lines
     */
    private String processSql(String sql) {
        if (sql == null) {
            return null;
        }

        if (Properties.isSqlTrim()) {
            sql = sql.trim();
        }

        if (format.equalsIgnoreCase("basic")) {
            sql = com.wjxinfo.core.base.utils.sqlf.BasicFormatterImpl.INSTANCE.format(sql);
        } else if (format.equalsIgnoreCase("ddl")) {
            sql = com.wjxinfo.core.base.utils.sqlf.DDLFormatterImpl.INSTANCE.format(sql);
        }

        return sql;
    }

    @Override
    public void methodReturned(Spy spy, String methodCall, String returnMsg) {
        // Disable method call with log display
    }

    @Override
    public void resultSetCollected(ResultSetCollector resultSetCollector) {
        String resultToPrint = new ResultSetCollectorPrinter().getResultSetToPrint(resultSetCollector);

        if (resultSetTableLogger.isDebugEnabled()) {
            resultSetTableLogger.debug(RESULTSETTABLE_MARKER, resultToPrint);
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
