package View;

import Controller.DeleteInterface;
import Controller.LoginInterface;
import Model.LogoutEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

import static javafx.application.Platform.exit;

public class deleteUserController extends WindowController {
    private DeleteInterface dataBaseToDelete = new DeleteInterface();
    private LoginInterface dataBaseToLogIn = new LoginInterface();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text messageLabel;
    @FXML
    private CheckBox VBox;
    @FXML
    public void handleDelete(){
        boolean ok = dataBaseToLogIn.combinationApprove(username.getText(),password.getText());
        if(ok & VBox.isSelected()){
            dataBaseToDelete.deleteDetails(username.getText());
            exit();
        }
        else if(!VBox.isSelected())
            this.messageLabel.setText("not sure");
        else
            this.messageLabel.setText("Wrong Login/Password");

    }
}
