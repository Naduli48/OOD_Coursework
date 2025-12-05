import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final java.util.logging.Logger logger =
            LoggerUtil.getLogger("TeamMateLogger");

    public static void main(String[] args) {

        TeamManager builder = new TeamManager();
        List<Team> formedTeams = null;

        int teamSize = 0;
        boolean csvLoaded = false;
        boolean teamsGenerated = false;

        while (true) {

            System.out.println("\nTEAMMATE SYSTEM MENU");
            System.out.println("========================\n");
            System.out.println("1. Upload participant CSV file");
            System.out.println("2. Define team size");
            System.out.println("3. Generate teams");
            System.out.println("4. View formed teams");
            System.out.println("5. Exit");
            System.out.print("\nEnter option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter CSV file name (example: participants_sample.csv): ");
                    String fileName = scanner.nextLine();

                    try {
                        builder.loadCSV(fileName.trim());
                        csvLoaded = true;

                        System.out.println("CSV successfully loaded");
                        logger.info("CSV loaded by organizer.");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        logger.warning("CSV load failed.");
                    }
                    break;

                case "2":
                    if (!csvLoaded) {
                        System.out.println("Please upload CSV file first.");
                        break;
                    }

                    System.out.print("Enter team size: ");

                    try {
                        teamSize = Integer.parseInt(scanner.nextLine());
                        if (teamSize <= 0) {
                            System.out.println("Team size must be greater than 0.");
                        } else {
                            System.out.println("Team size set to: " + teamSize);
                            logger.info("Organizer set team size to " + teamSize);
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid number. Try again.");
                    }
                    break;

                case "3":
                    if (!csvLoaded) {
                        System.out.println("You must upload CSV first.");
                        break;
                    }
                    if (teamSize <= 0) {
                        System.out.println("Please set a valid team size.");
                        break;
                    }

                    try {
                        formedTeams = builder.formTeams(teamSize);
                        builder.saveTeamsToCSV(formedTeams, "formed_teams.csv");
                        System.out.println("Teams saved to formed_teams.csv");

                        teamsGenerated = true;

                        System.out.println("\nTeams successfully generated");
                        logger.info("Teams generated successfully.");

                    } catch (Exception e) {
                        System.out.println("Team formation failed: " + e.getMessage());
                        logger.warning("Team formation failed.");
                    }
                    break;

                case "4":
                    if (!teamsGenerated) {
                        System.out.println("No teams formed yet. Generate teams first.");
                        break;
                    }

                    System.out.println("\nFORMED TEAMS");
                    System.out.println("================\n");

                    for (Team t : formedTeams) {
                        t.displayTeam();
                    }

                    logger.info("Organizer viewed formed teams.");
                    break;

                case "5":
                    System.out.println("Thank you for using TeamMate");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
