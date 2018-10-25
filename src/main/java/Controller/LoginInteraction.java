package Controller;

import Model.LoginDatabase;

import java.util.regex.Pattern;

/**
 * basic abstract class for extension by any interface interacting with login details
 */
public abstract class LoginInteraction {
    static LoginDatabase activeConnection;
    protected LoginInteraction(){
        if(activeConnection==null)
            activeConnection = new LoginDatabase();
    }
    protected boolean loginApprove(String login){
        //check with sql
        String[] tuple = activeConnection.getByLogin(login);
        String found = tuple[0];
        return found.equalsIgnoreCase(login);
    }
    public boolean passwordApprove(String password){
        //approves only passwords that include at least 1 number, 1 capital letter and one regular letter.
        return Pattern.matches("[0-9]", password) && Pattern.matches("[a-z]", password) && Pattern.matches("[A-Z]", password);
    }
    public String[] getUserInfo(String login){
        return activeConnection.getByLogin(login);
    }
    public void endSession(){
        activeConnection.closeConnection();
    }
    public boolean combinationApprove(String login,String password){
        String[] account = activeConnection.getByLogin(login);
        if(account==null)
            return false;
        return account[1].contentEquals(password);
    }
}
