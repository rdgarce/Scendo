package Model;

public class User {
    
    private String user_id;
    private String email;
    private String name;
    private String password;

    public User(String user_id,String email,String name,String password){

        this.user_id = user_id;
        this.email = name;
        this.name = name;
        this.password = password;
        
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