import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamID;
    private List<Participant> participants = new ArrayList<>();

    public Team(String teamID){
        this.teamID = teamID;
    }

    public String getTeamID(){
        return teamID;
    }

    public List<Participant> getParticipants(){
        return participants;
    }

    public void addParticipant(Participant p){
        participants.add(p);
    }

    // Calculates the average skill level of all members in the team.
    // This method helps ensure balanced teams by showing how strong the team is overall.
    // It adds the skill levels of each participant and divides by the number of members.
    // Without this, the system cannot check or maintain skill balance between teams.
    public int getAverageSkill(){
        int total = 0;

        //Add up the skill levels of all participants in this team
        for (Participant p : participants){
            total += p.getSkillLevel();
        }

        //calculate average skill level
        int avg = total/participants.size();
        return avg;



    }
}


