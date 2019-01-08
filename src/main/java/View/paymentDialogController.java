package View;

import Controller.LoginInterface;
import Controller.MessagingInterface;
import Controller.PurchaseApplicationInterface;
import Model.PurchaseApplication;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
//special mark for proposed trade is applicant+delimiter(from this class)+vacationID
public class paymentDialogController {

    private boolean offer = false;
    private boolean trade = false;
    public static final String delimiter = "&1&";
    @FXML
    private TextArea msg;
    @FXML
    private ListView my_vc;
    @FXML
    private ArrayList<Vacation> myItems = new ArrayList<>();

    public void initialize(){
        Object[] arr = uiController.Ui.myListingList.keySet().toArray();
        for(int i=0;i<arr.length;i++){
            my_vc.getItems().add("1."+((myListingsItemController)arr[i]).containedItem.getDest_city()+"->"+((myListingsItemController)arr[i]).containedItem.getDest_region()+" on "+((myListingsItemController)arr[i]).containedItem.getStart());
            this.myItems.add(((myListingsItemController)arr[i]).containedItem);
        }
    }
    public void select_offer(){
        this.offer=true;
        this.trade=false;
        this.my_vc.setDisable(true);
    }
    public void select_trade(){
        this.offer=false;
        this.trade=true;
        this.my_vc.setDisable(false);
    }
    public void close(){
        this.my_vc.getScene().getWindow().fireEvent(new WindowEvent(this.my_vc.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void submit(){
        if(!trade&&!offer)
            this.close();
        uiController.Ui.purchase_decision =true;
        if(offer){
            String vacation_id = uiController.Ui.item.item.getListing_id();//vacation_id
            String applicant = LoginInterface.getCurrentUser();//applicant
            PurchaseApplication purchaseApplication = new PurchaseApplication(vacation_id,applicant);
            PurchaseApplicationInterface.PAI.acceptApplication(purchaseApplication);
            if(MessagingInterface.MI.sessionExists(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner()))
                MessagingInterface.MI.sendMessage(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),"System"+MessagingItemController.delimiter+"Hello there is someone interested in your listing: "+uiController.Ui.item.item.getListing_id());
            else
                MessagingInterface.MI.createNewConversation(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),"System"+MessagingItemController.delimiter+"Hello there is someone interested in your listing: "+uiController.Ui.item.item.getListing_id());
            MessagingInterface.MI.sendMessage(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),this.msg.getText());
        }
        if(trade){
            String vacation_id = uiController.Ui.item.item.getListing_id();//vacation_id
            String applicant = LoginInterface.getCurrentUser();//applicant
            PurchaseApplication purchaseApplication = new PurchaseApplication(vacation_id,applicant+paymentDialogController.delimiter+this.myItems.get(this.my_vc.getSelectionModel().getSelectedIndex()).getListing_id());
            PurchaseApplicationInterface.PAI.acceptApplication(purchaseApplication);
            if(MessagingInterface.MI.sessionExists(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner()))
                MessagingInterface.MI.sendMessage(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),"System"+MessagingItemController.delimiter+"Hello there is someone interested in your listing("+uiController.Ui.item.item.getListing_id()+"), and want to trade it for vacation #"+this.myItems.get(this.my_vc.getSelectionModel().getSelectedIndex()).getListing_id()+": "+this.msg.getText());
            else
                MessagingInterface.MI.createNewConversation(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),"System"+MessagingItemController.delimiter+"Hello there is someone interested in your listing("+uiController.Ui.item.item.getListing_id()+"), and want to trade it for vacation #"+this.myItems.get(this.my_vc.getSelectionModel().getSelectedIndex()).getListing_id()+": "+this.msg.getText());
            MessagingInterface.MI.sendMessage(LoginInterface.getCurrentUser(),uiController.Ui.item.item.getOwner(),this.msg.getText());
        }
        this.my_vc.getScene().getWindow().fireEvent(new WindowEvent(this.my_vc.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
