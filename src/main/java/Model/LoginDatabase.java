package Model;

import java.sql.*;

public class LoginDatabase extends Database {
    private String name;
    private Connection currentConnection;

    public LoginDatabase(){
        super("login_server_path");
        String sql = "CREATE TABLE IF NOT EXISTS accounts (\n"
                + "	login text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	name text NOT NULL,\n"
                + "	lastName text NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + ");";
        try{
            // create a new table
            Statement stmt = currentConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    //tuple structure as array:
    //0-login
    //1-password
    //2-name
    //3-last name
    //4-address line
    public void createTuple(String[] tuple){
        if(tuple.length!=5)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO accounts(login,password,name,lastName,address) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1, tuple[0]);
            pstmt.setString(2, tuple[1]);
            pstmt.setString(3, tuple[2]);
            pstmt.setString(4, tuple[3]);
            pstmt.setString(5, tuple[4]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database insertion error.");
        }
    }
    public void editTuple(String field, String newValue, String login){
        String sql = "UPDATE accounts SET ? = ? "
                + "WHERE login = ?";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, field);
            pstmt.setString(2, newValue);
            pstmt.setString(3, login);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    //limited to a single result
    public String[] getTuple(String field,String value){
        String sql = "SELECT login, password, name, lastName, address " + "FROM accounts WHERE ? = ?";
        ResultSet rs = null;
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,field);
            pstmt.setString(2,value);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        boolean exists = false;
        try {
            exists = rs.next();
        } catch (SQLException e) {}

        if(!exists)
            return null;
        String[] res = new String[5];
        try {
            res[0] = rs.getString("login");
            res[1] = rs.getString("password");
            res[2] = rs.getString("name");
            res[3] = rs.getString("lastName");
            res[4] = rs.getString("address");
        }
        catch (SQLException e){
            System.out.println("Information retrieval error.");
        }
        return res;
    }
    public void deleteTuple(String login){
        String sql = "DELETE FROM accounts WHERE login = ?";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, login);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
