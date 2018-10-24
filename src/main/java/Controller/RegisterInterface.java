package Controller;


public class RegisterInterface extends LoginInteraction {
    public boolean newLoginApprove(String login){
        String[] res = activeConnection.getByLogin(login);
        if(res==null)
            return true;
        else return false;
    }

    public boolean detailsApprove(String[] details){
        for (String s: details) {
            if(s.length()<1)
                return false;
        }
        return true;
    }
    public void wiriteToDB(String[] details){
        activeConnection.createTuple(details);
    }
}
