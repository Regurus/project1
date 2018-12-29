package View;

import Controller.MyListingsInterface;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class myListingsItemController implements Item{

    private Vacation containedItem;

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
        if(uiController.purAddInterface.hasApplicant(containedItem.getListing_id())){
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

    @Override
    public String getType() {
        return "Listing Item";
    }

    @FXML
    private void handleDelete(){
        if(containedItem!=null)
            uiController.addVacInterface.deleteFromDB(containedItem.getListing_id());
        uiController.Ui.removeItem(this);
    }
    @FXML
    private void handleAccept(){
        if(containedItem!=null){
            uiController.addVacInterface.deleteFromDB(containedItem.getListing_id());
            uiController.PI.addPVacation(containedItem);
            containedItem=null;
            uiController.Ui.removeItem(this);
        }
    }
    @FXML
    private void handleDecline(){
        uiController.purAddInterface.declinePurchaseApplication(containedItem.getListing_id());
        status_lbl.setText("Status: Listed.");
        this.approve_btn.setDisable(true);
        this.decline_btn.setDisable(true);
    }
}
