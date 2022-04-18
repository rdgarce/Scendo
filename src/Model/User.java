package Model;

public class User {
    
    private String user_id;
    private String email;
    private String name;
    private String password;

    public User(){
        
    }

    

    public void set_email(String email){

        this.email = email;
        
    }

    public void set_user_id(String user_id){

        this.user_id = user_id;

    }

    public void set_password(String password){

        this.password = password;

    }

    public void set_name(String name){

        this.name = name;

    }

    public String get_email(){

        return this.email;

    }

    public String get_user_id(){

        return this.user_id;

    }

    public String get_name(){

        return this.name;

    }

    public String get_password(){

        return this.password;

    }


}