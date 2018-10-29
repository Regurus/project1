package View;

import Controller.EditInterface;
import Controller.LoginInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

public class viewUserController implements InitialiableWindow{
    private EditInterface data = new EditInterface();
    private String userSearch = LoginInterface.getCurrentUser();
    private String[] userValues;
    @FXML
    TextField forSearch;
    @FXML
    TextField username;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField city;
    @FXML
    Text results;
    public viewUserController(){
        this.userValues=this.data.getUserInfo(userSearch);
    }
    public void setInitialValues(){
        username.setText(this.userValues[0]);
        username.editableProperty().setValue(false);
        fname.setText(this.userValues[2]);
        fname.editableProperty().setValue(false);
        lname.setText(this.userValues[3]);
        lname.editableProperty().setValue(false);
        city.setText(this.userValues[4]);
        city.editableProperty().setValue(false);
    }
    public void setUserSearch(){
            userSearch = forSearch.getText();
            this.userValues = this.data.getUserInfo(userSearch);
        if (this.userValues ==null)
                results.setText("not found");
        else {
            this.setInitialValues();
            results.setText("enjoy!");
            }
    }
}
