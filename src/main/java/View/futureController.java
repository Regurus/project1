package View;

import Controller.LoginInterface;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.WindowEvent;

import static javafx.application.Platform.exit;

public class futureController extends WindowController{
    @FXML
    MenuBar menu;
    public void appExit(){
        exit();
    }
    public void appLogOut(){
        LoginInterface.nullifyCurrentUser();
        this.openNewWindow("Vactiov4U","/signIn.fxml",600, 400);
        this.menu.getScene().getWindow().fireEvent(new WindowEvent(this.menu.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));

    }
    public void appDelete(){
        this.openNewWindow("Confirm","/deleteUser.fxml",400, 250);
    }
    public void appEdit(){
        this.openNewWindow("Edit Details","/updateUser.fxml",600,400);
    }
    public void appView(){
        this.openNewWindow("Search Details","/viewUser.fxml",600,400);
    }
}
