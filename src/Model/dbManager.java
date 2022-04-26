package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class dbManager {

   private String db_host_name;
   private String usr;
   private String pswd;
   private Connection c;
   private ArrayList<String> error_logs;
   private ArrayList<ResultSet> log_db;


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
      this.log_db = new ArrayList<ResultSet>();
      
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

               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE userId = ?;");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User(rs.getString("userId"), 
                                       rs.getString("email"), 
                                       rs.getString("name"), 
                                       rs.getString("password"));

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

               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE email = ?;");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User(rs.getString("userId"), 
                                       rs.getString("email"), 
                                       rs.getString("name"), 
                                       rs.getString("password"));

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
               
               PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE name = ?;");
               stmt.setString(1, val);
               rs = stmt.executeQuery();
               
               while (rs.next()) {

                  User usr = new User(rs.getString("userId"), 
                                       rs.getString("email"), 
                                       rs.getString("name"), 
                                       rs.getString("password"));

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

      try {
         
         Iterator<User> usr_it = users.iterator();
         int insert_count = 0;
         int update_count = 0;
         PreparedStatement insert_stmt = c.prepareStatement("INSERT INTO Users VALUES(?,?,?,?);");
         PreparedStatement update_stmt = c.prepareStatement("UPDATE Users SET name = ?, email = ?, password = ? WHERE userId = ?;");

         while (usr_it.hasNext()) {

            User us = usr_it.next();
            int test = search_user_by_id(us);

            if (test == 1 ) {
               //Here we need to UPDATE the User
               
               update_stmt.setString(1, us.getName());
               update_stmt.setString(2, us.getEmail());
               update_stmt.setString(3, us.getPassword());
               update_stmt.setObject(4, UUID.fromString(us.getUserID()));
               update_stmt.addBatch();
               update_count += 1;
            }
            else if (test == -1 ){
               //Here we need to INSERT the User
               
               insert_stmt.setObject(1, UUID.fromString(us.getUserID()));
               insert_stmt.setString(2, us.getName());
               insert_stmt.setString(3, us.getEmail());
               insert_stmt.setString(4, us.getPassword());
               insert_stmt.addBatch();
               insert_count += 1;
            }
         }

         c.setAutoCommit(false);

         if (insert_count > 0)
            insert_stmt.executeBatch();
         
         if (update_count > 0)
            update_stmt.executeBatch();

         c.commit();
         c.setAutoCommit(true);
         
         return 0;

      } catch (Exception e) {
         error_logs.add(e.getMessage());
         return -1;
      }

   }
   
   public String getLastLog(){ return this.error_logs.get(error_logs.size()-1); }

   public ArrayList<String> getLogs(){ 
      
      ArrayList <String> copy = new ArrayList<String>();

      copy.addAll(this.error_logs);
   
      return copy;
   
   }
   
   public void storeLogDb(){

      String file_path = "log/query_scendo.log";
      String dir_name = "log";
      
      try {

         File file = new File(file_path);
         File dir = new File(dir_name);
         if(file.isFile()==false)
            dir.mkdir();
            

         FileWriter w = new FileWriter(file_path,true);
        
         for (ResultSet rs: log_db) {
               w.write(rs.toString()+"\n");
         }
         w.flush();
         w.close();
      } catch (IOException e) {
         error_logs.add(e.getMessage());
      }

   }
   
   /*
   *  Call this method to obtain a suitable
   *  Id for a User or a Scendo.
   *  Returns a String containing a UUID 
   *  by executing a query on the database,
   *  or null object if any error occurs.
   */
   public String getUUID(){

      ResultSet rs;
      Statement stmt;
      String uuid;

      try {
         
         stmt = c.createStatement();
         stmt.execute("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";");
         rs = stmt.executeQuery("SELECT uuid_generate_v4();");
         rs.next();
         uuid = rs.getString(1);
         log_db.add(rs);
         

      } catch (SQLException e) {
         
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
         log_db.add(rs);


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
  private int search_user_by_email(User us){

   PreparedStatement pstmt;
   ResultSet rs;

   try {
    
      pstmt = c.prepareStatement("SELECT * FROM Users WHERE email = ?");
    
      pstmt.setString(1, us.getEmail());
    
      rs = pstmt.executeQuery();
      
      log_db.add(rs);


      if(rs.next() == false)
         return -1;
      else
         return 1;

   } catch (SQLException e) {
   
      error_logs.add(e.getMessage());
    
      return 0;
   
   }
   
   }

   /*
   *  Search if there is a User in the database
   *  that match [us] userId.
   *  If the User is found, return 1 else -1.
   *  0 is returned if an error occurs.
   */
   private int search_user_by_id(User us){

      PreparedStatement pstmt;
      ResultSet rs;

      try {
      
         pstmt = c.prepareStatement("SELECT * FROM Users WHERE userId = ?");
      

         pstmt.setObject(1, UUID.fromString(us.getUserID()));

      
         rs = pstmt.executeQuery();
         
         log_db.add(rs);


         if(rs.next() == false)
            return -1;
         else
            return 1;

      } catch (SQLException e) {
      
         error_logs.add(e.getMessage());
      
         return 0;
      
      }
      
   }

   private User load_user_from_id(String id) {

      try{

         ResultSet rs;

         PreparedStatement stmt = c.prepareStatement("SELECT * FROM Users WHERE userId = ?;");

         stmt.setString(1,id);

         rs = stmt.executeQuery();
         log_db.add(rs);
         User us = new User(rs.getString("userId"), 
                              rs.getString("email"), 
                              rs.getString("name"), 
                              rs.getString("password"));
         return us;

      }catch(SQLException e){

         error_logs.add(e.getMessage());

         return null;

      }

   }

   private void store_user_on_db(User us){

      try{
      
         PreparedStatement stmt = c.prepareStatement("INSERT INTO Users VALUES(?,?,?,?);");

         stmt.setObject(1, UUID.fromString(us.getUserID()));

         stmt.setString(2,us.getName());

         stmt.setString(3,us.getEmail());

         stmt.setString(4,us.getPassword());

         stmt.execute();


      }catch(SQLException e){

         error_logs.add(e.getMessage());
      
      }
      
      
   }

}
