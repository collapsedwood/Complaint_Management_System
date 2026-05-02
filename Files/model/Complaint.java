package model;
import java.time.LocalDateTime;
public class Complaint{
    private int complaint_id;
    private int user_id ;
    private LocalDateTime comp_registration_Date;
    private String complaint_description;
    private Status status;
    public Complaint(int comp_id,int user_id,String comp_desc)
 {   
    complaint_id=comp_id;
    this.user_id=user_id;
    comp_registration_Date=LocalDateTime.now();
    complaint_description=comp_desc;
    status=Status.PENDING;
 }
 public Complaint(int comp_id,int user_id,String comp_desc,Status stat,LocalDateTime time)
 {
    complaint_id=comp_id;
    this.user_id=user_id;
    comp_registration_Date=time;
    complaint_description=comp_desc;
  status =stat;

 }
public void set_status(Status stat)
{
    status=stat;
}
public int get_compID()
{
    return complaint_id;
}

public LocalDateTime get_compRegDate()
{
    return comp_registration_Date;
}
public String get_compDesc()
{
    return complaint_description;
}
 public Status get_status()
{
    return status;
}
public int get_user_id()
{
    return this.user_id;
}
}
