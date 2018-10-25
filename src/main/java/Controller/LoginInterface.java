package Controller;


public class LoginInterface extends LoginInteraction{
    private static String currentLogin;//should be updated to null on exit
    public LoginInterface(){

    }
    @Override
    public boolean combinationApprove(String login,String password){
        String[] account = activeConnection.getByLogin(login);
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
    public static void nullifyCurrentUser(){
        currentLogin=null;
    }
    public static String getCurrentUser(){
        return currentLogin;
    }
}
