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
        this.activeConnection.editTuple("login",newLogin,LoginInterface.currentLogin);
        return true;
    }
    public boolean updatePassword(String newPassword){
        boolean passwordOK = this.passwordApprove(newPassword);
        if(!passwordOK)
            return false;
        this.activeConnection.editTuple("password",newPassword,LoginInterface.currentLogin);
        return true;
    }
    //detail field should be: name or lastName or address
    public boolean updateDetails(String newDetail,String detailField){
        this.activeConnection.editTuple(detailField,newDetail,LoginInterface.currentLogin);
        return true;
    }
}
