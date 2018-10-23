package View;

import Controller.LoginInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

public class signInController extends WindowController {
    private static LoginInterface dataBase = new LoginInterface();
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Text messageLabel;
    @FXML
    public void handleSignIn(){
        boolean ok = dataBase.combinationApprove(username.getText(),password.getText());
        if(!ok)
            this.messageLabel.setText("Wrong Login/Password");
    }
    @FXML
    public void handleSignUpFromSignIn()throws IOException {
        File a = new File(" ");
        openNewWindow("SignUp", "/signUp.fxml",600,400);
    }
    public static void close(){
        dataBase.endSession();
    }

}
