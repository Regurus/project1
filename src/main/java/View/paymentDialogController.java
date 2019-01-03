package View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

public class paymentDialogController {

    @FXML
    private Label price_lbl;

    public void initialize(){
        this.price_lbl.setText(uiController.item.item.getPrice()+"$");
    }

    public void close(){
        this.price_lbl.getScene().getWindow().fireEvent(new WindowEvent(this.price_lbl.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void payAndClose(){
        uiController.Ui.purchase_decision =true;
        this.price_lbl.getScene().getWindow().fireEvent(new WindowEvent(this.price_lbl.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
