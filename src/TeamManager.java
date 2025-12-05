import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class TeamManager {

    private List<Participant> participants = new ArrayList<>();
    private List<Participant> remainingParticipants = new ArrayList<>();
    private static final java.util.logging.Logger logger =
            LoggerUtil.getLogger("TeamMateLogger");

    //Load csv file
    public void loadCSV(String filename) throws FileProcessingException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            br.readLine(); // skip header

            participants.clear();
            remainingParticipants.clear();

            String line;
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

    //Create teams
    private List<List<Participant>> createChunks(int size) {

        List<List<Participant>> fullTeams = new ArrayList<>();
        remainingParticipants.clear();

        int total = participants.size();
        int fullTeamCount = total / size;
        int remainderStartIndex = fullTeamCount * size;

        //create full teams
        for (int i = 0; i < remainderStartIndex; i += size) {
            fullTeams.add(participants.subList(i, i + size));
        }

        //put remaining players into the pool
        if (remainderStartIndex < total) {
            remainingParticipants.addAll(
                    participants.subList(remainderStartIndex, total)
            );

            logger.info("Remaining participants: " +
                    remainingParticipants.size() + " moved to pool");
        }

        return fullTeams;
    }

    //build team
    private Team buildTeam(List<Participant> group, String name) {
        Team t = new Team(name);
        for (Participant p : group) {
            t.addParticipant(p);
        }
        logger.info(name + " formed");
        return t;
    }

    //concurrency
    public List<Team> formTeams(int teamSize)
            throws TeamFormationException {

        ExecutorService exec = Executors.newFixedThreadPool(4);
        List<Future<Team>> futures = new ArrayList<>();
        List<Team> teams = new ArrayList<>();

        List<List<Participant>> fullChunks = createChunks(teamSize);

        // Submit tasks to thread pool
        for (int i = 0; i < fullChunks.size(); i++) {

            final int index = i;
            List<Participant> chunk = fullChunks.get(i);

            futures.add(
                    exec.submit(() ->
                            buildTeam(chunk, "\nTeam : " + (index + 1)+"\n"))
            );
        }

        //collect results
        for (Future<Team> f : futures) {
            try {
                teams.add(f.get());
            } catch (Exception e) {
                throw new TeamFormationException("Team formation error");
            }
        }

        //create pool
        if (!remainingParticipants.isEmpty()) {
            Team pool = new Team("\nPool : \n");

            for (Participant p : remainingParticipants) {
                pool.addParticipant(p);
            }

            logger.info("Pool created with " +
                    remainingParticipants.size() + " participant(s)");
            teams.add(pool);
        }

        exec.shutdown();
        return teams;
    }

    public void saveTeamsToCSV(List<Team> teams, String filename) throws FileProcessingException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            // Write header row
            writer.println("TeamID,ParticipantID,Name,PreferredGame,SkillLevel,PreferredRole,PersonalityType");

            // Write each team's members
            for (Team team : teams) {
                for (Participant p : team.getParticipants()) {
                    writer.print(
                            team.getTeamID() + "," +
                                    p.getUserID() + "," +
                                    p.getName() + "," +
                                    p.getPreferredGame() + "," +
                                    p.getSkillLevel() + "," +
                                    p.getPreferredRole() + "," +
                                    p.getPersonalityType()
                    );
                }
            }

            logger.info("Teams successfully saved to CSV: " + filename);

        } catch (Exception e) {
            throw new FileProcessingException("Failed to save teams CSV: " + e.getMessage());
        }
    }


    public List<Participant> getParticipants() {
        return participants;
    }
}
