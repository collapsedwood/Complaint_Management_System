
package model;
public class User{
    private  int user_id;
    private  String user_name;
     private String password;
     private UserRole role;
    
 public User(int user_id,String user_name,String password,UserRole role)
    {
       this. user_id=user_id;
       this. user_name=user_name;
       this.password=password;
       this.role=role;
    }
public int getUserId()
{
    return user_id;

}
public String getUserName()
{
    return user_name;
}
public UserRole getRole()
{
    return role;
}
public void setPassword(String new_password)
{
    password=new_password;
}
public boolean verify(String en_password)
{
    if (password.equals(en_password))
    {
        return true;
    }
    else {
        return false;
    }
}
public void setUserRole(UserRole newRole)
{
  this.role=newRole;
}

}
