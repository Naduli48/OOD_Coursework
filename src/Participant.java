public class Participant extends User{
    private String preferredGame;
    private int skillLevel;
    private String preferredRole;
    private int personalityScore;
    private String personalityType;

    public Participant(String userID, String name, String email,String preferredGame, int skillLevel, String preferredRole, int personalityScore, String personalityType) {
        super(userID, name, email);
        this.preferredGame = preferredGame;
        this.skillLevel = skillLevel;
        this.preferredRole = preferredRole;
        this.personalityScore = personalityScore;
        this.personalityType = personalityType;
    }

    public String getPreferredGame() {
        return preferredGame;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    public int getPersonalityScore() {
        return personalityScore;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    @Override
    public void displayInfo() {
        System.out.println("Participants : "+"\nID : "+getUserID()+
                "\nName : "+getName()+
                "\nEmail : "+getEmail()+
                "\nGame : "+preferredGame+
                "\nRole : "+preferredRole+
                "\nSkill Level : "+skillLevel+
                "\nPersonality Type : "+personalityType);
    }

    public String toString(){
        return getUserID()+getName()+getEmail()+preferredGame+preferredRole+skillLevel+personalityType+personalityScore;
    }
}
