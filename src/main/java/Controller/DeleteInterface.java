package Controller;

public class DeleteInterface extends LoginInteraction {
    //deleting by user name
    public void deleteDetails(String userName){
        activeConnection.deleteTuple(userName);
    }
}
