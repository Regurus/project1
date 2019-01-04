package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageSystemDatabase extends Database{

    public static final String delimiter = ";";//used as a delimiter for

    public MessageSystemDatabase(){
        super("message_server");
        this.tableName = "message_table";
        String sql = "CREATE TABLE IF NOT EXISTS message_table(\n"
                + "	user1 TEXT NOT NULL,\n"
                + "	user2 TEXT NOT NULL,\n"
                + " seenByUser1 INTEGER NOT NULL,\n"
                + "	seenByUser2 INTEGER NOT NULL,\n"
                + "	content TEXT NOT NULL,\n"
                + "	PRIMARY KEY (user1, user2)\n"
                + ");";
        try{
            // create a new table
            Statement stmt = currentConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void createTuple(MessagingSession session){
        String sql = "INSERT INTO "+this.name+" (user1,user2,seenByUser1,seenByUser2,content) VALUES(?,?,?,?,?)";
        String[] args = {session.getUser1(),session.getUser2(),"1","0",session.getContent()};
        this.executeUpdateStatement(sql,args);
    }

    public void appendToTuple(String user1,String user2, String new_message){
        //getTupleByUsers
        //Append new message to the conversation (simple)
        //editTuple(update the existing tuple)
        boolean flag = false;
        String[] fields = {"user1","user2"};
        String[] values = {user1,user2};
        ResultSet temp = this.getTupleByFields(fields,values,"AND");

        try{
            String[] otherFields = {"user2","user1"};
            if(!temp.next()){
                temp = this.getTupleByFields(otherFields,values,"AND");
                flag = true;
            }
            if(!flag){
                this.editTuple(fields[0],fields[1],"content",temp.getString(5)+MessageSystemDatabase.delimiter+new_message);
            }
            else{
                this.editTuple(otherFields[0],otherFields[1],"content",temp.getString(5)+MessageSystemDatabase.delimiter+new_message);
            }
        }
        catch (Exception e){
            System.out.printf("Database read error!");
        }
    }
    public void editTuple(String user1, String user2, String field, String new_value){
        String sql = "UPDATE "+this.name+" SET "+field+" = ? "+ "WHERE user1 = ? AND user2 = ?";
        String[] args = {new_value,user1,user2};
        this.executeUpdateStatement(sql,args);

    }
    public boolean checkIfExists(String user1,String user2){
        boolean flag = false;
        String[] fields = {"user1","user2"};
        String[] otherFields = {"user2","user1"};
        String[] values = {user1,user2};
        ResultSet temp = this.getTupleByFields(fields,values,"AND");
        try{
            if(!temp.next()){
                temp = this.getTupleByFields(otherFields,values,"AND");
                if(!temp.next())
                    return false;
            }
        }
        catch (Exception e){
            System.out.printf("Database read error!");
        }
        return true;
    }
    //change to messagingSession later.
    public MessagingSession[] parseConversation(ResultSet rs){
        ArrayList<MessagingSession> res = new ArrayList<>();
        try{
            while(rs.next()){
                res.add(new MessagingSession(rs.getString(1),rs.getInt(3) == 1,rs.getString(2),rs.getInt(4)==1,rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        MessagingSession[] arr = new MessagingSession[res.size()];
        arr = res.toArray(arr);
        return arr;
    }
    public MessagingSession[] getByUser(String user){
        MessagingSession[] p1 = this.parseConversation(this.getTuplesByField("user1",user));
        MessagingSession[] p2 = this.parseConversation(this.getTuplesByField("user2",user));
        MessagingSession[] result = new MessagingSession[p1.length+p2.length];
        System.arraycopy(p1,0,result,0,p1.length);
        System.arraycopy(p2,0,result,p1.length-1,p2.length);
        return result;
    }
}
