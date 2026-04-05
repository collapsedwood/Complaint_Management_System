package model;
import java.time.LocalDateTime;
public class Complaint{
    private int complaint_id;
    private int user_id ;
    private LocalDateTime comp_registration_Date;
    private String complaint_description;
    private String status;
    public Complaint(int comp_id,int user_id,String comp_desc)
 {   
    complaint_id=comp_id;
    this.user_id=user_id;
    comp_registration_Date=LocalDateTime.now();;
    complaint_description=comp_desc;
    status="pending";
 }
public void set_status(String stat)
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
 public String get_status()
{
    return status;
}
}
