import Model.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("src/main/java/View/signIn.fxml"));
        //root.getStylesheets().add(getClass().getResource("Main_Style.css").toString());
        primaryStage.setTitle("Nituts");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        //SetStageCloseEvent(primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //deployment check
        //if not deployed do deployment procedure
        //initialize database connections
        //open Login UI
        Settings.getInstance().setProperty("key","value");
        Settings.getInstance().saveFile();
    }
}
