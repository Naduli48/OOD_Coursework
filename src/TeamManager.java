import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class TeamManager {

    private List<Participant> participants = new ArrayList<>();
    private List<Participant> remainingParticipants = new ArrayList<>(); // handle remaining players
    private static final java.util.logging.Logger logger = LoggerUtil.getLogger("TeamMateLogger");

    //load csv file
    public void loadCSV(String filename) throws FileProcessingException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String header = br.readLine(); // Skip header
            String line;
            participants.clear(); //clear previous list
            remainingParticipants.clear();  //Clear previous pool

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                participants.add(new Participant(
                        d[0], d[1], d[2],
                        d[3], Integer.parseInt(d[4]),
                        d[5], Integer.parseInt(d[6]),
                        d[7]
                ));
            }

            logger.info("CSV loaded: " + participants.size() + " participants");

        } catch (Exception e) {
            throw new FileProcessingException("CSV load failed: " + e.getMessage());
        }
    }

    //Split participants
    private List<List<Participant>> createChunks(int size) {
        List<List<Participant>> chunks = new ArrayList<>();
        remainingParticipants.clear();

        int numParticipants = participants.size();
        int numFullTeams = numParticipants/size;
        int remainingStart = numFullTeams*size;   //Index where the remaining players start

        //Create chun
        for (int i = 0; i < participants.size(); i += size) {
            int end = Math.min(i + size, participants.size());
            chunks.add(participants.subList(i, end));
        }

        return chunks;
    }

    //Form Team
    private Team buildTeam(List<Participant> group, String name) {
        Team t = new Team(name);
        for (Participant p : group) {
            t.addParticipant(p);
        }
        logger.info(name + " formed.");
        return t;
    }

    //Concurrency Team Formation
    public List<Team> formTeams(int teamSize)
            throws TeamFormationException {

        ExecutorService exec = Executors.newFixedThreadPool(4);
        List<Future<Team>> futures = new ArrayList<>();
        List<Team> teams = new ArrayList<>();

        List<List<Participant>> chunks = createChunks(teamSize);

        for (int i = 0; i < chunks.size(); i++) {

            final int index = i;
            List<Participant> chunk = chunks.get(i);

            futures.add(
                    exec.submit(() -> buildTeam(chunk, "\nTeam : " + (index + 1)+"\n"))
            );
        }

        for (Future<Team> f : futures) {
            try {
                teams.add(f.get());
            } catch (Exception e) {
                throw new TeamFormationException("Team formation error");
            }
        }

        exec.shutdown();
        return teams;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}
