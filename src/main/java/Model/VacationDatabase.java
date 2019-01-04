package Model;

import Controller.LoginInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VacationDatabase extends Database{
    private String name;

    //<editor-fold desc="Creation management">
    public VacationDatabase(){
        super("vacation_server");
        this.tableName = "vacation_table";
        String sql = "CREATE TABLE IF NOT EXISTS vacation_table(\n"
                + "	vacation_id TEXT PRIMARY KEY,\n"
                + "	destination_region TEXT NOT NULL,\n"
                + "	destination_city TEXT NOT NULL,\n"
                + "	price TEXT NOT NULL,\n"
                + "	departure TEXT NOT NULL,\n"
                + "	arrival TEXT NOT NULL,\n"
                + "	description TEXT NOT NULL,\n"
                + "	picture_name TEXT,\n"
                + "	owner TEXT NOT NULL,\n"
                + " applicant TEXT NOT NULL\n"
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
    public void createTuple(String[] tuple){
        if(tuple.length!=10)
            throw new RuntimeException("Incorrect tuple size, cannot index");
        String sql = "INSERT INTO vacation_table (vacation_id,destination_region,destination_city,price,departure,arrival,description,picture_name,owner,applicant) VALUES(?,?,?,?,?,?,?,?,?,?)";
        String[] args = {tuple[7],tuple[0],tuple[1],tuple[2],tuple[3],tuple[4],tuple[5],tuple[6],tuple[8],tuple[9]};
        this.executeUpdateStatement(sql,args);
    }
    //</editor-fold>

    //<editor-fold desc="Content management (edition & deletion)">
    private void editTuple(String vacation_id, String field, String newValue){
        String sql = "UPDATE vacation_table SET "+field+" = ? "
                + "WHERE vacation_id = ?";
        String[] args = {newValue, vacation_id};
        this.executeUpdateStatement(sql,args);

    }
    public void deleteTuple(String vacation_id){
        String sql = "DELETE FROM vacation_table WHERE vacation_id = ?";
        String[] args = {vacation_id};
        this.executeUpdateStatement(sql,args);
    }
    //</editor-fold>

    //<editor-fold desc="Querying">
    public Vacation[] getTuplesByLocationANDDate(String location, String date){
        String sql = "SELECT * FROM vacation_table WHERE (destination_region = ? OR destination_city = ?) AND departure = ?;";
        String[] args = {location,location,date};
        ResultSet rs = this.executeGetStatement(sql,args);
        return this.parseResultSetNoCurrent(rs);
    }

    public Vacation[] getTuplesByLocation(String location){
        String[] fields = {"destination_region","destination_city"};
        String[] values = {location,location};
        return this.parseResultSetNoCurrent(this.getTupleByFields(fields,values,"OR"));
    }

    public Vacation getTupleByID(String vacation_id){
        Vacation[] tuple = this.parseResultSet(getTuplesByField("vacation_id",vacation_id ));
        return tuple[0];//assumption: querying by key should always return 1 result, as the key field has no duplicates
    }

    public Vacation[] getTuplesByDate(String date){
        return this.parseResultSetNoCurrent(this.getTuplesByField("departure",date));
    }

    public Vacation[] getVacationsByName(String name){
        return this.parseResultSet(getTuplesByField("owner",name));
    }


    private Vacation[] filterResultSetByDate(Vacation[] parseResultSet, String departure){
        ArrayList<Vacation> relevantVacations = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date now = sdf.parse(departure);

            for(Vacation v: parseResultSet){
                if(sdf.parse(v.getStart()).after(now))
                    relevantVacations.add(v);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Vacation[] arr = new Vacation[relevantVacations.size()];
        arr = relevantVacations.toArray(arr);
        return arr;
    }

    public Vacation[] getTwentyVactions(){
        try{
            PreparedStatement pstmt  = this.currentConnection.prepareStatement("SELECT * FROM vacation_table WHERE applicant = ? LIMIT 20;");
            pstmt.setString(1,"admin");
            ResultSet rs = pstmt.executeQuery();
            return this.parseResultSetNoCurrent(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Vacation[] parseResultSetNoCurrent(ResultSet rs){
        ArrayList<Vacation> res = new ArrayList<>();
        try{
            while(rs.next()){
                if(!rs.getString(9).equalsIgnoreCase(LoginInterface.getCurrentUser()))
                    res.add(new Vacation(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(1),rs.getString(9),rs.getString(10)));
                else
                    rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Vacation[] arr = new Vacation[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    private Vacation[] parseResultSet(ResultSet rs){
        ArrayList<Vacation> res = new ArrayList<>();
        try{
            while(rs.next()){
                    res.add(new Vacation(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(1),rs.getString(9),rs.getString(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Vacation[] arr = new Vacation[res.size()];
        arr = res.toArray(arr);
        return arr;
    }
    //</editor-fold>

    //<editor-fold desc="Purchasing application management">
    public boolean hasApplicant(String vacation_id){
        Vacation tuple = getTupleByID(vacation_id);
        if(tuple.getApplicant().equals("admin")){
            return false;
        }
        else{
            return true;
        }
    }
    public void applyForPurchase(String vacation_id, String applicant){
        if(!hasApplicant(vacation_id)){
            editTuple(vacation_id,"applicant",applicant);
        }
    }
    public void declinePurchaseApplication(String vacation_id){
        editTuple(vacation_id,"applicant","admin");
    }
    //</editor-fold>
}
