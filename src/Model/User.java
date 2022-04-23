package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    
    private final String user_id;
    private String email;
    private String name;
    private String password;

    public User(String email, String name, String password,String user_id){

        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.password = password;
        
    }

    public User(ResultSet rs)throws SQLException{

        this.user_id = rs.getString("userId");
        this.email  = rs.getString("email");
        this.name = rs.getString("name");
        this.email= rs.getString("password");


    }



    public void setEmail(String email){

        this.email = email;
        
    }

    public void setName(String name){

        this.name = name;
        
    }

    public void setPassword(String password){

        this.password = password;

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

}