package Model;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    protected Connection currentConnection;
    protected Database(String name){
        File a = new File("src/main/resources/Database.sqlite3");
        File parentFolder = new File(a.getParent());
        deployDataBase(parentFolder.getParent(),name);
    }
    protected void deployDataBase(String location,String name){
        String url = "jdbc:sqlite:"+location + "\\" + name;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database connection established.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        currentConnection = conn;
    }
    public void closeConnection(){
        try {
            currentConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection closure error.");
        }
    }
}
