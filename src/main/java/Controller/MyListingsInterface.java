package Controller;

import Model.Vacation;
import View.uiController;
import View.myListingsItemController;

public class MyListingsInterface extends VacationInteraction{
    public static myListingsItemController current;

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
    //public static boolean acceptApplication(Vacation vacation)
}
