package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VacationDatabase extends Database{
    private String name;

    public VacationDatabase(){
        super("vacation_server");
        String sql = "CREATE TABLE IF NOT EXISTS vacation_table(\n"
                + "	vacation_id TEXT PRIMARY KEY,\n"
                + "	destination_region TEXT NOT NULL,\n"
                + "	destination_city TEXT NOT NULL,\n"
                + "	price TEXT NOT NULL,\n"
                + "	departure TEXT NOT NULL,\n"
                + "	arrival TEXT NOT NULL,\n"
                + "	description TEXT NOT NULL,\n"
                + "	picture_name TEXT\n,"
                + "	owner TEXT NOT NULL\n"
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
        if(tuple.length!=9)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO vacation_table (vacation_id,destination_region,destination_city,price,departure,arrival,description,picture_name,owner) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1, tuple[7]);
            pstmt.setString(2, tuple[0]);
            pstmt.setString(3, tuple[1]);
            pstmt.setString(4, tuple[2]);
            pstmt.setString(5, tuple[3]);
            pstmt.setString(6, tuple[4]);
            pstmt.setString(7, tuple[5]);
            pstmt.setString(8, tuple[6]);
            pstmt.setString(9, tuple[8]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database insertion error.");
        }
    }
//    public void editTuple(String field, String newValue, String vacation_id){
//        String sql = "UPDATE vacation_table SET "+field+" = ? "
//                + "WHERE vacation_id = ?";
//        try {
//            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
//            // set the corresponding param
//            pstmt.setString(1, newValue);
//            pstmt.setString(2, vacation_id);
//            // update
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
    /*public void deleteTuple(String vacation_id){
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

    }*/


    public Vacation[] getTuplesByLocationANDDate(String location, String date){
        String sql = "SELECT * FROM vacation_table WHERE (destination_region = ? OR destination_city = ?) AND departure = ?;";
        ResultSet rs = null;
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,location);
            pstmt.setString(2,location);
            pstmt.setString(3,date);
            rs = pstmt.executeQuery();
            return this.parseResultSet(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Vacation[] getTwentyVactions(){
        ArrayList<Vacation> res = new ArrayList<>();
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement("SELECT * FROM vacation_table LIMIT 20;");
            ResultSet rs = pstmt.executeQuery();
            return this.parseResultSet(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Vacation[] parseResultSet(ResultSet rs){
        ArrayList<Vacation> res = new ArrayList<>();
        try{
            while(rs.next()){
                res.add(new Vacation(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(1),rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Vacation[] arr = new Vacation[res.size()];
        arr = res.toArray(arr);
        return arr;
    }
}
