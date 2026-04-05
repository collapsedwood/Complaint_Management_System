//remember for compiling we can use javac -d . Object/*.java Service/*.java Main.java
import Service.ComplaintManager;
import Service.UserManager;
import exceptions.UserAlreadyExistException;
import java.util.Scanner;
import model.User;
public class Main {
    public static void main(String[] args)
    {Scanner sc= new Scanner(System.in);

       User ActivUser;
        UserManager U1=new UserManager();
       try { 
        U1.SignUp("Yash", "work");
        U1.SignUp("Yash","work");
       boolean check= U1.login("Yash","work");
       if(check==true)
       {
ActivUser=U1.getCurrentUser();
ComplaintManager C1=new ComplaintManager();
    String description;
description=sc.nextLine();
C1.complaint_registration(ActivUser, description);


       }
       } 
       catch(IllegalArgumentException e){
    System.out.println(e.getMessage());
       }
catch(UserAlreadyExistException e)
{
    System.out.println(e.getMessage());
}
        
    }

}
