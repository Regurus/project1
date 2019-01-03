package Controller;

import Model.MessageSystemDatabase;

public abstract class MessageSystemInteraction {

    protected static MessageSystemDatabase activeConnection;
    protected MessageSystemInteraction(){
        if(activeConnection==null)
            activeConnection = new MessageSystemDatabase();
    }
}
