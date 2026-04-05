package Service;
import java.util.HashMap;
import model.User;
import model.UserRole;
import exceptions.UserAlreadyExistException;
import java.lang.*;
public class UserManager{
   private HashMap<String,User> Users=new HashMap<>();
   private User CurrentUser;
    private static int user_id=1000;
  
   public static int giveUserId()
{
   return user_id++;

}
public User getCurrentUser()
{
   return CurrentUser;
}
public boolean  SignUp ( String name,String password) throws 
UserAlreadyExistException
{
   if(name==null || name.isEmpty())
   {
      throw new IllegalArgumentException("Username is empty");

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
 User newUser=new User(id,name,password,UserRole.Customer);
 Users.put(name, newUser);

return true;

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
}
