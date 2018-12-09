package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VacationDatabase extends Database{
    private String name;

    public VacationDatabase(){
        super("vacation_server");
        String sql = "CREATE TABLE IF NOT EXISTS vacation_table(\n"
                + "	vacation_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	destination_region TEXT NOT NULL,\n"
                + "	destination_city TEXT NOT NULL,\n"
                + "	price TEXT NOT NULL,\n"
                + "	departure TEXT NOT NULL,\n"
                + "	arrival TEXT NOT NULL,\n"
                + "	description TEXT NOT NULL,\n"
                + "	picture_name TEXT\n"
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
    //0-destination_region
    //1-destination_city
    //2-price
    //3-departure
    //4-arrival
    //5-description
    //6-picture_name
    public void createTuple(String[] tuple){
        if(tuple.length!=7)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO vacation_table (destination_region,destination_city,price,departure,arrival,description,picture_name) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1, tuple[0]);
            pstmt.setString(2, tuple[1]);
            pstmt.setString(3, tuple[2]);
            pstmt.setString(4, tuple[3]);
            pstmt.setString(5, tuple[4]);
            pstmt.setString(6, tuple[5]);
            pstmt.setString(7, tuple[6]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database insertion error.");
        }
    }
    public void editTuple(String field, String newValue, String vacation_id){
        String sql = "UPDATE vacation_table SET "+field+" = ? "
                + "WHERE vacation_id = ?";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, newValue);
            pstmt.setString(2, vacation_id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void deleteTuple(String vacation_id){
        String sql = "DELETE FROM vacation_table WHERE vacation_id = ?";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, vacation_id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public String[] getByVacationID(String vacation_id){
        String sql = "SELECT destination_region, destination_city, price, departure, arrival,description, picture_name " + "FROM vacation_table WHERE vacation_id = ?";
        ResultSet rs = null;
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,vacation_id);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.parseResultSet(rs);
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
        String[] res = new String[7];
        try {
            res[0] = rs.getString("vacation_id");
            res[1] = rs.getString("destination_region");
            res[2] = rs.getString("destination_city");
            res[3] = rs.getString("price");
            res[4] = rs.getString("departure");
            res[5] = rs.getString("arrival");
            res[6] = rs.getString("description");
            res[7] = rs.getString("picture_name");
        }
        catch (SQLException e){
            System.out.println("Information retrieval error.");
        }
        return res;
    }
}
