package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
/**
 * Singleton implementation for settings file.
 * All operations involving change to any setting should be done with this class.
 */
public class Settings {
    private static Settings ourInstance = new Settings();
    private Properties file;
    public boolean fileExists = true;
    private final String SETTING_LOCATION = "src/main/resources/Settings.ini";

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
        file = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(SETTING_LOCATION);
        } catch (FileNotFoundException e) {
            fileExists = false;
            return;
        }
        try {
            file.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setProperty(String key,String prop){
        file.setProperty(key, prop);
    }
    public String getProperty(String key){
        return file.getProperty(key);
    }
    public void saveFile(){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(SETTING_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Warning: Unable to save setting file next run will need a new deployment procedure!");
            return;
        }
        try {
            file.store(out, "Last Update: "+new Date().getTime());
        } catch (IOException e) {
            System.out.println("Warning: Unable to save setting file next run will need a new deployment procedure!");
            return;
        }
        try {
            out.close();
        } catch (IOException e) {
            //!?!
        }
    }
}
