package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String name;

    public Database(String name){
        String address = Settings.getInstance().getProperty(name);
        if(address == null) {
            throw new RuntimeException("No allocation for the database: " + name + "\nPlease refer to settings");
        }
        deployDataBase(address,name);
        //save open connection

    }
    public void deployDataBase(String location,String name){
        String url = "jdbc:sqlite:"+location + name;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createTuple(){

    }
    public void editTuple(){

    }
    public String[] getTuple(){
        return null;
    }
    public void deleteTuple(){

    }
    public void closeConnection(){

    }
}
