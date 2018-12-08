package View;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.EditInterface;
import Controller.LoginInterface;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;


public class UiController extends WindowController implements InitialiableWindow {

    private EditInterface data = new EditInterface();
    private String[] userValues;
    private int depressedBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField opassword;
    @FXML
    private PasswordField npassword;
    @FXML
    private PasswordField cpassword;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField city;
    @FXML
    private Text msg;
    @FXML
    private AnchorPane user_edit_pane;
    @FXML
    private SplitPane search_pane;
    @FXML
    private SplitPane history_pane;
    @FXML
    private SplitPane favorites_pane;
    @FXML
    private Button home_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private Button history_btn;
    @FXML
    private Button favorites_btn;
    @FXML
    private TilePane favorites_container;
    @FXML
    private ScrollPane scrollPane;
    public void initialize(){
        home_btn.setStyle("-fx-background-color: #ffffff");
        this.depressedBtn = 0;
        Node[] nodes = new Node[10];
        for(int i=0;i<10;i++){
            try{
                nodes[i] = FXMLLoader.load(getClass().getResource("/resultItem.fxml"));
            }
            catch (Exception e){
                System.out.println("FXML Error");
            }
            favorites_container.getChildren().add(nodes[i]);
        }
        scrollPane.setContent(this.favorites_container);
    }
    public UiController(){
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
    }
    public void handle_menu_click(ActionEvent actionEvent){
        int newButton=0;
        if (actionEvent.getSource() == home_btn) {
            search_pane.toFront();
        }
        if (actionEvent.getSource() == settings_btn) {
            user_edit_pane.toFront();
            newButton=1;
        }
        if (actionEvent.getSource() == history_btn) {
            history_pane.toFront();
            newButton=2;
        }
        if (actionEvent.getSource() == favorites_btn) {
            favorites_pane.toFront();
            newButton=3;
        }
        this.updateMenu(newButton);
    }
    public void appLogOut(){
        LoginInterface.nullifyCurrentUser();
        this.openNewWindow("Vaction4U","/signIn.fxml",600, 400);
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void close(){
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    private void updateMenu(int newActiveButton){
        Button active = null;
        if(this.depressedBtn==0)
            active = home_btn;
        if(this.depressedBtn==1)
            active = settings_btn;
        if(this.depressedBtn==2)
            active = history_btn;
        if(this.depressedBtn==3)
            active = favorites_btn;
        active.setStyle("-fx-background-color:  #4682B4");
        Button newActive = null;
        if(newActiveButton==0)
            newActive = home_btn;
        if(newActiveButton==1)
            newActive = settings_btn;
        if(newActiveButton==2)
            newActive = history_btn;
        if(newActiveButton==3)
            newActive = favorites_btn;
        newActive.setStyle("-fx-background-color:  #FFFFFF");
        this.depressedBtn = newActiveButton;
    }


}
