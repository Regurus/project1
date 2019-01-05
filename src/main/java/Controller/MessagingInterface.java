package Controller;

import Model.MessagingSession;

public class MessagingInterface extends MessageSystemInteraction{
    public static MessagingInterface MI;
    //user1 is the sender
    public void createNewConversation(String user1, String user2, String first_message){
        if(!this.sessionExists(user1,user2)){
            MessagingSession msg = new MessagingSession(user1,true,user2,false,LoginInterface.getCurrentUser()+"+:+"+first_message+';');
            activeConnection.createTuple(msg);
        }
        else{
            this.sendMessage(user1,user2,first_message);
        }
    }

    public void sendMessage(String user1, String user2, String message){
        activeConnection.appendToTuple(user1,user2,message);
    }

    //anecdote - try to validate if this authentication works if the users are flipped (if not, find a way to overcome this)
    public boolean sessionExists(String user1, String user2){
        return activeConnection.checkIfExists(user1,user2);
    }

    public MessagingSession[] getAllUsersConversations(String user){
        return activeConnection.getByUser(user);
    }
}
