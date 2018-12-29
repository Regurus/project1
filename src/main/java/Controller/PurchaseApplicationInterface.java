package Controller;

import Model.PurchaseApplication;
import Model.Vacation;
import View.uiController;

public class PurchaseApplicationInterface extends VacationInteraction{

    public void getPublishedItems(){
        this.updateUI(activeConnection.getVacationsByName(LoginInterface.getCurrentUser()));
    }

    public void updateUI(Vacation[] vacations){
        if(vacations==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<vacations.length;i++){
            uiController.Ui.addPublishedItem(vacations[i]);
        }
    }

    public boolean hasApplicant(String vacation_id){
        return activeConnection.hasApplicant(vacation_id);
    }

    public void wiriteToDB(PurchaseApplication purchaseApplication){
        activeConnection.applyForPurchase(purchaseApplication.getVacation_id(),purchaseApplication.getApplicant());
    }

    public void declinePurchaseApplication(String vacation_id){
        activeConnection.declinePurchaseApplication(vacation_id);
    }
}
