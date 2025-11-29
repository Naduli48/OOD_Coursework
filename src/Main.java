import java.util.List;

public class Main {
    public static void main(String[] args) {

        TeamManager builder = new TeamManager();

        try {
            builder.loadCSV("src/participants_sample.csv");

            int teamSize = 5; // Organizer-defined

            List<Team> teams = builder.formTeams(teamSize);

            System.out.println("\n==== TEAM LIST ====");
            for (Team t : teams) {
                t.displayTeam();
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
