package ir.tdaapp.diako.sharrNew.ViewModel;

/**
 * Created by Diako on 6/23/2019.
 */

public class VM_User {
    private String FullName;
    private String Email;
    private String CellPhone;

    public VM_User(String FullName,String Email,String CellPhone){
        this.FullName=FullName;
        this.Email=Email;
        this.CellPhone=CellPhone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }
}
