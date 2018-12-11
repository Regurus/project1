package View;

import Controller.MyListingsInterface;
import Controller.SearchInterface;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class myListingsItemController {

    private Vacation containedItem;
    private UiController UI;

    @FXML
    private Label trip_dest_lbl;
    @FXML
    private Label trip_date_lbl;
    @FXML
    private Label status_lbl;
    @FXML
    private Button approve_btn;
    @FXML
    private Button decline_btn;
    @FXML
    private void initialize(){
        MyListingsInterface.current = this;
    }
    public void defineContent(Vacation vc){
        this.trip_dest_lbl.setText("Trip to: "+vc.getDest_region()+" -> "+vc.getDest_city());
        this.trip_date_lbl.setText("In Dates: "+vc.getStart().replace('-','/')+" - "+vc.getEnd().replace('-','/'));
        this.containedItem = vc;
        this.UI = SearchInterface.ui;
        if(UiController.purAddInterface.hasApplicant(containedItem.getListing_id())){
            status_lbl.setText("Status: Pending for approval.");
            this.approve_btn.setDisable(false);
            this.decline_btn.setDisable(false);
        }
        else{
            status_lbl.setText("Status: Listed.");
            this.approve_btn.setDisable(true);
            this.decline_btn.setDisable(true);
        }
    }
    @FXML
    private void handleDelete(){
        System.out.println("Delete: Not implemented on this stage!");
    }
    @FXML
    private void handleAccept(){
        System.out.println("Accept: Not implemented yet!");
    }
    @FXML
    private void handleDecline(){
        UiController.purAddInterface.declinePurchaseApplication(containedItem.getListing_id());
    }
}
