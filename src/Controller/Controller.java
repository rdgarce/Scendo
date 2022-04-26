package Controller;

import Model.User;
import Model.dbManager;
import Model.dbManager.userFieldID;

import java.lang.reflect.Executable;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Controller {
    public static void main(String[] args) {

        dbManager db = new dbManager("jdbc:postgresql://localhost:5432/scendodb", "scendoadmin", "admin");

        try{
             
        if (db.openConnection() == -1)
        {
            System.err.println("Error opening the connection\n");
            System.err.println(db.getLastLog());
            return;
        }

        User raf = new User(db.getUUID(), "email@ramil.com", "Raf", "password");
        User hermo = new User(db.getUUID(), "email@ramil.com", "Hermo", "password2");

        ArrayList<User> a = new ArrayList<User>();
        a.add(raf);
        a.add(hermo);

        db.pushUsers(a);
        //System.out.print("Log after pushUsers: " + db.getLastLog());

        //System.out.print("log: " + db.getLogs().toString() + "\n");

        ArrayList<User> b = db.retreiveUsers(userFieldID.EMAIL, "email@ramil.com");

        System.out.print(b.get(0).getName() + '\n' + b.get(1).getName());

        db.pushUsers(a);

        }


        catch (Exception e){
            db.storeLogDb();
        }
        finally{
            db.storeLogDb();
        }
        
    }
}