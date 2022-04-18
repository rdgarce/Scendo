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
            
        ResultSet rs = db.executeQueryFromString("SELECT * FROM Users;");

        try {
            
            while (rs.next()) {
            
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (Exception e) {
            System.err.println(db.getLastLog());
            //TODO: handle exception
        }
        

        
    }
}