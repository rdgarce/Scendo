package Model;

import java.io.PushbackInputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.postgresql.util.PGPropertyMaxResultBufferParser;


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

    public String get_creator_user_id(){

        return this.creator_user_id;

    }

    public String get_scendo_id(){

        return this.scendo_id;

    }

    public java.util.Date get_time(){

        return this.time;

    }

    public String get_location(){

        return this.location;

    }

    public ArrayList<User> get_invited_users(){

        return this.invited_users;

    }

    
}