package Controller;

import Model.PurchasedVacation;
import Model.Vacation;
import View.uiController;
import java.time.LocalDate;

public class PurchasesInterface extends PurchasedVacationInteraction{
    public static PurchasesInterface PI;

    public void getPublishedItems(){
        Vacation[] vacs = this.getPurchasingHistory(LoginInterface.getCurrentUser());
        this.updateUI(vacs);
    }

    public void updateUI(Vacation[] vacations){
        if(vacations==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<vacations.length;i++){
            uiController.Ui.addPurchasedItem((PurchasedVacation)vacations[i]);
        }
    }

    public PurchasedVacation[] getPurchasingHistory(String buyer){
        return activeConnection.getVacationsByBuyer(buyer);
    }

    public void addPVacation(Vacation purchased){
        String time_stamp = LocalDate.now().toString();
        PurchasedVacation purchasedVacation = new PurchasedVacation(purchased,time_stamp);
        activeConnection.createTuple(purchasedVacation.toStringArray());
        uiController.Ui.addPurchasedItem(purchasedVacation);
    }

}
