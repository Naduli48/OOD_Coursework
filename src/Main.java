import java.util.List;
import java.util.Scanner;

public class Main {
    private static final LoggerUtil LOGGER_UTIL = LoggerUtil.getLogger("TeamMateLogger");

    public static void main(String[] args){
        try{
            LOGGER_UTIL.info("TeamMate System Started");

            Organizer organizer = new Organizer("ORD001","Event organizer", "organizer1@university.edu");

            List<Participant> participants = CSVReader.load("participants_sample.csv");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter team size");
            int size = scanner.nextInt();

            TeamManager tm = new TeamManager(size,participants);
            List<Team> teams = tm.manageTeams();

            CSVWriter.saveTeams("Formed_teams.csv",teams);

            System.out.println("Teams formed successfully");
            LOGGER_UTIL.info("Process complete");

        } catch (Exception e){
            LOGGER_UTIL.warning("Error : ",e.getMessage());
            System.out.println("An error occurred");
        }
    }
}

