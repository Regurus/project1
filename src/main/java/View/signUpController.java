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
        msg.setText("");
        if(!data.newLoginApprove(username.getText())){
            msg.setText("Login used or incorrect!");
            return;
        }
        if(!password.getText().equals(cpassword.getText())){
            msg.setText("Passwords doesn`t match!");
            return;
        }
        if(this.bday.getValue()==null){
            msg.setText("Birth date field is empty");
            return;
        }
        LocalDate bdayValue= this.bday.getValue().plusYears(18);
        if(!bdayValue.isBefore(LocalDate.now())){
            msg.setText("You are not over 18");
            return;
        }
        String[] data = new String[5];
        data[0] = username.getText();
        data[1] = password.getText();
        data[2] = fname.getText();
        data[3] = lname.getText();
        data[4] = city.getText();
        if(!this.data.detailsApprove(data)){
            msg.setText("Details not filled!");
            return;
        }
        this.data.wiriteToDB(data);
        this.username.getScene().getWindow().fireEvent(new WindowEvent(this.username.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    public void redirectToSignIn(){
        this.data.endSession();
        this.username.getScene().getWindow().fireEvent(new WindowEvent(this.username.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
