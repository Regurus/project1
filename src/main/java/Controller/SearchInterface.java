package Controller;

import Model.Vacation;
import Model.VacationDatabase;
import View.ResultItemController;
import View.UiController;

import java.time.LocalDate;

public class SearchInterface extends VacationInteraction{
    public static ResultItemController lastItem;
    public static UiController ui;

    public void search(String text, LocalDate value) {
        updateUi(this.activeConnection.getTuplesByLocationANDDate(text,value.toString()));
    }

    private void updateUi(Vacation[] list){
        if(list==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<list.length;i++){
            ui.addResultItem();
            lastItem.defineContent(null,list[i].getDest_city(),list[i].getDest_region(),""+list[i].getVacationLenght(),list[i].getPrice());
        }
    }
}
