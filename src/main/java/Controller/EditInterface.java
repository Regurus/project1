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
        activeConnection.editTuple("login",newLogin,LoginInterface.getCurrentUser());
        return true;
    }
    public boolean updatePassword(String newPassword){
        boolean passwordOK = this.passwordApprove(newPassword);
        if(!passwordOK)
            return false;
        activeConnection.editTuple("password",newPassword,LoginInterface.getCurrentUser());
        return true;
    }
    //detail field should be: name or lastName or address
    public boolean updateDetails(String newDetail,String detailField){
        activeConnection.editTuple(detailField,newDetail,LoginInterface.getCurrentUser());
        return true;
    }
}
