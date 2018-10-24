package Controller;

import Model.LoginDatabase;

import java.util.regex.Pattern;

/**
 * basic abstract class for extension by any interface interacting with login details
 */
public abstract class LoginInteraction {
    protected LoginDatabase activeConnection;
    protected LoginInteraction(){
        this.activeConnection = new LoginDatabase();
    }
    protected boolean loginApprove(String login){
        //check with sql
        String[] tuple = this.activeConnection.getByLogin(login);
        String found = tuple[0];
        if(found.equalsIgnoreCase(login))
            return true;
        return false;
    }
    public boolean passwordApprove(String password){
        //approves only passwords that include at least 1 number, 1 capital letter and one regular letter.
        if(Pattern.matches("[0-9]",password)&&Pattern.matches("[a-z]",password)&&Pattern.matches("[A-Z]",password))
            return true;
        return false;
    }
    public void endSession(){
        this.activeConnection.closeConnection();
    }
}
