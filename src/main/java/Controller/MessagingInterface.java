package Controller;

import Model.MessagingSession;

public class MessagingInterface extends MessageSystemInteraction{
    public void createNewConversation(String user1, String user2, String first_message){

    }

    public void sendMessage(String user1, String user2, String message){

    }

    //anecdote - try to validate if this authentication works if the users are flipped (if not, find a way to overcome this)
    public boolean sessionExists(String user1, String user2){
        return true;
    }

    public MessagingSession[] getAllUsersConversations(String user){
        return null;
    }
}
