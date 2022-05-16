package Controller;

import Model.Scendo;
import Model.User;
import Model.dbManager;
import Model.dbManager.scendoFieldID;
import Model.dbManager.userFieldID;
import java.util.ArrayList;
import java.sql.Timestamp;

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
        User hermo = new User(db.getUUID(), "email2@ramil.com", "Hermo", "password2");

        ArrayList<User> a = new ArrayList<User>();
        a.add(raf);
        a.add(hermo);

        db.pushUsers(a);

        for (User us : db.retreiveUsers(userFieldID.USERID, raf.getUserID())) {
            System.out.println(us.getName());
        }

        

        Scendo sc1 = new Scendo(db.getUUID(), raf.getUserID(), "Via Roma", Timestamp.valueOf("2022-04-20 23:15:00"));
        sc1.addInvitedUser(hermo.getUserID());
        
        db.pushScendo(sc1);

        User cozis = new User(db.getUUID(), "email33@ramil.com", "Cozis", "password3");
        db.pushUser(cozis);

        sc1.addInvitedUser(cozis.getUserID());
        sc1.removeInvitedUser(hermo.getUserID());
        sc1.setLocation("Via Napoli");

        db.pushScendo(sc1);

        ArrayList<Scendo> scendos = db.retreiveScendos(scendoFieldID.LOCATION, "Via Napoli");

        for (Scendo scendo : scendos) {
            System.out.println(scendo.getScendoID());
        }
        

        }


        catch (Exception e){
            System.out.println(db.getLastLog());
            db.storeLogDb();
        }
        finally{
            db.storeLogDb();
        }
        
    }
}