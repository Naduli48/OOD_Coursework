import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamID;
    private List<member> members;

    public Team(String teamID){
        this.teamID = teamID;
        this.members = new ArrayList<member>();
    }
}
