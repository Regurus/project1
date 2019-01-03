package View;

import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class resultItemController implements Item{
    Vacation item;

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
    private void initialize(){

    }
    public void openItem(){
        uiController.item = this;
        uiController.Ui.openDesciption();
    }
    @Override
    public void defineContent(Vacation item){
        if(item == null)
            return;
        this.item = item;
        lbl_len.setText(item.getVacationLenght()+" Days");
        lbl_price.setText(item.getPrice()+'$');
        String destR = item.getDest_region();
        if(destR.length()>10)
            destR = destR.substring(0,destR.indexOf(' ',1))+"...";
        lbl_area_name.setText(destR);
        String destC = item.getDest_city();
        if(destC.length()>10)
            destC = destC.substring(0,destC.indexOf(' ',1))+"...";
        lbl_city_name.setText(destC);
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/images/userImages/"+item.getImage_path());
        this.preview.setImage(new Image(file.toURI().toString()));
    }

    @Override
    public String getType() {
        return "Result Item";
    }
}
