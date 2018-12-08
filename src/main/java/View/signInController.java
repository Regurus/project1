package View;

import Controller.LoginInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class signInController extends WindowController {
    private static LoginInterface dataBase = new LoginInterface();
    @FXML
    Button signInButton;
    @FXML
    Button signUpButton;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Text messageLabel;

    @FXML
    public void handleSignIn(){
        boolean ok = dataBase.combinationApprove(username.getText(),password.getText());
        if(!ok){
            this.messageLabel.setText("Wrong Login/Password");
            return;
        }
        openNewWindow("Vactoin4U", "/Ui.fxml",1200,680);
        this.username.getScene().getWindow().fireEvent(new WindowEvent(this.username.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));

    }
    @FXML
    public void handleSignUpFromSignIn()throws IOException {
        openNewWindow("SignUp", "/signUp.fxml",600,400);
    }




    public static void close(){
        dataBase.endSession();
    }
}
