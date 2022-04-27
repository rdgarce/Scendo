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
   *  is the same wether you want to insert or update a
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
   
   /*
   *  Push to the database a User.
   *  The function determins if the User need to be
   *  INSERTED or UPDATED into the db so the usage 
   *  is the same wether you want to insert or update a
   *  User in the database.
   *  Returns 0 if the query is executed with no errors
   *  or -1 if any error occurs.
   */
   public int pushUser(User user){

      try {
         
         PreparedStatement insert_stmt = c.prepareStatement("INSERT INTO Users VALUES(?,?,?,?);");
         PreparedStatement update_stmt = c.prepareStatement("UPDATE Users SET name = ?, email = ?, password = ? WHERE userId = ?;");

         int test = search_user_by_id(user);

         if (test == 1) {
            //Here we need to UPDATE the User
            update_stmt.setString(1, user.getName());
            update_stmt.setString(2, user.getEmail());
            update_stmt.setString(3, user.getPassword());
            update_stmt.setObject(4, UUID.fromString(user.getUserID()));
            update_stmt.executeQuery();
            
         }
         else if (test == -1 ){
            //Here we need to INSERT the User
            insert_stmt.setObject(1, UUID.fromString(user.getUserID()));
            insert_stmt.setString(2, user.getName());
            insert_stmt.setString(3, user.getEmail());
            insert_stmt.setString(4, user.getPassword());
            insert_stmt.executeQuery();

         }
      
         return 0;

      } catch (Exception e) {
         error_logs.add(e.getMessage());
         return -1;
      }
           
   }

   /*
   *  Push the [scendo] and its invited users to the database.
   *  The function determins if the [scendo] has to be inserted
   *  or updated. In the former case they are also inserted all
   *  the invited users, in the latter the function updates [scendo]
   *  information and edit the User_Scendo table so that it reflects the
   *  [scendo.getInvitedUsers()] list.
   *  Returns 0 if the process is executed withour errors or
   *  -1 if any one occurs.
   */
   public int pushScendo(Scendo scendo){

      try {
         
         PreparedStatement insert_scendo_stmt = c.prepareStatement("INSERT INTO Scendos VALUES(?,?,?);");
         PreparedStatement update_scendo_stmt = c.prepareStatement("UPDATE Scendos SET location = ?, scendoTime = ? WHERE scendoId = ?;");

         PreparedStatement insert_user_scendo_stmt = c.prepareStatement("INSERT INTO User_Scendo VALUES(?,?,?);");
         PreparedStatement delete_user_scendo_stmt = c.prepareStatement("DELETE FROM User_Scendo WHERE userId = ? AND scendoId = ?;");

         
         int test = search_scendo_by_id(scendo);

         if (test == 1) {
            /*
            *  Here we Update the Scendo
            *  So we need to update:
            *     - The Scendo itself (location, time).
            *     - The list of the invited Users, so the User_Scendo table, inserting and deleting records.
            */

            update_scendo_stmt.setString(1, scendo.getLocation());
            update_scendo_stmt.setTimestamp(2, scendo.getTime());
            update_scendo_stmt.setObject(3, UUID.fromString(scendo.getScendoID()));
            
            //Getting every invited User to this Scendo from the User_Scendo table
            PreparedStatement get_db_inv_usrs = c.prepareStatement("SELECT * FROM User_Scendo WHERE scendoId = ?;");
            get_db_inv_usrs.setObject(1, UUID.fromString(scendo.getScendoID()));
            ResultSet db_inv_usrs = get_db_inv_usrs.executeQuery();
            
            //Checking wether an invited user has to be added or deleted
            ArrayList<String> inv_usrs = scendo.getInvitedUsers();
            while (db_inv_usrs.next()) {

               String db_inv_usr_id = db_inv_usrs.getObject("userId").toString();
               if (db_inv_usrs.getBoolean("isCreator"))
                     continue;
               
               if (inv_usrs.contains(db_inv_usr_id)) {
                  /*
                  *  Here we fall if the user in the db is
                  *  present in the [inv_usrs] so nothing has
                  *  to be done apart from removing
                  *  that user from the [inv_usrs]
                  */
                  inv_usrs.remove(db_inv_usr_id);

               }
               else{
                  /*
                  *  If the invited user in the db is not in the [inv_usrs]
                  *  it means it has to be deleted (from the db) because
                  *  the new list does not contain it
                  */                  
                  delete_user_scendo_stmt.setObject(1, UUID.fromString(db_inv_usr_id));
                  delete_user_scendo_stmt.setObject(2, UUID.fromString(scendo.getScendoID()));
                  delete_user_scendo_stmt.addBatch();

               }

            }

            /*
            *  Here we are left with a [inv_usrs] that contains only 
            *  the invited user that has to be added to the db
            */
            Iterator<String> inv_usr_it = inv_usrs.iterator();
            while (inv_usr_it.hasNext()) {

               String usr = inv_usr_it.next();
               insert_user_scendo_stmt.setObject(1, UUID.fromString(usr));
               insert_user_scendo_stmt.setObject(2, UUID.fromString(scendo.getScendoID()));
               insert_user_scendo_stmt.setBoolean(3, false);
               insert_user_scendo_stmt.addBatch();
               
            }

            c.setAutoCommit(false);

            delete_user_scendo_stmt.executeBatch();
            insert_user_scendo_stmt.executeBatch();

            c.commit();
            c.setAutoCommit(true);
            
         }
         else if (test == -1) {
            /*
            *  Here we Insert the Scendo
            *  So we need to insert:
            *     - The Scendo itself (location, scendoTime).
            *     - The list of the invited Users, so records in the User_Scendo table.
            */
            
            insert_scendo_stmt.setObject(1, UUID.fromString(scendo.getScendoID()));
            insert_scendo_stmt.setString(2, scendo.getLocation());
            insert_scendo_stmt.setTimestamp(3, scendo.getTime());
            insert_scendo_stmt.execute();

            //Inserting the Creator-of-the-Scendo in User_Scendo
            insert_user_scendo_stmt.setObject(1, UUID.fromString(scendo.getCreatorUserID()));
            insert_user_scendo_stmt.setObject(2, UUID.fromString(scendo.getScendoID()));
            insert_user_scendo_stmt.setBoolean(3, true);
            insert_user_scendo_stmt.addBatch();

            //Inserting the invited users inside User_Scendo      
            Iterator<String> inv_users = scendo.getInvitedUsers().iterator();
            while (inv_users.hasNext()) {
               
               String usr = inv_users.next();
               insert_user_scendo_stmt.setObject(1, UUID.fromString(usr));
               insert_user_scendo_stmt.setObject(2, UUID.fromString(scendo.getScendoID()));
               insert_user_scendo_stmt.setBoolean(3, false);
               insert_user_scendo_stmt.addBatch();
            
         }

         c.setAutoCommit(false);

         insert_user_scendo_stmt.executeBatch();

         c.commit();
         c.setAutoCommit(true);

         }

         return 0;


      } catch (Exception e) {
         error_logs.add(e.getMessage());
         return -1;
      }
   }

   public int pushScendos(ArrayList<Scendo> scendos){
      //TBD
      return 0;
   }

   public ArrayList<Scendo> retreiveScendos(scendoFieldID fieldID, String val){
      //TBD
   }

   public String getLastLog(){
      return this.error_logs.get(error_logs.size()-1);
   }

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

   /*
   *  Search if there is a Scendo in the database
   *  that match [scd] scendoId.
   *  If the Scendo is found, return 1 else -1.
   *  0 is returned if an error occurs.
   */
   private int search_scendo_by_id(Scendo scd){

   PreparedStatement pstmt;
   ResultSet rs;

      try {
      
         pstmt = c.prepareStatement("SELECT * FROM Scendos WHERE scendoId = ?");
      

         pstmt.setObject(1, UUID.fromString(scd.getScendoID()));

      
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

}
