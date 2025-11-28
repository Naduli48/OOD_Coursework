public class Organizer extends User{

    public Organizer(String userID, String name, String email) {
        super(userID, name, email);
    }

    @Override
    public void displayInfo() {
        System.out.println("Organizer : "+
                "\nID : "+getUserID()+
                "\nName : "+getName()+
                "\nEmail : "+getEmail());
    }
}
