package Model;

import java.sql.*;

public class LoginDatabase extends Database {
    private String name;

    public LoginDatabase(){
        super("login_server");
        String sql = "CREATE TABLE IF NOT EXISTS login_table (\n"
                + "	login text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	fname text NOT NULL,\n"
                + "	lname text NOT NULL,\n"
                + "	city text NOT NULL,\n"
                + "	bday date NOT NULL\n"
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
    //2-fname
    //3-lname
    //4-city
    //5-bday
    public void createTuple(String[] tuple){
        if(tuple.length!=6)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO login_table (login,password,fname,lname,city,bday) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1, tuple[0]);
            pstmt.setString(2, tuple[1]);
            pstmt.setString(3, tuple[2]);
            pstmt.setString(4, tuple[3]);
            pstmt.setString(5, tuple[4]);
            pstmt.setString(6, tuple[5]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database insertion error.");
        }
    }
    public void editTuple(String field, String newValue, String login){
        String sql = "UPDATE login_table SET ? = ? "
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
        String sql = "SELECT login, password, fname, lname, city, bday " + "FROM login_table WHERE ? = ?";
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
        String[] res = new String[6];
        try {
            res[0] = rs.getString("login");
            res[1] = rs.getString("password");
            res[2] = rs.getString("fname");
            res[3] = rs.getString("lname");
            res[4] = rs.getString("city");
            res[5] = rs.getString("bday");
        }
        catch (SQLException e){
            System.out.println("Information retrieval error.");
        }
        return res;
    }
    public void deleteTuple(String login){
        String sql = "DELETE FROM login_table WHERE login = ?";
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
