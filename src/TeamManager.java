import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class TeamManager {
    private static final Logger logger = LoggerUtil.getLogger("TeamMateLogger");
    private int teamSize;
    private List<Participant> participants;

    public TeamManager(int teamSize, List<Participant> list) {
        this.teamSize = teamSize;
        this.participants = list;
    }

    public List<Team> manageTeams(){
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // This block uses multithreading to form teams in parallel.
        // Instead of forming each team one-by-one (slow), we submit each team formation
        // task to the ExecutorService, which runs them on multiple threads.
        // Each submitted task returns a Future<Team> which represents a "result that will arrive later".
        // This improves performance when dealing with large datasets.
        List<Future<Team>> futures = new ArrayList<>();
        List<Team> teams = new ArrayList<>();

        Collections.shuffle(participants);

        int totalTeams = participants.size() / teamSize;

        logger.info("Forming"+totalTeams+"teams");

        for (int i=0; i < totalTeams; i++){
            int start = i * teamSize;
            int end = start + teamSize;

            List<Participant> subset = participants.subList(start,end);

            futures.add(
                    executor.submit(() -> formTeam(subset, "Team" + (i + 1)))
            );
        }

        for (Future<Team> f : futures){
            try{
                teams.add(f.get());
            }catch (Exception e){
                logger.warning("Team formation failed");
            }
        }

        executor.shutdown();
        return teams;
    }

    private Team formTeam(List<Participant> group, String teamID){
        Team team = new Team(teamID);
        for (Participant p : group){
            team.addParticipant(p);
        }
        logger.info(teamID+"created");
        return team;
    }
}

