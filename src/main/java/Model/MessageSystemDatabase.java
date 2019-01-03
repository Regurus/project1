package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageSystemDatabase extends Database{

    private final String delimiter = ";";//used as a delimiter for

    public MessageSystemDatabase(){
        //REMEMBER TO DEFINE USER1 AND USER2 AS KEY
        super("message_server");
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

    public void createTuple(String[] tuple){

    }

    public void appendToTuple(String user1,String user2, String new_message){
        //getTupleByUsers
        //Append new message to the conversation (simple)
        //editTuple(update the existing tuple)
    }

    public void getTuplesByUser(String user){

    }

    private void getTupleByUsers(String user1, String user2){

    }

    public void editTuple(String user1, String user2, String field, String new_value){

    }

    //change to messagingSession later.
    public /*MessagingSession*/ void parseConversation(ResultSet rs){

    }
}
