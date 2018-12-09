package Controller;

import Model.Vacation;
import Model.VacationDatabase;
import View.ResultItemController;
import View.UiController;
import javafx.scene.image.Image;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SearchInterface {
    public static ResultItemController lastItem;
    public static UiController ui;
    public VacationDatabase vacDB = new VacationDatabase();

    public void search(String text, LocalDate value) {
        updateUi(vacDB.getRelevantTuples(text,value.toString()));
    }

    private void updateUi(Vacation[] list){
        for(int i=0;i<list.length;i++){
            ui.addResultItem();
            lastItem.defineContent(null,list[i].getDest_city(),list[i].getDest_region(),""+list[i].getVacationLenght(),list[i].getPrice());
        }
    }
}
