package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                  usr.setUserID(rs.getString("userId"));
                  usr.setEmail(rs.getString("email"));
                  usr.setName(rs.getString("name"));
                  usr.setPassword(rs.getString("password"));

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
                  usr.setUserID(rs.getString("userId"));
                  usr.setEmail(rs.getString("email"));
                  usr.setName(rs.getString("name"));
                  usr.setPassword(rs.getString("password"));

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
                  usr.setUserID(rs.getString("userId"));
                  usr.setEmail(rs.getString("email"));
                  usr.setName(rs.getString("name"));
                  usr.setPassword(rs.getString("password"));

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
   *Load user from email
   *commit a query on database 
   * 
   * 
   */
   
   private User load_user_from_email(String email) {

      try{

         ResultSet rs;

         PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE email = ?;");

         stmt.setString(1, email);

         rs = stmt.executeQuery();

         User us = new User(rs.getString("email"),rs.getString("name"),rs.getString("password"));

         return us;

      }catch(SQLException e){

         error_logs.add(e.getMessage());

         return null;

      }

   }

   private User load_user_from_name(String name){


      try{

         ResultSet rs;
         
         PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE name = ?;");
         
         stmt.setString(1, name);
         
         rs = stmt.executeQuery();
         
         User us = new User(rs.getString("email"),rs.getString("name"),rs.getString("password"));
         
         return us;
      
      }catch(SQLException e){
      
         error_logs.add(e.getMessage());

         return null;
      
      }


   }

   private User load_user_from_user_id(String usid){

      try{

         ResultSet rs;
         
         PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE userId = ?;");
         
         stmt.setString(1, usid);
         
         rs = stmt.executeQuery();
         
         User us = new User(rs.getString("email"),rs.getString("name"),rs.getString("password"));
         
         return us;
      
      }catch(SQLException e){
      
         error_logs.add(e.getMessage());

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

      for (int i = 0; i < users.size(); i++) {
         
         if(this.search_user_from_email(users.get(i))== -1){

            //push on db

         }else{

            //update the db

         }

      }
      return 1;
   }
   
   public String getLastLog(){ return error_logs.get(error_logs.size()-1); }
   public ArrayList<String> getLogs(){ /*TBD: The function must return a deep copy of the error_logs and not a reference*/}
   
   /*
   *  Returns a String containing a UUID 
   *  by executing a query on the database,
   *  or null object if any error occurs.
   */
   private String get_UUID(){

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

   /*
   *  Check if the given [str] is a correct UUID.
   *  Returns 0 if it's the case or -1 if the string
   *  is not a correct UUID or any error occurs.
   *  getLastLog() method can be called to retreive 
   *  the error message of the last executed operation
   */ 
   private int test_UUID(String str){

      PreparedStatement stmt;
      ResultSet rs;

      try {

         stmt = c.prepareStatement("SELECT uuid_or_null('?') IS NULL;");
         stmt.setString(1, str);
         rs = stmt.executeQuery();

         if (rs.getBoolean(1) == true){
            
            error_logs.add("The provided string is not an UUID");
            return -1;
            
         }
         else
            return 0;
         
      } catch (Exception e) {
         
         error_logs.add(e.getMessage());
         return -1;

      }
      
   }

   /*
   *  Search if there is a User in the database
   *  that match [us] email.
   *  If the User is found, return 1 else -1.
   *  0 is returned if an error occurs.
   */
  private int search_user_from_email(User us){

   PreparedStatement pstmt;
   ResultSet rs;

   try {
    
      pstmt = c.prepareStatement("SELECT email FROM Users WHERE email = ?");
    
      pstmt.setString(1, us.getEmail());
    
      rs = pstmt.executeQuery();

      if(rs.next() == false)
         return -1;
      else
         return 1;

   } catch (SQLException e) {
   
      error_logs.add(e.getMessage());
    
      return 0;
   
   }
   
   }

}
