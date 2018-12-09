package Controller;

import Model.VacationDatabase;

/**
 * basic abstract class for extension by any interface interacting with vacation details
 */
public abstract class VacationInteraction {
    protected static VacationDatabase activeConnection;
    protected VacationInteraction(){
        if(activeConnection==null)
            activeConnection = new VacationDatabase();
    }
}
