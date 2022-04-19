package Model;

public class User {
    
    private String user_id;
    private String email;
    private String name;
    private String password;

    public User(String email, String name, String password){
        
        this.email = email;
        this.name = name;
        this.password = password;
        
    }
    
    public User(){}

    public void setUserID(String userId){

        this.user_id = userId;
        
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