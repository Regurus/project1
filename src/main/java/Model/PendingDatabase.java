package Model;

import java.sql.*;
import java.util.ArrayList;

public class PendingDatabase extends Database{
    private String name;

    public PendingDatabase(){
        super("pending_server");
        String sql = "CREATE TABLE IF NOT EXISTS pending_table(\n"
                + "	vacation_id TEXT NOT NULL,\n"
                + "	applicant TEXT NOT NULL,\n"
                + " PRIMARY KEY(vacation_id,applicant)\n"
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
    //0-vacation_id
    //1-applicant
    public void createTuple(String[] tuple){
        if(tuple.length!=2)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO pending_table (vacation_id,applicant) VALUES(?,?)";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1, tuple[0]);
            pstmt.setString(2, tuple[1]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Database insertion error.");
        }
    }

    public PurchaseApplication[] getTuplesByApplicant(String applicant){
        return this.getTuplesByField(applicant,"applicant");
    }

    public PurchaseApplication[] getTuplesByVacation(String vacation_id){
        return getTuplesByField(vacation_id,"vacation_id");
    }

    private PurchaseApplication[] getTuplesByField(String field_value, String field_name){
        String sql = "SELECT * FROM pending_table WHERE " + field_name + " = ?;";
        ResultSet rs = null;
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,field_value);
            rs = pstmt.executeQuery();
            return this.parseResultSet(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private PurchaseApplication[] parseResultSet(ResultSet rs){
        ArrayList<PurchaseApplication> res = new ArrayList<>();
        try{
            while(rs.next()){
                res.add(new PurchaseApplication(rs.getString(0),rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        PurchaseApplication[] arr = new PurchaseApplication[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    //<editor-fold desc="Delete Interface">
    public void deleteTuplesByApplicant(String name){
        deleteTuplesByField(name,"applicant");
    }

    public void deleteTuplesByVacation(String vacation_id){
        deleteTuplesByField(vacation_id,"vacation_id");
    }

    private void deleteTuplesByField(String field_value, String field_name){
        String sql = "DELETE FROM pending_table WHERE " + field_name + " = ?";
        try {
            PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, field_value);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteTuple(String vacation_id, String applicant){
        String sql = "SELECT * FROM vacation_table WHERE vacation_id = ? AND applicant = ?;";
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement(sql);
            pstmt.setString(1,vacation_id);
            pstmt.setString(2,applicant);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //</editor-fold>
}
