package Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PurchasedDatabase extends Database{
    private String name;

    //<editor-fold desc="Creation management">
    public PurchasedDatabase() {
        super("purchased_server");
        this.tableName = "purchased_table";
        String sql = "CREATE TABLE IF NOT EXISTS purchased_table(\n"
                + "	vacation_id TEXT PRIMARY KEY,\n"
                + "	destination_region TEXT NOT NULL,\n"
                + "	destination_city TEXT NOT NULL,\n"
                + "	price TEXT NOT NULL,\n"
                + "	departure TEXT NOT NULL,\n"
                + "	arrival TEXT NOT NULL,\n"
                + "	description TEXT NOT NULL,\n"
                + "	picture_name TEXT,\n"
                + "	owner TEXT NOT NULL,\n"
                + " applicant TEXT NOT NULL,\n"
                + " purchase_date TEXT NOT NULL\n"
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
    //7-vacation_id
    //8-owner
    //9-applicant
    //10-purchase_date
    public void createTuple(String[] tuple){
        if(tuple.length!=11)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO purchased_table (vacation_id,destination_region,destination_city,price,departure,arrival,description,picture_name,owner,applicant,purchase_date) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String[] args = {tuple[7],tuple[0],tuple[1],tuple[2],tuple[3],tuple[4],tuple[5],tuple[6],tuple[8],tuple[9],tuple[10]};
        this.executeUpdateStatement(sql,args);
    }
    //</editor-fold>

    //<editor-fold desc="Querying">
    public PurchasedVacation[] getTuplesByLocationANDDate(String location, String date){
        String sql = "SELECT * FROM purchased_table WHERE (destination_region = ? OR destination_city = ?) AND departure = ?;";
        String[] args = {location,location,date};
        ResultSet rs = this.executeGetStatement(sql,args);
        return this.parseResultSet(rs);

    }

    public PurchasedVacation[] getTuplesByLocation(String location){
        String sql = "SELECT * FROM purchased_table WHERE destination_region = ? OR destination_city = ?;";
        String[] args = {location,location};
        ResultSet rs = this.executeGetStatement(sql,args);
        return this.parseResultSet(rs);
    }

    public PurchasedVacation getTupleByID(String vacation_id){
        PurchasedVacation[] tuple = this.parseResultSet(getTuplesByField("vacation_id",vacation_id));
        return tuple[0];//assumption: querying by key should always return 1 result, as the key field has no duplicates
    }

    public PurchasedVacation[] getTuplesByDate(String date){
        return this.parseResultSet(this.getTuplesByField("departure",date));
    }

    public PurchasedVacation[] getVacationsBySeller(String name){
        return this.parseResultSet(getTuplesByField("owner",name));
    }

    public PurchasedVacation[] getVacationsByBuyer(String name){
        return this.parseResultSet(getTuplesByField("applicant",name));
    }

    /*private PurchasedVacation[] getTuplesByField(String field_value, String field_name){
        String sql = "SELECT * FROM purchased_table WHERE " + field_name + " = ?;";
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
    }*/

    private PurchasedVacation[] filterResultSetByDate(PurchasedVacation[] parseResultSet, String departure){
        ArrayList<PurchasedVacation> relevantVacations = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date now = sdf.parse(departure);

            for(PurchasedVacation v: parseResultSet){
                if(sdf.parse(v.getStart()).after(now))
                    relevantVacations.add(v);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PurchasedVacation[] arr = new PurchasedVacation[relevantVacations.size()];
        arr = relevantVacations.toArray(arr);
        return arr;
    }

    private PurchasedVacation[] parseResultSet(ResultSet rs){
        ArrayList<PurchasedVacation> res = new ArrayList<>();
        try{
            while(rs.next()){
                res.add(new PurchasedVacation(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(1),rs.getString(9),rs.getString(10),rs.getString(11)));//jaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        PurchasedVacation[] arr = new PurchasedVacation[res.size()];
        arr = res.toArray(arr);
        return arr;
    }
    //</editor-fold>


}
