import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static Logger logger;

    //Return a shared, properly configured logger instance
    public static Logger getLogger(String name){
        if (logger == null){
            logger = Logger.getLogger(name);
            setupLogger();
        }
        return logger;
    }

    //configure file logging(Teammate.log)
    private static void setupLogger(){
        try{
            //Reset existing configuration and setup file handler
            LogManager.getLogManager().reset();

            FileHandler fh = new FileHandler("Teammate.log",true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

            logger.setLevel(Level.ALL);

        }catch (IOException e){
            System.err.println("Logger setup failed"+e.getMessage());
        }
    }
}

