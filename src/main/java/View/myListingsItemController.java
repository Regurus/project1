package View;

import Controller.MyListingsInterface;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class myListingsItemController {
    @FXML
    private Label trip_dest_lbl;
    @FXML
    private Label trip_date_lbl;
    @FXML
    private Label status_lbl;
    @FXML
    private Button approve_btn;

    @FXML
    public void initialize(){
        MyListingsInterface.current = this;
    }



    public void setProperties(Vacation vc, boolean status){
        this.trip_dest_lbl.setText("Trip to: "+vc.getDest_region()+" -> "+vc.getDest_region());
        this.trip_date_lbl.setText("In Dates: "+vc.getStart().replace('-','/')+" - "+vc.getEnd().replace('-','/'));
        if(status){
            status_lbl.setText("Status: Listed.");
            this.approve_btn.setDisable(true);
        }
        else{
            status_lbl.setText("Status: Pending for approval.");
            this.approve_btn.setDisable(false);
        }
    }
}
