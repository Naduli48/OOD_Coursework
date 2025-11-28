import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CSVReader {
    private static final Logger logger = LoggerUtil.getLogger("TeamMateLogger");

    public static List<Participant> load(String filename) throws FileProcessingException{
        List<Participant> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();
            String line;

            while ((line = br.readLine()) != null){
                String[] p = line.split(",");

                if (p.length < 8){
                    logger.warning("Invalid row : "+line);
                    continue;
                }
                try{
                    list.add(new Participant(
                            p[0], p[1], p[2], p[3],
                            Integer.parseInt(p[4]),
                            p[5],
                            Integer.parseInt(p[6]),
                            p[7]
                    ));
                }catch (Exception e){
                    logger.warning("Skipping invalid row : "+line);
                }
            }
            logger.info("Loaded"+list.size()+"participants");

        }catch (IOException e){
            throw new FileProcessingException("Error loading csv file");
        }
        return list;
    }

}

