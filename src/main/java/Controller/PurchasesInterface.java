package Controller;

import Model.Vacation;
import View.UiController;
import View.purchasedItemController;

public class PurchasesInterface {

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

}
