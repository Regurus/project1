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
        if(text.equals("")){
            updateUi(activeConnection.getTuplesByDate(value.toString()));
            return;
        }
        if(value == null){
            updateUi(activeConnection.getTuplesByLocation(text));
            return;
        }
        updateUi(activeConnection.getTuplesByLocationANDDate(text,value.toString()));
    }
    public Vacation[] getTwenty(){
        return activeConnection.getTwentyVactions();
    }
    private void updateUi(Vacation[] list){
        if(list==null){
            System.out.println("No results to show");
            return;
        }
        for(int i=0;i<list.length;i++){
            ui.addResultItem();
            lastItem.defineContent(list[i]);
        }
    }
}
