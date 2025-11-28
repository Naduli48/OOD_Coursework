import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter {
    private static final Logger logger = LoggerUtil.getLogger("TeamMateLogger");

    public static void saveTeams(String filename, List<Team> teams) throws FileProcessingException{
        try(FileWriter fw = new FileWriter(filename)){
            fw.write("TeamID, ParticipantID,Name, Game, Role, Skill, Personality\n");

            for (Team t : teams){
                for (Participant p: t.getParticipants()){
                    fw.write(
                            t.getTeamID()+p.toString()
                    );
                }
            }
            logger.info("Team saved to "+filename);
        }catch (IOException e){
            throw new FileProcessingException("Error saving team");
        }
    }
}

