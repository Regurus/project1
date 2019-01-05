package View;

import Controller.*;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class myListingsItemController implements Item{

    Vacation containedItem;

    @FXML
    private Label trip_dest_lbl;
    @FXML
    private Label trip_date_lbl;
    @FXML
    private Label status_lbl;
    @FXML
    private Label trade_status_lbl;
    @FXML
    private Button approve_btn;
    @FXML
    private Button decline_btn;
    @FXML
    private Button accept_trade_btn;
    @FXML
    private void initialize(){
    }
    public void defineContent(Vacation vc){
        this.trip_dest_lbl.setText("Trip to: "+vc.getDest_region()+" -> "+vc.getDest_city());
        this.trip_date_lbl.setText("In Dates: "+vc.getStart().replace('-','/')+" - "+vc.getEnd().replace('-','/'));
        this.trade_status_lbl.setText("Trade: No trades requested");
        this.accept_trade_btn.setDisable(true);
        this.containedItem = vc;
        if(PurchaseApplicationInterface.PAI.hasApplicant(containedItem.getListing_id())){
            if(vc.getApplicant().contains(paymentDialogController.delimiter)){                //its trade
                status_lbl.setText("Status: Pending for trade approval.");
                this.trade_status_lbl.setText("Trade: Trade requested for "+vc.getApplicant().split(paymentDialogController.delimiter)[1]);
                this.accept_trade_btn.setDisable(false);
                this.approve_btn.setDisable(true);
            }
            else{
                status_lbl.setText("Status: Pending for transaction approval.");
                this.approve_btn.setDisable(false);

            }
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
    public void handleTrade(){
        //get vacation object for the traded item
        Vacation[] arr = SearchInterface.SI.search(this.containedItem.getApplicant().split(paymentDialogController.delimiter)[1]);
        this.containedItem.setApplicant(this.containedItem.getApplicant().split(paymentDialogController.delimiter)[0]);
        if(arr.length==0||arr.length>1)
            System.out.println("Traded vacation searching error!");
        //set applicant as owner of contained item
        arr[0].setApplicant(LoginInterface.getCurrentUser());
        //perform double sell
        AddVacationInterface.AVI.deleteFromDB(arr[0].getListing_id());
        PurchasesInterface.PI.addPVacation(arr[0]);
        this.handleAccept();
    }
    @FXML
    private void handleDelete(){
        if(containedItem!=null)
            AddVacationInterface.AVI.deleteFromDB(containedItem.getListing_id());
        uiController.Ui.removeItem(this);
    }
    @FXML
    private void handleAccept(){
        if(containedItem!=null){
            AddVacationInterface.AVI.deleteFromDB(containedItem.getListing_id());
            PurchasesInterface.PI.addPVacation(containedItem);
            containedItem=null;
            uiController.Ui.removeItem(this);
        }
    }
    @FXML
    private void handleDecline(){
        PurchaseApplicationInterface.PAI.declinePurchaseApplication(containedItem.getListing_id());
        status_lbl.setText("Status: Listed.");
        this.approve_btn.setDisable(true);
        this.decline_btn.setDisable(true);
    }
}
