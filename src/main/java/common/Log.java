package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger Log =  LogManager.getLogger(Log.class);

    //Info Level Logs
    public static void info (String message) {
        Log.info(message);
    }

    //Error Level Logs
    public static void error (String message) {
        Log.error(message);
    }

}
