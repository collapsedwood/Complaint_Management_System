package Service;
import database.DbConnection;
import exceptions.UserAlreadyExistException;
import java.sql.*;
import java.util.HashMap;
import model.User;
import model.UserRole;
public class UserManager{
   private HashMap<String,User> Users=new HashMap<>();
   private User CurrentUser;
    private static int user_id=1;
  String sql;
   public static int giveUserId()
{
   return user_id++;

}
public User getCurrentUser()
{
   return CurrentUser;
}

public boolean  SignUp ( String name,String password) throws 
UserAlreadyExistException,SQLException
{  
   if(name==null || name.isEmpty())
   {
      throw new IllegalArgumentException("Username is empty");

   }
   String pattern = "^[a-zA-Z0-9]{4,15}$";
   if (!name.matches(pattern)) {
    throw new IllegalArgumentException("Username must be 4-15 alphanumeric characters (no spaces/symbols)");
}

   if(password==null || password.isEmpty())
   {
      throw new IllegalArgumentException(password);
   }
   if(Users.containsKey(name))
   {
      throw new UserAlreadyExistException("The name "+ name +" is already taken");
   }

  else
   {
      int id= giveUserId();
 User newUser=new User(id,name,password,UserRole.Customer);
 sql="INSERT INTO users (username,password,role) VALUES (?,?,?)";
 Users.put(name, newUser);
 try (Connection con = DbConnection.getconnection(); 
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, name);
    ps.setString(2, password);
    ps.setString(3, UserRole.Customer.name());
    ps.executeUpdate();
} 
return true;

}
   }
   public void UsernameCheckup(String name)  throws UserAlreadyExistException
   {
      if(Users.containsKey(name))
      {
throw new UserAlreadyExistException("The name "+ name +" is already taken");
   }
      }
   
   public boolean login(String name,String password) throws IllegalArgumentException

   {
       boolean  aut;
      User foundUser=Users.get(name);
      if (foundUser!=null)
      {
           aut= foundUser.verify(password);
           if (aut) {

   this.CurrentUser=foundUser;
   return true;
}
throw new IllegalArgumentException("Incorrect password");
     }
   
      else 
      {
         throw new IllegalArgumentException("Username is invalid!");
      }



}
public boolean registerAdmin(String name,String password) throws UserAlreadyExistException,SQLException
{
if(name==null || name.isEmpty())
   {
      throw new IllegalArgumentException("Username is empty");

   }
      String pattern = "^[a-zA-Z0-9]{4,15}$";
   if (!name.matches(pattern)) {
    throw new IllegalArgumentException("Username must be 4-15 alphanumeric characters (no spaces/symbols)");
}

   if(password==null || password.isEmpty())
   {
      throw new IllegalArgumentException(password);
   }
   if(Users.containsKey(name))
   {
      throw new UserAlreadyExistException("The name "+ name +"is already taken");
   }

  else
   {
      int id= giveUserId();
 User newUser=new User(id,name,password,UserRole.Admin);
  sql="INSERT INTO users (username,password,role) VALUES (?,?,?)";
 Users.put(name, newUser);
 try (Connection con = DbConnection.getconnection(); 
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, name);
    ps.setString(2, password);
    ps.setString(3, UserRole.Admin.name());
    ps.executeUpdate();
} 
return true;
}
}
public void promoteToAdmin(int targetUserId,User Actor)  throws SQLException
{
   if(Actor.getRole()!=UserRole.Admin)
   throw new SecurityException("Unauthorized: Only Admins can promote users.");
boolean found = false;
  for (User u : Users.values())
  {
   if (u.getUserId() == targetUserId)
   {  
      if(u.getRole()!=UserRole.Admin)
      {
         u.setUserRole(UserRole .Admin);
        sql= "UPDATE users SET role = 'Admin' WHERE user_id = ?";
try (Connection con = DbConnection.getconnection(); 
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setInt(1, targetUserId);
    ps.executeUpdate();
} 
      System.out.println("User is promoted to Admin");
      found=true;
      }
      else if(u.getRole()==UserRole.Admin)
      {
         System.out.println("User is Already a Admin");
      }
      break;
   }
  }
   if(!found)
       { System.out.println("Error: User ID " + targetUserId + " not found."); }

}
public void loadUsersFromDB() throws SQLException
{
   String sql="SELECT * FROM users";
   ResultSet rs;
   try(Connection  con= DbConnection.getconnection();
      PreparedStatement ps=con.prepareStatement(sql)){
     rs= ps.executeQuery();
     while(rs.next())
     {
   int db_id=rs.getInt("user_id");
   String name=rs.getString("username");
   String password=rs.getString("password");
   String roleval=rs.getString("role");
   UserRole role=UserRole.valueOf(roleval);
   User tempUser=new User(db_id, name, password, role);
   Users.put(name,tempUser);
if(db_id>=user_id)
{
   user_id=db_id;
}
 }
   user_id++;
   }
   catch(SQLException e)
   {
throw new SQLException("Database is not loading");
   }
}
}