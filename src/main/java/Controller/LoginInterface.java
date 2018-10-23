package Controller;


public class LoginInterface extends LoginInteraction{
    private static String currentLogin;//should be updated to null on exit
    public LoginInterface(){

    }
    public boolean combinationApprove(String login,String password){
        String[] account = this.activeConnection.getTuple("login",login);
        if(account==null)
            return false;
        if(account[1].contentEquals(password)){
            this.setCurrentUser(login);
            return true;
        }
        return false;
    }
    private void setCurrentUser(String user){
        this.currentLogin=user;
    }
    public static String getCurrentUser(){
        return currentLogin;
    }
}
