package Model;

import java.sql.*;

public class LoginDatabase extends Database {
    private String name;

    public LoginDatabase(){
        super("login_server");
        this.tableName = "login_table";
        String sql = "CREATE TABLE IF NOT EXISTS login_table (\n"
                + "	login text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + "	fname text NOT NULL,\n"
                + "	lname text NOT NULL,\n"
                + "	city text NOT NULL\n"
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
    public void createTuple(String[] tuple){
        if(tuple.length!=5)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO login_table (login,password,fname,lname,city) VALUES(?,?,?,?,?)";
        String[] args = {tuple[0],tuple[1],tuple[2],tuple[3],tuple[4]};
        this.executeUpdateStatement(sql,args);
    }
    public void editTuple(String field, String newValue, String login){
        String sql = "UPDATE login_table SET "+field+" = ? "
                + "WHERE login = ?";
        String[] args = {newValue,login};
        this.executeUpdateStatement(sql,args);

    }
    public void deleteTuple(String login){
        String sql = "DELETE FROM login_table WHERE login = ?";
        String[] args = {login};
        this.executeUpdateStatement(sql,args);
    }


    public String[] getByLogin(String login){
        String sql = "SELECT login, password, fname, lname, city " + "FROM login_table WHERE login = ?";
        String[] args = {login};
        ResultSet rs = this.executeGetStatement(sql,args);
        return parseResultSet(rs);
    }

    private String[] parseResultSet(ResultSet rs){
        boolean exists;
        try {
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if(!exists)
            return null;
        String[] res = new String[5];
        try {
            res[0] = rs.getString("login");
            res[1] = rs.getString("password");
            res[2] = rs.getString("fname");
            res[3] = rs.getString("lname");
            res[4] = rs.getString("city");
        }
        catch (SQLException e){
            System.out.println("Information retrieval error.");
        }
        return res;
    }
}