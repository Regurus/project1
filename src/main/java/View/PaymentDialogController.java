package View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

public class PaymentDialogController {

    @FXML
    private Label price_lbl;

    public void initialize(){
        this.price_lbl.setText(UiController.item.item.getPrice()+"$");
    }

    public void close(){
        this.price_lbl.getScene().getWindow().fireEvent(new WindowEvent(this.price_lbl.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void payAndClose(){
        UiController.purchase_desition=true;
        this.price_lbl.getScene().getWindow().fireEvent(new WindowEvent(this.price_lbl.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
