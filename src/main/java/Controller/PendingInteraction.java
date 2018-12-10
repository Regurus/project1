package Controller;

import Model.PendingDatabase;

public abstract class PendingInteraction {
    protected static PendingDatabase activeConnection;
    protected PendingInteraction(){
        if(activeConnection==null)
            activeConnection = new PendingDatabase();
    }
}
