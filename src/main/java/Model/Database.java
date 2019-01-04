package Model;

import java.io.File;
import java.sql.*;

public  abstract class Database {
    protected Connection currentConnection;
    protected String name;
    protected String tableName;

    protected Database(String name){
        this.name = name;
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
    public ResultSet getTuplesByField(String field, String value){
        String sql = "SELECT * FROM "+this.tableName+" WHERE " + field + " = ?;";
        ResultSet rs = null;
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,value);
            rs = pstmt.executeQuery();
            return rs;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    ResultSet getTupleByFields(String[] fields, String[] values, String logicCase){
        if(fields.length!=values.length)
            throw new RuntimeException("Asymmetrical fields/values amount given for search!");
        String sql = "SELECT * FROM "+this.tableName+" WHERE ";
        for (int i=0;i<fields.length;i++) {
            sql = sql + fields[i] + " = " + "?";
            if (i + 1 < fields.length) {
                switch (logicCase) {
                    case "AND":
                        sql = sql + " AND ";
                        break;
                    case "OR":
                        sql = sql + " OR ";
                        break;
                }
            }
        }
        sql = sql+";";
        return this.executeGetStatement(sql,values);
    }
    void executeUpdateStatement(String statement, String[] arguments){
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(statement);
            // set the corresponding param
            for(int i=0;i<arguments.length;i++){
                pstmt.setString(i+1, arguments[i]);
            }
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    ResultSet executeGetStatement(String statement, String[] arguments){
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(statement);
            // set the corresponding param
            for(int i=0;i<arguments.length;i++){
                pstmt.setString(i+1, arguments[i]);
            }
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
