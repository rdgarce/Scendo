package Model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Scendo {

    private String creator_user_id;
    private String scendo_id;
    private String location;
    private java.util.Date time;
    private ArrayList<User> invited_users;

    public Scendo(String creatorUserId, String location, java.util.Date time){

        this.creator_user_id = creatorUserId;
        this.location = location;
        this.time = time;
    }

    public String getCreatorUserID(){

        return this.creator_user_id;

    }

    public String getScendoID(){

        return this.scendo_id;

    }

    public java.util.Date getTime(){

        return this.time;

    }

    public String getLocation(){

        return this.location;

    }

    public ArrayList<User> getInvitedUsers(){

        return this.invited_users;

    }
    
}