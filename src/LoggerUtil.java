import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    //initialize the static logger instance
    private static Logger logger = Logger.getLogger("TeamMateLogger");

    static{
        try{
            //Reset existing configuration and setup file handler
            LogManager.getLogManager().reset();

            FileHandler fh = new FileHandler("Teammate.log",true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

            logger.setLevel(Level.ALL);

        }catch (IOException e){
            System.err.println("Logger setup failed");
        }
    }

    public static Logger getLogger(String teamMateLogger){
        return logger;
    }
}

