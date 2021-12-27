package bootstrap;

import org.apache.log4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    static String[] COLORS = {"red", "blue", "green", "yellow"};
    static Logger logger = LoggerFactory.getLogger(Driver.class);

    /**
     * This is the start of the main program!
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        configureLogging("var/log/gitIntro/", false, System.currentTimeMillis());
        logger.info("Hello World");
    }

    /**
     * This method needs to be called for configuring file logging to monitor the program operation. When running the
     * program in relay mode on the edge device, this is the method you should use. This will let the application
     * collect log information in local .log files in the /executionLogs folder. In relay mode, the application will
     * send these log files to aws s3 periodically.
     *
     * @param logDirectory - directory where application log files are to be saved.
     * @param debug        - boolean - if true, the logger will collect debug level information. This usually generates
     *                     voluminous logs only required for debugging purposes. Know what you are doing and use this
     *                     sparingly!
     * @param startTimeMs  - timestamp milliseconds when application was started.
     * @return returns the name of the local log file.
     */
    public static String configureLogging(String logDirectory, boolean debug, Long startTimeMs) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFileName = "";
        if (!debug) {
            dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            logFileName = logDirectory + "introToGit.log";

        } else {
            dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            logFileName = logDirectory + "introToGitDebug.log";
        }

        System.out.println("Log files written out at " + logFileName);
        dailyRollingFileAppender.setFile(logFileName);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%-6d [%25.35t] %-5p %40.80c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

    /**
     * This method needs to be called for configuring console logging to monitor the program operation.
     *
     * @param debug - boolean - if true, the logger will collect debug level information. This usually generates
     *              voluminous logs only required for debugging purposes. Know what you are doing and use this
     *              sparingly!
     */
    public static void configureConsoleLogging(boolean debug) {
        ConsoleAppender ca = new ConsoleAppender();
        if (!debug) {
            ca.setThreshold(Level.toLevel(Priority.INFO_INT));
        } else {
            ca.setThreshold(Level.toLevel(Priority.DEBUG_INT));
        }
        ca.setLayout(new EnhancedPatternLayout("%-6d [%25.35t] %-5p %40.80c - %m%n"));
        ca.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(ca);
    }
}