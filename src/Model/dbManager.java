package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
   *  If the result of the query is only one User, a one-legth ArrayList is returned.
   */
   public ArrayList<User> retreiveUsers(userFieldID fieldID, String val){
      
      ArrayList<User> result = new ArrayList<User>();
      ResultSet rs;

      switch (fieldID) {

         case USERID:
            //Query on "userId"
            try {

               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE userId = ?");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User();
                  //User set method needed
                  usr.set_user_id(rs.getString("userId"));
                  usr.set_email(rs.getString("email"));
                  usr.set_name(rs.getString("name"));
                  usr.set_password(rs.getString("password"));

                  result.add(usr);

               }

               return result;
               
            } catch (Exception e) {
               
               error_logs.add(e.getMessage());
               return null;

            }
         
         case EMAIL:
            //Query on "email"
            try {

               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE email = ?");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User();
                  //User set method needed
                  usr.set_user_id(rs.getString("userId"));
                  usr.set_email(rs.getString("email"));
                  usr.set_name(rs.getString("name"));
                  usr.set_password(rs.getString("password"));

                  result.add(usr);

               }

               return result;
               
            } catch (Exception e) {
               
               error_logs.add(e.getMessage());
               return null;

            }
         
         case NAME:
            //Query on "name"
            try {

               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE name = ?");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User();
                  //User set method needed
                  usr.set_user_id(rs.getString("userId"));
                  usr.set_email(rs.getString("email"));
                  usr.set_name(rs.getString("name"));
                  usr.set_password(rs.getString("password"));

                  result.add(usr);

               }

               return result;
               
            } catch (Exception e) {
               
               error_logs.add(e.getMessage());
               return null;

            }
      
         default:
            return null;
      }
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