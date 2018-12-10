package View;

import Controller.SearchInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ResultItemController {

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
    @FXML
    public void initialize(){
        SearchInterface.lastItem = this;
    }
    public void openItem(){

    }
    public void defineContent(Image preview,String dest_city, String dest_reg, String len, String price){
        lbl_len.setText(len+" Days");
        lbl_price.setText(price+'$');
        lbl_area_name.setText(dest_reg);
        lbl_city_name.setText(dest_city);
        //this.preview.setImage(preview);
    }
}
