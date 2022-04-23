package Model;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.postgresql.copy.CopyDual;

public class Scendo {

    private String creator_user_id;
    private String scendo_id;
    private String location;
    private java.util.Date time;
    private ArrayList<User> invited_users = null;

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
        
        //Returns a copy of the arraylist.
        ArrayList <User> copy = new ArrayList<User>();
        copy.addAll(this.invited_users);
        return copy;

    }
    
}