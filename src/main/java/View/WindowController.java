package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController {
    protected final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    protected final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    protected void openNewWindow(String windowName, String fxmlFile,int width,int height){
        try {
            Stage stage = new Stage();
            stage.setTitle(windowName);
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource(fxmlFile).openStream());
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            if(fxmlLoader.getController() instanceof InitialiableWindow)
                ((InitialiableWindow)fxmlLoader.getController()).setInitialValues();
            stage.show();
        }
        catch (Exception e) {e.printStackTrace();}
    }

}
