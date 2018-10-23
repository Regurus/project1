package View;

import Controller.RegisterInterface;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.time.LocalDate;

public class signUpController {
    private RegisterInterface data = new RegisterInterface();
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    PasswordField cpassword;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField city;
    @FXML
    DatePicker bday;
    @FXML
    Text msg;
    @FXML
    public void handleSignUp(){
        if(!data.newLoginApprove(username.getText()))
            msg.setText("Login used or incorrect!");
        if(password.getText().contentEquals(cpassword.getText()))
            msg.setText("Passwords doesn`t match!");
        LocalDate bdayValue= this.bday.getValue().plusYears(18);
        if(!bdayValue.isBefore(LocalDate.now())){
            msg.setText("You are not over 18");
            try {
                wait(5);
            } catch (InterruptedException e) {
            }
            this.username.getScene().getWindow().fireEvent(new WindowEvent(this.username.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));

        }
        String[] data = new String[4];
        data[0] = password.getText();
        data[1] = fname.getText();
        data[2] = lname.getText();
        data[3] = city.getText();


    }
    @FXML
    public void redirectToSignIn(){
        this.data.endSession();
        this.username.getScene().getWindow().fireEvent(new WindowEvent(this.username.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
