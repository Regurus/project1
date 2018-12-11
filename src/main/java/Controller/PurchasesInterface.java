package Controller;

import Model.PurchasedVacation;
import Model.Vacation;
import View.UiController;
import View.purchasedItemController;

import java.time.LocalDate;

public class PurchasesInterface extends PurchasedVacationInteraction{

    public static UiController UI;
    public static purchasedItemController lastItem;

    public void getPublishedItems(){
        //this.updateUI(activeConnection.getVacationsByName(LoginInterface.getCurrentUser()));
    }

    public void updateUI(Vacation[] vacations){
        if(vacations==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<vacations.length;i++){
            UI.addPurchasedItem();
            lastItem.defineContent(vacations[i],"00");
        }
    }

    public PurchasedVacation[] getPurchasingHistory(String buyer){
        return activeConnection.getVacationsByBuyer(buyer);
    }

    public void addPVacation(Vacation purchased){
        String time_stamp = LocalDate.now().toString();
        PurchasedVacation purchasedVacation = new PurchasedVacation(purchased,time_stamp);
        activeConnection.createTuple(purchasedVacation.toStringArray());
    }

}
