package Controller;


public class RegisterInterface {
    public boolean newLoginApprove(String login){
        //check with database if already used
        return true;
    }

    public boolean detailsApprove(String[] details){
        for (String s: details) {
            if(s.length()<1)
                return false;
        }
        return true;
    }
}
