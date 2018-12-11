package View;

import Model.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class purchasedItemController {

    @FXML
    private Label dealDate_lbl;
    @FXML
    private Label details_lbl;
    @FXML
    private Label id_lbl;

    public void defineContent(Vacation vac, String dealDate){
        dealDate_lbl.setText("Purchased on: "+dealDate.replace('-','/'));
        details_lbl.setText("Travel to: "+vac.getDest_region()+"->"+vac.getDest_city()+" on "+vac.getStart().replace('-','/'));
        id_lbl.setText("Travel ID: "+vac.getListing_id());
    }

}
