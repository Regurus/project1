package Controller;

import Model.MessagingSession;
import View.MessagingItemController;

public class MessagingInterface extends MessageSystemInteraction{
    public static MessagingInterface MI;
    //user1 is the sender
    public void createNewConversation(String user1, String user2, String first_message){
        if(!this.sessionExists(user1,user2)){
            MessagingSession msg = new MessagingSession(user1,true,user2,false,first_message+';');
            activeConnection.createTuple(msg);
        }
        else{
            this.sendMessage(user1,user2,first_message);
        }
    }

    public void sendMessage(String user1, String user2, String message){
        if(message.contains(MessagingItemController.delimiter))
            activeConnection.appendToTuple(user1,user2,message);
        else
            activeConnection.appendToTuple(user1,user2,LoginInterface.getCurrentUser()+ MessagingItemController.delimiter+message);
    }

    //anecdote - try to validate if this authentication works if the users are flipped (if not, find a way to overcome this)
    public boolean sessionExists(String user1, String user2){
        return activeConnection.checkIfExists(user1,user2);
    }

    public MessagingSession[] getAllUsersConversations(String user){
        return activeConnection.getByUser(user);
    }
}
