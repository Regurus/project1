package View;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import Controller.*;
import Model.ImageSaver;
import Model.PurchaseApplication;
import Model.PurchasedVacation;
import Model.Vacation;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private EditInterface data = new EditInterface();
    static AddVacationInterface addVacInterface = new AddVacationInterface();
    static PurchaseApplicationInterface purAddInterface = new PurchaseApplicationInterface();
    private MyListingsInterface MLI = new MyListingsInterface();
    static PurchasesInterface PI = new PurchasesInterface();
    private String[] userValues;
    private int depressedBtn = 0;
    private SearchInterface searchInterface = new SearchInterface();
    static resultItemController item;
    boolean purchase_decision;
    private String imageID;
    private HashMap<resultItemController,Node> resultsList = new HashMap<>();
    private HashMap<purchasedItemController,Node> purchasedList = new HashMap<>();
    private HashMap<myListingsItemController,Node> myListingList = new HashMap<>();

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
    @FXML
    private Label add_msg;
    //</editor-fold>

    //<editor-fold desc="Search">
    @FXML
    private DatePicker searchDate;
    @FXML
    private TextField searchBox;
    //</editor-fold>

    public void initialize(){
        uiController.Ui = this;
        this.userValues=this.data.getUserInfo(LoginInterface.getCurrentUser());
        SearchInterface.ui = this;
        this.username_lbl.setText(this.userValues[0]);
        initializePublished();
        initializeHomeScreen();
        initializePurchases();
        //scrollPane.setContent(this.test_container);
    }
    private void initializeHomeScreen(){
        Vacation[] existing = this.searchInterface.getTwenty();
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
        this.MLI.getPublishedItems();
    }
    public void initializeUserData(){
        username.setText(this.userValues[0]);
        username.editableProperty().setValue(false);
        fname.setText(this.userValues[2]);
        lname.setText(this.userValues[3]);
        city.setText(this.userValues[4]);
    }
    private void initializePurchases(){
        PI.getPublishedItems();
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
            if(!dataChanged)
                this.home_btn.fire();
        }
        if(dataChanged){
            if(!fname.getText().equals(this.userValues[2]))
                data.updateDetails(fname.getText(),"fname");
            if(!lname.getText().equals(this.userValues[3]))
                data.updateDetails(lname.getText(),"lname");
            if(!city.getText().equals(this.userValues[4]))
                data.updateDetails(city.getText(),"city");
            this.home_btn.fire();
        }
    }
    public void handleMenuClick(ActionEvent actionEvent){
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
    public void handleSettingsClick(){
        user_edit_pane.toFront();
    }
    public void handleMessagesClick(){

    }
    public void handleLogoutClick(){
        LoginInterface.nullifyCurrentUser();
        this.openNewWindowAndCloseOld("Vaction4U","/signIn.fxml",600, 400);
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void handleClose(){
        this.home_btn.getScene().getWindow().fireEvent(new WindowEvent(this.home_btn.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    public void handleOpenImage(){
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
    public void handleSearch(){
        this.home_scr_items.getChildren().clear();
        this.searchInterface.search(this.searchBox.getText(),this.searchDate.getValue());
    }
    public void handlePublishNewVacation(ActionEvent actionEvent) {
        String uniqueID = UUID.randomUUID().toString();
        Vacation vacation = new Vacation(add_text_region.getText(),add_text_city.getText(),add_text_price.getText(),add_date_start.getValue().toString(),add_date_end.getValue().toString(),add_text_description.getText(),this.imageID,uniqueID,LoginInterface.getCurrentUser(),"admin");
        if(addVacInterface.detailsApprove(vacation.toStringArray())){
            addVacInterface.wiriteToDB(vacation.toStringArray());
            this.home_btn.fire();
        }
        else
            this.add_msg.setText("All fields are required!");
    }
    public void handleApplication(){
        this.openNewWindow("Payment", "/paymentDialog.fxml",375,419);
        if(this.purchase_decision){
            //do add application
            String vacation_id = uiController.item.item.getListing_id();//vacation_id
            String applicant = LoginInterface.getCurrentUser();//applicant
            PurchaseApplication purchaseApplication = new PurchaseApplication(vacation_id,applicant);
            purAddInterface.acceptApplication(purchaseApplication);

            this.purchase_decision =false;
            this.home_btn.fire();
        }
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
            System.out.println("FXML Error");
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
    void openDesciption(){
        details_dest_lbl.setText("Destination: "+ uiController.item.item.getDest_region()+","+ uiController.item.item.getDest_city());
        details_start_lbl.setText("Departure: "+ uiController.item.item.getStart().replace("-","/"));
        details_end_lbl.setText("Return: "+ uiController.item.item.getEnd().replace('-','/'));
        details_price_lbl.setText("Price: "+ uiController.item.item.getPrice()+"$");
        details_desc_area.setText(uiController.item.item.getDescription());
        details_desc_area.setWrapText(true);
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/images/userImages/"+ uiController.item.item.getImage_path());
        details_img.setImage(new Image(file.toURI().toString()));
        this.details_scr.toFront();
    }

}
