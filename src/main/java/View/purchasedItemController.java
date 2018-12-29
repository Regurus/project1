package View;

import Model.PurchasedVacation;
import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class purchasedItemController implements Item{

    @FXML
    private Label dealDate_lbl;
    @FXML
    private Label details_lbl;
    @FXML
    private Label id_lbl;

    public void initialize(){
    }
    @Override
    public void defineContent(Vacation vac){
        PurchasedVacation item = (PurchasedVacation)vac;
        dealDate_lbl.setText("Purchased on: "+item.getPurchase_date().replace('-','/'));
        details_lbl.setText("Travel to: "+vac.getDest_region()+"->"+vac.getDest_city()+" on "+vac.getStart().replace('-','/'));
        id_lbl.setText("Travel ID: "+vac.getListing_id());
    }
    @Override
    public String getType() {
        return "Purchased Item";
    }
}
