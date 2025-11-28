public class Participant extends User{
    private String surveyStatus;
    private String skill;

    public Participant(String userID, String name, String email) {
        super(userID, name, email);
    }

    @Override
    public void displayInfo() {
        System.out.println();
    }
}
