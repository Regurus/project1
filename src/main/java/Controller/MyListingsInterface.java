package Controller;

import Model.Vacation;
import View.UiController;
import View.myListingsItemController;

public class MyListingsInterface extends VacationInteraction{
    public static myListingsItemController current;
    public static UiController ui;

    public void getPublishedItems(){

    }

    public void updateUI(Vacation[] vacations){
        if(vacations==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<vacations.length;i++){
            ui.addPublishedItem();
            current.setProperties(vacations[i],false);
        }
    }
}
