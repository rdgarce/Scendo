package Controller;

import Model.dbManager;
import java.sql.ResultSet;

public class Controller {
    public static void main(String[] args) {

                
        dbManager db = new dbManager("jdbc:postgresql://localhost:5432/scendodb", "scendoadmin", "admin");
        if (db.openConnection() == -1)
        {
            System.err.println("Error opening the connection\n");
            System.err.println(db.getLastLog());
            return;
        }
            
        
    }
}