package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseLogger {
    private static final Logger infoLogger = LogManager.getLogger("InfoLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");

    public static void logDatabaseConnection(String query) {
        infoLogger.info("Database query executed at {} - Query: {}", getCurrentTimestamp(), query);
    }

    public static void logDatabaseException(Exception e) {
        errorLogger.error("Exception occurred at {} - {}", getCurrentTimestamp(), e.getMessage());
        e.printStackTrace();
    }

    private static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}
