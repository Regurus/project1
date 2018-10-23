import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        //root.getStylesheets().add(getClass().getResource("Main_Style.css").toString());
        primaryStage.setTitle("Nituts");
        primaryStage.setScene(new Scene(root, 600, 400));
        //SetStageCloseEvent(primaryStage);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();

    }
    public static void main(String[] args) {
        //deployment check
        //if not deployed do deployment procedure
        //initialize database connections
        //open Login UI
        launch(args);
    }
}
