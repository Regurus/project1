package View;

import Controller.SearchInterface;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


public class ResultItemController {
    public static UiController UI;
    @FXML
    private Label lbl_len;
    @FXML
    private Label lbl_price;
    @FXML
    private Label lbl_area_name;
    @FXML
    private Label lbl_city_name;
    @FXML
    private ImageView preview;
    public Vacation item;
    @FXML
    public void initialize(){
        SearchInterface.lastItem = this;
    }
    public void openItem(){
        UiController.item = this;
        UI.openDesciption();
    }
    public void defineContent(Vacation item){
        this.item = item;
        lbl_len.setText(item.getVacationLenght()+" Days");
        lbl_price.setText(item.getPrice()+'$');
        lbl_area_name.setText(item.getDest_region());
        lbl_city_name.setText(item.getDest_city());
        File file = new File("/resources/images/userImages/"+item.getImage_path());
        this.preview.setImage(new Image(file.toURI().toString()));
    }
}
