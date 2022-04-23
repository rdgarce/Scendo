package Controller;

import Model.dbManager;
import java.sql.ResultSet;

public class Controller {
    public static void main(String[] args) {
             
        dbManager db = new dbManager("jdbc:postgresql://localhost:5432/scendodb", "scendoadmin", "admin");
        db.store_log_db();
        if (db.openConnection() == -1)
        {
            System.err.println("Error opening the connection\n");
            System.err.println(db.getLastLog());
            return;
        }
            
        
    }

    private static void println(Object copy) {
    }
}