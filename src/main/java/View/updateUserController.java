package View;

import Controller.EditInterface;
import Controller.LoginInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

public class updateUserController implements InitialiableWindow{
    private EditInterface data = new EditInterface();
    private String[] userValues;
    @FXML
    TextField username;
    @FXML
    PasswordField opassword;
    @FXML
    PasswordField npassword;
    @FXML
    PasswordField cpassword;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField city;
    @FXML
    Text msg;
    public updateUserController(){
        this.userValues=this.data.getUserInfo(LoginInterface.getCurrentUser());
    }
    public void setInitialValues(){
        username.setText(this.userValues[0]);
        username.editableProperty().setValue(false);
        fname.setText(this.userValues[2]);
        lname.setText(this.userValues[3]);
        city.setText(this.userValues[4]);
    }
    public void handleUpdate() {
        boolean dataChanged = false;
        boolean passwordChanged = false;
        if(!fname.getText().equals(this.userValues[2])||!lname.getText().equals(this.userValues[3])||!city.getText().equals(this.userValues[4]))
            dataChanged=true;
        if(!opassword.getText().equals("")||!npassword.getText().equals("")||!cpassword.getText().equals(""))
            passwordChanged=true;
        if(!dataChanged&&!passwordChanged)
            return;
        if(passwordChanged){
            String message = "";
            if(!data.combinationApprove(LoginInterface.getCurrentUser(),opassword.getText()))
                message = message+"Old password is incorrect!\n";
            if(!npassword.getText().equals(cpassword.getText()))
                message = message+"New password doesn`t match!\n";
            if(npassword.getText().equals(""))
                message = message+"Please enter new password.";
            this.msg.setText(message);
            if(!message.equals(""))
                return;
            data.updatePassword(npassword.getText());
        }
        if(dataChanged){
            if(!fname.getText().equals(this.userValues[2]))
                data.updateDetails(fname.getText(),"fname");
            if(!lname.getText().equals(this.userValues[3]))
                data.updateDetails(lname.getText(),"lname");
            if(!city.getText().equals(this.userValues[4]))
                data.updateDetails(city.getText(),"city");
        }
        this.lname.getScene().getWindow().fireEvent(new WindowEvent(this.lname.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
