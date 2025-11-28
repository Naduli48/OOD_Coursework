import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamID;
    private List<Participant> participants;

    public Team(String teamID){
        this.teamID = teamID;
        this.participants = new ArrayList<Participant>();
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
}
