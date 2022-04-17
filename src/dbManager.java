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

   public int closeConnection(){

      try {

         this.c.close();

      } catch (Exception e) {

         error_logs.add(e.getMessage());
         return -1;
      }

      return 0;
   }

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

   //public ResultSet executeQuery(PreparedStatement statement){

      //Todo
   //}
   
   public String getLastLog(){ return error_logs.get(error_logs.size()-1); }
   
   //public ArrayList<String> getLogs(){ /*TBD*/}
   
}