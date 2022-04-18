package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

import java.sql.ResultSet;
import java.sql.Statement;


public class dbManager {

   private String db_host_name;
   private String usr;
   private String pswd;
   private Connection c;
   private ArrayList<String> error_logs;


   public enum userFieldID{
      USERID,
      EMAIL,
      NAME
   }

   public enum scendoFieldID{
      SCENDOID,
      LOCATION,
      TIME
   }

   public dbManager(String dbHostName, String username, String password) {

      this.db_host_name = dbHostName;
      this.usr = username;
      this.pswd = password;
      this.error_logs = new ArrayList<String>();
      
   }

   /* 
   *  Establish the connection to the given database.
   *  Returns 0 if the connection is established or 
   *  -1 if it fails to be built.
   *  getLastLog() method can be called to retreive 
   *  the error message of the last executed operation
   */
   public int openConnection(){

      try {

         this.c = DriverManager.getConnection(this.db_host_name, this.usr, this.pswd);

      } catch (Exception e) {

         error_logs.add(e.getMessage());
         return -1;

      }

      return 0;
   }

   /* 
   *  Try to close the connection to the given database.
   *  Returns 0 if the connection is closed without errors
   *  or -1 if it fails.
   *  getLastLog() method can be called to retreive 
   *  the error message of the last executed operation
   */
   public int closeConnection(){

      try {

         this.c.close();

      } catch (Exception e) {

         error_logs.add(e.getMessage());
         return -1;
      }

      return 0;
   }


   /*
   *  Execute a query from a String directly into the database.
   *  Returns ResultSet or null if the query fails.
   *  getLastLog() method can be called to retreive 
   *  the error message of the last executed operation
   */
   public ResultSet executeQueryFromString(String query){

      ResultSet rs;
      Statement stmt;

      try {
         
         stmt = c.createStatement();
         rs = stmt.executeQuery(query);

      } catch (Exception e) {
         
         error_logs.add(e.getMessage());
         return null;

      }
      
      return rs;
   }

   /*
   *  Retreive User(s) from the database filtering on the field
   *  specified in the [fieldID] where the value is equals to [val].
   *  Returns either an ArrayList of User(s) or a null value.
   *  If the result of the query is only one User, a one-legth array is returned.
   */
   public ArrayList<User> retreiveUsers(userFieldID fieldID, String val){

      String field_;

      switch (fieldID) {
         case NAME:
            RealDBField a = RealDBField.name;
            //facciamo la query su "dbisc"
            /*query su:*/ //a;
            
            break;
      
         default:
            break;
      }
      //usa dbManager per fare una query di ritiro di utente;
   }

   private boolean search_user_from_email(User us){

      PreparedStatement pstmt = null;

      

      String query = "SELECT email from Users where email = ?";
      pstmt = c.prepareStatement(query);
      pstmt.setString(1, us.get_email());
   

      


   }

   /*
   *  Push to the database an ArrayList of User(s).
   *  The function determins if the User need to be
   *  INSERTED or UPDATED into the db so the usage 
   *  is the same wether you want insert or update a
   *  User in the database.
   *  Returns 0 if the query is executed with no errors
   *  or -1 if any error occurs.
   */
   public int pushUsers(ArrayList<User> users){
      //pusha nel db l'oggetto in questione utilizzando dbManager


      for (int i = 0; i < users.size(); i++) {
         
         PreparedStatement pstmt = null;

         String
         pstmt = c.prepareStatement(sql)


         if (users[i].get) {


         }

      }

   }
   
   public String getLastLog(){ return error_logs.get(error_logs.size()-1); }
   public ArrayList<String> getLogs(){ /*TBD: The function must return a deep copy of the error_logs and not a reference (are u serious?!)*/}
   
   /*
   *  Returns a UUID
   */
   private String getUUID(){

      ResultSet rs;
      Statement stmt;
      String uuid;

      try {
         
         stmt = c.createStatement();
         rs = stmt.executeQuery("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";SELECT uuid_generate_v4();");
         uuid = rs.getString(1);

      } catch (Exception e) {
         
         error_logs.add(e.getMessage());
         return null;

      }

      return uuid;
          
   }
}