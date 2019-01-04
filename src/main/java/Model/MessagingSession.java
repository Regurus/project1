package Model;

public class MessagingSession {
    //container class for a tuple in the message database, needs to contain whatever it contains.
    private String user1;
    private boolean seenByUser1;
    private String user2;
    private boolean seenByUser2;
    private String content;
    public MessagingSession(String user1, boolean seenByUser1, String user2, boolean seenByUser2, String content) {
        this.user1 = user1;
        this.seenByUser1 = seenByUser1;
        this.user2 = user2;
        this.seenByUser2 = seenByUser2;
        this.content = content;
    }

    public void setSeenByUser1() {
        this.seenByUser1 = true;
    }

    public void setSeenByUser2() {
        this.seenByUser2 = true;
    }

    public boolean isSeenByUser1() {
        return seenByUser1;
    }

    public boolean isSeenByUser2() {
        return seenByUser2;
    }
    public String[] getMessages(){
        String[] result = this.content.split(MessageSystemDatabase.delimiter);
        return result;
    }
}

