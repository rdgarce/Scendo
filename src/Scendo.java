import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Scendo {

    private String creator_user_id;
    private int scendo_id;
    private String location;
    private java.util.Date time;
    private ArrayList<User> invited_users;

    public Scendo(String creatorUserId, String location, java.util.Date time){

        this.creator_user_id = creatorUserId;
        this.location = location;
        this.time = time;
    }
    
}