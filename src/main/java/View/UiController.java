package View;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.EditInterface;
import Controller.LoginInterface;
import Controller.SearchInterface;
import Model.Vacation;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;


public class UiController extends WindowController implements InitialiableWindow {

    private EditInterface data = new EditInterface();
    private String[] userValues;
    private int depressedBtn;
    private SearchInterface searchInterface;
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
    private Button home_btn;
    @FXML
    private Button add_btn;
    @FXML
    private Button history_btn;
    @FXML
    private Button favorites_btn;
    @FXML
    private TilePane home_scr_items;
    @FXML
    private AnchorPane home_scr;
    @FXML
    private TilePane history_scr_items;
    @FXML
    private AnchorPane history_scr;
    @FXML
    private TilePane favorites_scr_items;
    @FXML
    private AnchorPane favorites_scr;
    @FXML
    private GridPane add_scr;
    @FXML
    private Label add_msg;
    @FXML
    private ImageView add_image_preview;
    @FXML
    private DatePicker searchDate;
    @FXML
    private TextField searchBox;

    public void initialize(){
        home_btn.setStyle("-fx-background-color: #ffffff");
        this.depressedBtn = 0;
        this.searchInterface = new SearchInterface();
        //SearchInterface.ui = this;
        //TODO: delete on real testing
        Node[] nodes = new Node[10];
        for(int i=0;i<10;i++){
            try{
                nodes[i] = FXMLLoader.load(getClass().getResource("/resultItem.fxml"));
            }
            catch (Exception e){
                System.out.println("FXML Error");
            }
            home_scr_items.getChildren().add(nodes[i]);
        }
        home_scr.toFront();
        //scrollPane.setContent(this.test_container);
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
            home_scr.toFront();
            newButton=0;
        }
        if (actionEvent.getSource() == add_btn) {
            add_scr.toFront();
            newButton=1;
        }
        if (actionEvent.getSource() == history_btn) {
            history_scr.toFront();
            newButton=2;
        }
        if (actionEvent.getSource() == favorites_btn) {
            favorites_scr.toFront();
            newButton=3;
        }
        this.updateMenu(newButton);
    }
    @FXML
    public void openSettings(){
        user_edit_pane.toFront();
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
            active = add_btn;
        if(this.depressedBtn==2)
            active = history_btn;
        if(this.depressedBtn==3)
            active = favorites_btn;
        active.setStyle("-fx-background-color:  #4682B4");
        Button newActive = null;
        if(newActiveButton==0)
            newActive = home_btn;
        if(newActiveButton==1)
            newActive = add_btn;
        if(newActiveButton==2)
            newActive = history_btn;
        if(newActiveButton==3)
            newActive = favorites_btn;
        newActive.setStyle("-fx-background-color:  #FFFFFF");
        this.depressedBtn = newActiveButton;
    }
    @FXML
    private void openImage(){
        FileChooser fileChooser = new FileChooser();
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Image(*.jpg)"));
        File selectedImage = fileChooser.showOpenDialog(null);
        if(selectedImage !=null){
            this.add_image_preview.setImage(new Image(selectedImage.toURI().toString()));
        }
        else{
            add_msg.setText("Invalid Image File");
        }
    }
    //functional
    @FXML
    private void searchProcedure(){
        //this.searchInterface.search(this.searchBox.getText(),this.searchDate.getValue());
    }
    public void addResultItem(){
        Node newResult = null;
        try{
            newResult = FXMLLoader.load(getClass().getResource("/resultItem.fxml"));
            home_scr_items.getChildren().add(newResult);
        }
        catch (Exception e){
            System.out.println("FXML Error");
        }
    }

}
