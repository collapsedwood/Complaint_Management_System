//remember for compiling we can use javac -d . Object/*.java Service/*.java Main.java
import Service.ComplaintManager;
import Service.UserManager;
import exceptions.UserAlreadyExistException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Status;
import model.User;
import model.UserRole;
public class Main {
    public static void main(String[] args)
    {
        
            Scanner sc= new Scanner(System.in);
      int option;
      String  name;
      String password;
      int id;
      String desc;
       User ActivUser=null;
       UserManager U1=new UserManager();
       ComplaintManager C1=new ComplaintManager();//here was something that was questiong me 
      
       try {
         U1.loadUsersFromDB();
         C1.loadComplaintsFromDB();  
       } 
       catch (SQLException e)
    {
        System.out.println(e.getMessage());
    }

      try{ 
        U1.registerAdmin("Admin", "admin123");
         
    }
    catch (UserAlreadyExistException |SQLException e)
    {
        System.out.println(e.getMessage());
    }

       while(true)
       {
        try{
            if(ActivUser==null)
        {
            System.out.println("What you have to do");
            System.out.println("1.SignUp\n2.Login\n3.Exit");
            option=sc.nextInt();
            sc.nextLine();
            
                switch(option)
            {
            case 1:while (true) { 
                System.out.println("Enter a Username to create a account:");
                name= sc.nextLine();
               try {
                   U1.UsernameCheckup(name);
               } catch (UserAlreadyExistException e) {
                System.err.println("User Already exits try another username");
                continue;
               }     
               break;   
            }                                                                                
                System.out.println("Enter a Strong Password for your account:");
                password=sc.nextLine();
                U1.SignUp(name,password);
                System.out.println("Account created Successfully");
                 break;
             case 2: 
             System.out.println("Username:");
                   name= sc.nextLine();
                   System.out.println("Passsword");
                   password=sc.nextLine();
                   boolean check= U1.login(name,password);
                 if(check==true)
                {
            ActivUser=U1.getCurrentUser();
            System.out.println("Logged in Successfully");
               }
            break;
             case 3:
                            System.out.println("Exiting system.");
                            System.exit(0);
                            break;
           }
            
       
        
    }


    else if(ActivUser.getRole()==UserRole.Admin)
    {
        System.out.println("Welcome Admin!");
        System.out.println("What do you want to do ");
        System.out.println("1.View All Complaints\n2.Update Complaint Status\n3.Promote User to Admin\n4.Generate Report\n5.Logout");
        option=sc.nextInt();
      sc.nextLine();
        switch(option)
        {
            case 1:
            C1.view_all_complaints();
            break;
            case 2: 
            System.out.println("Enter the Complaint ID you want to update:");
            id=sc.nextInt();
                    sc.nextLine();
                    int choice;
                    System.out.println("1. PENDING, 2. IN_PROGRESS, 3. RESOLVED");
                    choice=sc.nextInt();
                    sc.nextLine();
                    Status stat;
                    if(choice==1)
                    {
                         stat=Status.PENDING;
                    }
                    else if(choice==2)
                    {
                      stat=Status.IN_PROGRESS;
                    }
                    else{
                        stat=Status.RESOLVED;
                    }
                   C1. UpdateStatus(id, stat);
                    break;
                    case 3:
                        System.out.println("Enter id of the user");
                         id=sc.nextInt();
                         sc.nextLine();
                       U1. promoteToAdmin( id,ActivUser);
                       break;
                          case 4:  C1.generate_report(ActivUser);
                                   break;
                                case 5:
                                    ActivUser=null;
                                    System.out.println("Logout successfully");
                                    break;
                }

            }
    else {
        System.out.println("What you want to do ");
        System.out.println("1.Raise Complaint\n2.View Complaint\n3.Logout");
        option=sc.nextInt();
        sc.nextLine();
        switch(option)
        {
        case 1:  
        System.out.println("Enter info about complaint");
desc=sc.nextLine();
     C1. complaint_registration(ActivUser,desc);
     System.out.println("Complaint Registered successfully");
     break;
        case 2:   
       C1. view_complaint( ActivUser);
        break;
        case 3: ActivUser=null;
             System.out.println("Logout successfully");
             break;


            }
    }

}
        catch(InputMismatchException e){
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
        }
catch(IllegalArgumentException e){
      System.out.println(e.getMessage());
       }
   catch(UserAlreadyExistException e)
   {
      System.out.println(e.getMessage());
      
     }
     catch(SQLException e)
     {
        System.err.println(e.getMessage());
        
     }
      catch(IOException e)
                       {
                        System.out.println(e.getMessage());
                       }

    }
}
}
