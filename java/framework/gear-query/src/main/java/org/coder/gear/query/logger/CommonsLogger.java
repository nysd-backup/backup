package org.coder.gear.query.logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

/**
 * logger.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class CommonsLogger extends AbstractSessionLog {

	  /**
     * <pre>
     *      Log 4 query .
     * </pre>
     */
    private static final Log QUERY_LOG = LogFactory.getLog("QUERY."
            + CommonsLogger.class);

    /**
     * <pre>
     *      Pattern .
     * </pre>
     */
    private static final Pattern PATTERN = Pattern
            .compile("(.+)\tbind => \\[(.+)\\]");

    /**
     * <pre>
     *      Name space of sql.
     * </pre>
     */
    private static final String NAME_SPACE_SQL = "sql";

    /**
     * @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(SessionLogEntry arg0) {
        String nameSpace = arg0.getNameSpace();
        if (NAME_SPACE_SQL.equals(nameSpace)) {
            if (QUERY_LOG.isDebugEnabled()) {
                String sql = StringUtils.replace(
                        StringUtils.replace(arg0.getMessage(), "\n", " "),
                        "\r", " ");
                String completeQuery = getCompleteQuery(sql);
                if (completeQuery != null) {
                    QUERY_LOG.trace(arg0.getMessage());
                    QUERY_LOG.debug(completeQuery);
                } else {
                    QUERY_LOG.debug(arg0.getMessage());
                }
            }
        } else {
        	QUERY_LOG.trace(arg0.getMessage());
        }

    }

    /**
     * Create complete sql.
     * 
     * @param sql the sql
     * @return binded sql
     */
    private String getCompleteQuery(String sql) {
        Matcher mt = PATTERN.matcher(sql);
        if (mt.find()) {
            String query = mt.group(1);
            String arguments = mt.group(2);
            String[] args = arguments.split(",");
            int i = 0;
            while (query.contains("?")) {
                if (i >= args.length) {
                    return null;
                }
                query = StringUtils.replaceOnce(query, "?", args[i]);
                i++;
            }
            return StringUtils.replace(query, "\t", " ");
        }
        return null;
    }
}