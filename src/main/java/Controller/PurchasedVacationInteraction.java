package Controller;

import Model.PurchasedDatabase;

/**
 * basic abstract class for extension by any interface interacting with vacation details
 */
public abstract class PurchasedVacationInteraction {
    protected static PurchasedDatabase activeConnection;
    protected PurchasedVacationInteraction(){
        if(activeConnection==null)
            activeConnection = new PurchasedDatabase();
    }
}
