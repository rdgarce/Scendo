package Model;

import java.util.ArrayList;

public class User {
    
    //final because it has to be set just once ;)
    private final String user_id;
    private String email;
    private String name;
    private String password;
    private ArrayList<String> user_scendos_id;

    public User(String userId, String email, String name, String password){
        
        this.user_id = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.user_scendos_id = new ArrayList<String>();
        
    }


    public void setName(String name){

        this.name = name;
        
    }

    public void setPassword(String password){

        this.password = password;

    }

    public void setEmail(String email){

        this.email = email;
        
    }

    public void addScendo(String scendoId){
        
        if(this.user_scendos_id.contains(scendoId) == false)
            this.user_scendos_id.add(scendoId);

    }

    public void removeScendo(String scendoId){
        
        this.user_scendos_id.remove(scendoId);

    }

    public boolean checkScendo(String scendoId){
        
        return this.user_scendos_id.contains(scendoId);

    }

    public String getUserID(){

        return this.user_id;

    }

    public String getEmail(){

        return this.email;

    }

    public String getName(){

        return this.name;

    }

    public String getPassword(){

        return this.password;

    }

    public ArrayList<String> getScendos(){

        ArrayList<String> copy = new ArrayList<String>();
        copy.addAll(this.user_scendos_id);

        return copy;

    }

}