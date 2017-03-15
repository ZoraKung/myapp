/*
 * Modified from Hibernate Core
 * https://github.com/hibernate/hibernate-orm/blob/bd256e4783219f4a765219cf625bb658fcb5fde1/hibernate-core/src/main/java/org/hibernate/engine/jdbc/internal/DDLFormatterImpl.java
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.wjxinfo.core.base.utils.sqlf;

import java.util.Locale;
import java.util.StringTokenizer;


/**
 * Performs formatting of DDL SQL statements.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class DDLFormatterImpl {
    /**
     * Singleton access
     */
    public static final DDLFormatterImpl INSTANCE = new DDLFormatterImpl();

    private static boolean isBreak(String token) {
        return "drop".equals(token) ||
                "add".equals(token) ||
                "references".equals(token) ||
                "foreign".equals(token) ||
                "on".equals(token);
    }

    private static boolean isQuote(String tok) {
        return "\"".equals(tok) ||
                "`".equals(tok) ||
                "]".equals(tok) ||
                "[".equals(tok) ||
                "'".equals(tok);
    }

    public String format(String sql) {
        if (isEmpty(sql)) {
            return sql;
        }
        if (sql.toLowerCase(Locale.ROOT).startsWith("create table")) {
            return formatCreateTable(sql);
        } else if (sql.toLowerCase(Locale.ROOT).startsWith("alter table")) {
            return formatAlterTable(sql);
        } else if (sql.toLowerCase(Locale.ROOT).startsWith("comment on")) {
            return formatCommentOn(sql);
        } else {
            return "\n    " + sql;
        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    private String formatCommentOn(String sql) {
        final StringBuilder result = new StringBuilder(60).append("\n    ");
        final StringTokenizer tokens = new StringTokenizer(sql, " '[]\"", true);

        boolean quoted = false;
        while (tokens.hasMoreTokens()) {
            final String token = tokens.nextToken();
            result.append(token);
            if (isQuote(token)) {
                quoted = !quoted;
            } else if (!quoted) {
                if ("is".equals(token)) {
                    result.append("\n       ");
                }
            }
        }

        return result.toString();
    }

    private String formatAlterTable(String sql) {
        final StringBuilder result = new StringBuilder(60).append("\n    ");
        final StringTokenizer tokens = new StringTokenizer(sql, " (,)'[]\"", true);

        boolean quoted = false;
        while (tokens.hasMoreTokens()) {
            final String token = tokens.nextToken();
            if (isQuote(token)) {
                quoted = !quoted;
            } else if (!quoted) {
                if (isBreak(token)) {
                    result.append("\n        ");
                }
            }
            result.append(token);
        }

        return result.toString();
    }

    private String formatCreateTable(String sql) {
        final StringBuilder result = new StringBuilder(60).append("\n    ");
        final StringTokenizer tokens = new StringTokenizer(sql, "(,)'[]\"", true);

        int depth = 0;
        boolean quoted = false;
        while (tokens.hasMoreTokens()) {
            final String token = tokens.nextToken();
            if (isQuote(token)) {
                quoted = !quoted;
                result.append(token);
            } else if (quoted) {
                result.append(token);
            } else {
                if (")".equals(token)) {
                    depth--;
                    if (depth == 0) {
                        result.append("\n    ");
                    }
                }
                result.append(token);
                if (",".equals(token) && depth == 1) {
                    result.append("\n       ");
                }
                if ("(".equals(token)) {
                    depth++;
                    if (depth == 1) {
                        result.append("\n        ");
                    }
                }
            }
        }

        return result.toString();
    }

}