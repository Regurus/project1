package Controller;

import Model.LoginDatabase;

import java.util.regex.Pattern;

/**
 * basic abstract class for extension by any interface interacting with login details
 */
public abstract class LoginInteraction {
    protected  static LoginDatabase activeConnection;
    protected LoginInteraction(){
        if(activeConnection==null)
            activeConnection = new LoginDatabase();
    }
    protected boolean loginApprove(String login){
        //check with sql
        String[] tuple = activeConnection.getByLogin(login);
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
        activeConnection.closeConnection();
    }
}
