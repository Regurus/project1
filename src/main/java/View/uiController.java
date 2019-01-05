package View;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import Controller.*;
import Model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;


public class uiController extends windowController implements InitialiableWindow {

    public static uiController Ui;
    private String[] userValues;
    private int depressedBtn = 0;
    boolean purchase_decision;
    private String imageID;
    protected resultItemController item;
    private HashMap<resultItemController,Node> resultsList = new HashMap<>();
    private HashMap<purchasedItemController,Node> purchasedList = new HashMap<>();
    public HashMap<myListingsItemController,Node> myListingList = new HashMap<>();
    private HashMap<MessagingItemController,Node> myMessagesList = new HashMap<>();
    private HashMap<String,Node> myMessagesSessionList = new HashMap<>();
    //<editor-fold desc="Settings Controls">
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
    private Label username_lbl;
    //</editor-fold>

    //<editor-fold desc="Details">
    @FXML
    private Label details_dest_lbl;
    @FXML
    private Label details_start_lbl;
    @FXML
    private Label details_end_lbl;
    @FXML
    private Label details_price_lbl;
    @FXML
    private TextArea details_desc_area;
    @FXML
    private ImageView details_img;
    //</editor-fold>

    //<editor-fold desc="Menu Icons">
    @FXML
    private FontAwesomeIconView homeIcon;
    @FXML
    private FontAwesomeIconView addIcon;
    @FXML
    private FontAwesomeIconView publishedIcon;
    @FXML
    private FontAwesomeIconView favoritesIcon;
    //</editor-fold>

    //<editor-fold desc="Menu">
    @FXML
    private Button home_btn;
    @FXML
    private Button add_btn;
    @FXML
    private Button published_btn;
    @FXML
    private Button favorites_btn;
    //</editor-fold>

    //<editor-fold desc="Screens">
    @FXML
    private TilePane home_scr_items;
    @FXML
    private AnchorPane home_scr;
    @FXML
    private AnchorPane details_scr;
    @FXML
    private GridPane add_scr;
    @FXML
    private AnchorPane favorites_scr;
    //</editor-fold>

    //<editor-fold desc="Published">
    @FXML
    private TilePane published_scr_items;
    @FXML
    private AnchorPane published_scr;
    //</editor-fold>

    //<editor-fold desc="Favorites">
    @FXML
    private TilePane favorites_scr_items;

    //</editor-fold>

    //<editor-fold desc="Add Vacation Screen">
    @FXML
    private TextField add_text_region;
    @FXML
    private TextField add_text_city;
    @FXML
    private TextField add_text_price;
    @FXML
    private DatePicker add_date_start;
    @FXML
    private DatePicker add_date_end;
    @FXML
    private TextArea add_text_description;
    @FXML
    private ImageView add_image_preview;

    //</editor-fold>

    //<editor-fold desc="Search">
    @FXML
    private DatePicker searchDate;
    @FXML
    private TextField searchBox;
    //</editor-fold>

    //<editor-fold desc="Messages">
    @FXML
    private TilePane msg_controls;
    @FXML
    private Label add_msg;
    @FXML
    private AnchorPane msg_scr;
    @FXML
    private AnchorPane msg_content;
    //</editor-fold>

    public void initialize(){
        uiController.Ui = this;
        this.initializeInterfaces();
        this.userValues=EditInterface.EI.getUserInfo(LoginInterface.getCurrentUser());
        this.username_lbl.setText(this.userValues[0]);
        initializePublished();
        initializeHomeScreen();
        initializePurchases();
        initializeMessages();
        //scrollPane.setContent(this.test_container);
    }
    private void initializeInterfaces(){

        PurchaseApplicationInterface.PAI = new PurchaseApplicationInterface();
        MyListingsInterface.MLI = new MyListingsInterface();
        PurchasesInterface.PI = new PurchasesInterface();
        SearchInterface.SI = new SearchInterface();
        AddVacationInterface.AVI = new AddVacationInterface();
        EditInterface.EI = new EditInterface();
        MessagingInterface.MI = new MessagingInterface();

    }
    private void initializeHomeScreen(){
        Vacation[] existing = SearchInterface.SI.getTwenty();
        if(existing!=null&&existing.length>0){
            for(int i=0;i<existing.length;i++){
                this.addItems("/resultItem.fxml", home_scr_items);
            }
        }
        assert existing != null;
        resultItemController[] currentContent = this.resultsList.keySet().toArray(new resultItemController[existing.length]);
        for(int i=0;i<existing.length;i++) {
            currentContent[i].defineContent(existing[i]);
        }
        home_scr.toFront();
    }
    private void initializePublished(){
        MyListingsInterface.MLI.getPublishedItems();
    }
    public void initializeUserData(){
        username.setText(this.userValues[0]);
        username.editableProperty().setValue(false);
        fname.setText(this.userValues[2]);
        lname.setText(this.userValues[3]);
        city.setText(this.userValues[4]);
    }
    private void initializeMessages(){
        MessagingSession[] user_msg = MessagingInterface.MI.getAllUsersConversations(LoginInterface.getCurrentUser());

        for(int i=0;i<user_msg.length;i++){
            //loadindg left hand section (message session selection menu)
            String msg_with = " ";
            if(user_msg[i].getUser1().equalsIgnoreCase(LoginInterface.getCurrentUser()))
                msg_with = user_msg[i].getUser2();
            else
                msg_with = user_msg[i].getUser1();
            Button msg_control = new Button(msg_with);
            msg_control.setStyle("-fx-font: 16 Century Gothic; -fx-background-color: #FFFFFF;-fx-text-fill: #4682B4;-fx-pref-height: 40px;-fx-pref-width: 200px;");
            msg_control.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    event.getSource();
                }
            });
            //end procedure
            //loading items for right hand section (actual messaging sessions)
            this.msg_controls.getChildren().add(msg_control);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/messageSessionItem.fxml"));
            Node res = null;
            try{
                res = fxmlLoader.load();
            }
            catch (Exception e){
                System.out.println("FXML Loading error! @/messageSessionItem.fxml");
            }
            MessagingItemController controller = (MessagingItemController)fxmlLoader.getController();
            controller.defineContent(user_msg[i]);
            this.myMessagesList.put(controller,res);
            this.myMessagesSessionList.put(msg_with,res);
            //end procedure
        }

    }
    @FXML
    private void initializePurchases(){
        PurchasesInterface.PI.getPublishedItems();
    }
    @FXML
    private void handleUpdate() {
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
            if(!EditInterface.EI.combinationApprove(LoginInterface.getCurrentUser(),opassword.getText()))
                message = message+"Old password is incorrect!\n";
            if(!npassword.getText().equals(cpassword.getText()))
                message = message+"New password doesn`t match!\n";
            if(npassword.getText().equals(""))
                message = message+"Please enter new password.";
            this.msg.setText(message);
            if(!message.equals(""))
                return;
            EditInterface.EI.updatePassword(npassword.getText());
            if(!dataChanged)
                this.home_btn.fire();
        }
        if(dataChanged){
            if(!fname.getText().equals(this.userValues[2]))
                EditInterface.EI.updateDetails(fname.getText(),"fname");
            if(!lname.getText().equals(this.userValues[3]))
                EditInterface.EI.updateDetails(lname.getText(),"lname");
            if(!city.getText().equals(this.userValues[4]))
                EditInterface.EI.updateDetails(city.getText(),"city");
            this.home_btn.fire();
        }
    }
    @FXML
    private void handleMenuClick(ActionEvent actionEvent){
        int newButton=0;
        if (actionEvent.getSource() == home_btn) {
            home_scr.toFront();
            newButton=0;
        }
        if (actionEvent.getSource() == add_btn) {
            add_scr.toFront();
            newButton=1;
        }
        if (actionEvent.getSource() == published_btn) {
            published_scr.toFront();
            newButton=2;
        }
        if (actionEvent.getSource() == favorites_btn) {
            favorites_scr.toFront();
            newButton=3;
        }
        this.updateMenu(newButton);
    }
    @FXML
    private void handleSettingsClick(){
        user_edit_pane.toFront();
    }
    private void handleMessagesClick(Button pressed){

    }
    @FXML
    private void handleLogoutClick(){
        LoginInterface.nullifyCurrentUser();
        this.openNewWindowAndCloseOld("Vaction4U","/signIn.fxml",600, 400);
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    private void handleClose(){
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    @FXML
    private void handleOpenImage(){
        FileChooser fileChooser = new FileChooser();
        File selectedImage = fileChooser.showOpenDialog(null);
        if(selectedImage !=null){
            this.add_image_preview.setImage(new Image(selectedImage.toURI().toString()));
            try{
                ImageSaver.changeSizeImage(selectedImage,160,250);
                this.imageID = ImageSaver.saveImage(selectedImage);
        }
            catch (Exception e){
                System.out.println("Picture resize fault");
            }
        }
        else{
            add_msg.setText("Invalid Image File");
        }
    }
    @FXML
    private void handleSearch(){
        this.home_scr_items.getChildren().clear();
        SearchInterface.SI.search(this.searchBox.getText(),this.searchDate.getValue());
    }
    @FXML
    private void handlePublishNewVacation(ActionEvent actionEvent) {
        String uniqueID = UUID.randomUUID().toString();
        Vacation vacation = new Vacation(add_text_region.getText(),add_text_city.getText(),add_text_price.getText(),add_date_start.getValue().toString(),add_date_end.getValue().toString(),add_text_description.getText(),this.imageID,uniqueID,LoginInterface.getCurrentUser(),"admin");
        if(AddVacationInterface.AVI.detailsApprove(vacation.toStringArray())){
            AddVacationInterface.AVI.wiriteToDB(vacation.toStringArray());
            this.home_btn.fire();
        }
        else
            this.add_msg.setText("All fields are required!");
    }
    @FXML
    private void handleApplication(){
        this.openNewWindow("Payment", "/paymentDialog.fxml",700,550);
        if(this.purchase_decision){
            this.home_scr_items.getChildren().remove(this.resultsList.get(this.item));
            this.purchase_decision =false;
            this.home_btn.fire();
        }
    }
    @FXML
    private void handleOpenMSG(){
        this.msg_scr.toFront();
    }
    private void openMessageSession(String with){
        this.msg_content = (AnchorPane)this.myMessagesSessionList.get(with);
    }
    private void updateMenu(int newActiveButton){
        Button active = null;
        if(this.depressedBtn==0){
            active = home_btn;
            this.homeIcon.setFill(Paint.valueOf("#FFFFFF"));
        }
        if(this.depressedBtn==1){
            active = add_btn;
            this.addIcon.setFill(Paint.valueOf("#FFFFFF"));
        }
        if(this.depressedBtn==2){
            active = published_btn;
            this.publishedIcon.setFill(Paint.valueOf("#FFFFFF"));
        }
        if(this.depressedBtn==3){
            active = favorites_btn;
            this.favoritesIcon.setFill(Paint.valueOf("#FFFFFF"));
        }
        assert active != null;
        active.setStyle("-fx-background-color:  #4682B4");
        Button newActive = null;
        if(newActiveButton==0){
            newActive = home_btn;
            this.homeIcon.setFill(Paint.valueOf("#4682B4"));
        }
        if(newActiveButton==1){
            newActive = add_btn;
            this.addIcon.setFill(Paint.valueOf("#4682B4"));
        }
        if(newActiveButton==2){
            newActive = published_btn;
            this.publishedIcon.setFill(Paint.valueOf("#4682B4"));
        }
        if(newActiveButton==3){
            newActive = favorites_btn;
            this.favoritesIcon.setFill(Paint.valueOf("#4682B4"));
        }
        assert newActive != null;
        newActive.setStyle("-fx-background-color:  #FFFFFF");
        this.depressedBtn = newActiveButton;
    }
    public void addResultItem(Vacation item){
        Item controller = this.addItems("/resultItem.fxml",home_scr_items);
        assert controller != null;
        controller.defineContent(item);
    }
    public void addPublishedItem(Vacation item){
        Item controller = this.addItems("/myListingsItem.fxml",published_scr_items);
        assert controller != null;
        controller.defineContent(item);
    }
    public void addPurchasedItem(PurchasedVacation item){
        Item controller = this.addItems("/purchasedItem.fxml",favorites_scr_items);
        assert controller != null;
        controller.defineContent(item);
    }
    private Item addItems(String name,TilePane addTo){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            Node res = fxmlLoader.load();
            addTo.getChildren().add(res);
            Object controller = fxmlLoader.getController();
            this.placeController((Item)controller,res);
            return (Item)controller;
        }
        catch (Exception e){
            System.out.println("FXML Error @"+name);
        }
        return null;
    }
    private void placeController(Item controller,Node node){
        switch (controller.getType()) {
            case "Result Item":
                this.resultsList.put((resultItemController) controller, node);
                break;
            case "Purchased Item":
                this.purchasedList.put((purchasedItemController) controller, node);
                break;
            case "Listing Item":
                this.myListingList.put((myListingsItemController) controller, node);
                break;
        }
    }
    void removeItem(Item controller){
        switch (controller.getType()) {
            case "Result Item":
                this.home_scr_items.getChildren().remove(this.resultsList.get(controller));
                break;
            case "Purchased Item":
                this.favorites_scr_items.getChildren().remove(this.purchasedList.get(controller));
                break;
            case "Listing Item":
                this.published_scr_items.getChildren().remove(this.myListingList.get(controller));
                break;
        }
    }
    void openDesciption(resultItemController item){
        this.item = item;
        details_dest_lbl.setText("Destination: "+ item.item.getDest_region()+","+ item.item.getDest_city());
        details_start_lbl.setText("Departure: "+ item.item.getStart().replace("-","/"));
        details_end_lbl.setText("Return: "+ item.item.getEnd().replace('-','/'));
        details_price_lbl.setText("Price: "+ item.item.getPrice()+"$");
        details_desc_area.setText(item.item.getDescription());
        details_desc_area.setWrapText(true);
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/images/userImages/"+ item.item.getImage_path());
        details_img.setImage(new Image(file.toURI().toString()));
        this.details_scr.toFront();
    }

}
