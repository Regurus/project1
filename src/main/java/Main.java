import Model.Settings;

public class Main {
    public static void main(String[] args) {
        //deployment check
        //if not deployed do deployment procedure
        //initialize database connections
        //open Login UI
        Settings.getInstance().setProperty("key","value");
        Settings.getInstance().saveFile();
    }
}
