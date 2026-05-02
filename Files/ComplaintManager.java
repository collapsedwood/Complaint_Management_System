package Service;
import  database.DbConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import model.Complaint;
import model.Status;
import model.User;
import model.UserRole;
public class ComplaintManager{
    private final HashMap<Integer,Complaint> complaints=new HashMap<>();//now final is used here so we cannot reassign a map
    static int compID=1;
   String sql;

   static  int  Complaint_ID()
   {
    return compID++;

   }
    
    public void complaint_registration(User ActivUser,String description) throws SQLException
 {

    int complaint_id=Complaint_ID();
    int id=ActivUser.getUserId();

   Complaint c = new Complaint(complaint_id, id,description);
    complaints.put(complaint_id,c);
    sql="INSERT INTO complaints (user_id, description, status) VALUES (?, ?, ?)";
    try (Connection con = DbConnection.getconnection(); 
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setInt(1, ActivUser.getUserId());
    ps.setString(2, description);
    ps.setString(3, Status.PENDING.name());
    ps.executeUpdate();
} 
 }
   public String view_complaint(User ActiUser) {
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    for (Complaint m : complaints.values()) {
        if (m.get_user_id() == ActiUser.getUserId()) {
            found = true;
            sb.append("Complaint ID: ").append(m.get_compID()).append("\n")
              .append("Registration Date: ").append(m.get_compRegDate().format(formater)).append("\n")
              .append("Description: ").append(m.get_compDesc()).append("\n")
              .append("Status: ").append(m.get_status()).append("\n")
              .append("---------------------------\n");
        }
    }
    if (!found) {
        return "No complaints found for your account.";
    }
    return sb.toString();
}

    public String view_all_complaints() {
    if (complaints == null || complaints.isEmpty()) {
        return "There are no complaints registered till now.";
    }
    
    StringBuilder sb = new StringBuilder();
    DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    for (Complaint m : complaints.values()) {
        sb.append("Complaint ID: ").append(m.get_compID()).append("\n")
          .append("User ID: ").append(m.get_user_id()).append("\n")
          .append("Date: ").append(m.get_compRegDate().format(formater)).append("\n")
          .append("Desc: ").append(m.get_compDesc()).append("\n")
          .append("Status: ").append(m.get_status()).append("\n")
          .append("---------------------------\n");
    }
    return sb.toString();
}
     
    
   
   public void UpdateStatus(int comp_id, Status newStatus) throws SQLException
   { 
      Complaint c =complaints.get(comp_id);//using get to find the values

      if (c!=null)
      {c.set_status(newStatus);
         sql="UPDATE  complaints SET status=? WHERE complaint_id=?";
      try (Connection con = DbConnection.getconnection(); 
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, newStatus.name());
    ps.setInt(2, comp_id);
    ps.executeUpdate();
} 

     System.out.println("Status updated");}
     else{
      System.out.println("The Complaint Id is invalid!");
     }
         
      }
public void generate_report(User ActiUser)throws IOException
{
    int resolved=0;
    int pending=0;
    int in_progress=0;
    if (ActiUser.getRole()==UserRole.Admin)
   {for(Complaint c:complaints.values())
   {
      if(c.get_status()==Status.PENDING)
      {
         pending++;
      }
      else if(c.get_status()==Status.IN_PROGRESS)
      {
         in_progress++;
      }
      else{
         resolved++;
      }
   }
   try(
   FileWriter writer=new FileWriter("Report.txt"))
   {writer.write("The no of complaints are resolved are "+resolved+"\n");
   writer.write("The no of complaints are pending are "+pending+"\n");
   writer.write("The no of complaints are in progress "+in_progress+"\n");
   
   }
   System.out.println("Report generated Successfully");
   
   
   }
}
public void loadComplaintsFromDB () throws SQLException
{
    sql="SELECT * FROM complaints ";
   try(Connection con=DbConnection.getconnection();
    PreparedStatement ps=con.prepareStatement(sql))
    {
      ResultSet rs=ps.executeQuery();
      while(rs.next())
     {
      int dbcomp_id=rs.getInt("complaint_id");
     int dbuserid=rs.getInt("user_id");
     String dbdesc=rs.getString("description");
     String statval=rs.getString("status");
     Status status=Status.valueOf(statval);
     Timestamp sqlTime = rs.getTimestamp("reg_date");
    LocalDateTime Time = sqlTime.toLocalDateTime();

     Complaint c=new Complaint(dbcomp_id,dbuserid,dbdesc,status,Time);
     complaints.put(dbcomp_id,c);
     if (dbcomp_id>=compID)
     {
      compID=dbcomp_id;
     }

     }
     compID++;
     
    }
    
}
}
