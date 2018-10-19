package Controller;


public class LoginInterface extends LoginInteraction{

    public LoginInterface(){

    }
    public boolean combinationApprove(String login,String password){
        String expectedPasswordHash = " ";
        //get password hash from sql
        String actualPasswordHash = " ";
        //do the hashing by selected algorithm
        if(expectedPasswordHash.contentEquals(actualPasswordHash)&&this.loginApprove(login))
            return true;
        return false;
    }

}
