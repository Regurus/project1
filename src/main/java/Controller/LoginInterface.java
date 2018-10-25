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
            setCurrentUser(login);
            return true;
        }
        return false;
    }
    private static void setCurrentUser(String user){
        currentLogin=user;
    }
    public static void nullifyCurrentUser(){
        currentLogin=null;
    }
    public static String getCurrentUser(){
        return currentLogin;
    }
}
