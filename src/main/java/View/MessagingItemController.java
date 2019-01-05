package View;

import Controller.LoginInterface;
import Controller.MessagingInterface;
import Model.MessagingSession;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class MessagingItemController {
    public static final String delimiter = ":+:";
    @FXML
    private TextArea msg_cont;
    @FXML
    private TextArea msg_new;
    private MessagingSession item;

    void defineContent(MessagingSession messagingSession){
        this.item = messagingSession;
        this.load();

    }
    private void refresh(){
        this.load();
    }
    @FXML
    private void sendMessage(){
        MessagingInterface.MI.sendMessage(item.getUser1(),item.getUser2(),msg_new.getText());
        msg_cont.setText(msg_cont+"You: "+msg_new.getText()+'\n');
        msg_new.setText("");
    }
    private void load(){
        String[] content = this.item.getMessages();
        for(int i=0;i<content.length;i++){
            String[] buffer = content[i].split(MessagingItemController.delimiter);
            if(buffer[0].equalsIgnoreCase(LoginInterface.getCurrentUser()))
                msg_cont.setText(msg_cont.getText()+"You: "+buffer[1]+'\n');
            else
                msg_cont.setText(msg_cont.getText()+buffer[0]+": "+buffer[1]+'\n');
        }
    }

}
