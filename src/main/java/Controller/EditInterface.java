package Controller;

/**
 * class for controlling the edit details interface
 * before closure the interface must receive all 3 boolean approvals only then it can send a update complete message
 */
public class EditInterface extends LoginInteraction{

    public boolean updateLogin(String newLogin){
        boolean exists = this.loginApprove(newLogin);
        if(exists)
            return false;
        //update to sql
        return true;
    }
    public boolean updatePassword(String newPassword){
        boolean passwordOK = this.passwordApprove(newPassword);
        if(!passwordOK)
            return false;
        //update to sql
        return true;
    }
    public boolean updateDetails(String[] details){
        //string amount should be checked
        //update to sql
        return true;
    }
}
