package Controller;


public class LoginInterface extends LoginInteraction{
    public static String currentLogin;//should be updated to null on exit
    public LoginInterface(){
    }
    public boolean combinationApprove(String login,String password){
        String[] account = this.activeConnection.getTuple("login",login);
        if(account==null)
            return false;
        if(account[1].contentEquals(password)){
            currentLogin=login;
            return true;
        }
        return false;
    }

}
