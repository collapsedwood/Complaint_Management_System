package Service;
import java.util.HashMap;
import model.Complaint;
import model.User;

public class ComplaintManager{
    private HashMap<Integer,Complaint> complaints=new HashMap<>();
    static int compID=0;
   

   static  int  Complaint_ID()
   {
    return compID++;

   }
    
    public void complaint_registration(User ActivUser,String description)
 {
    int complaint_id=Complaint_ID();
    int id=ActivUser.getUserId();

   Complaint c = new Complaint(complaint_id, id,description);
    complaints.put(complaint_id,c);
 }
  
}
