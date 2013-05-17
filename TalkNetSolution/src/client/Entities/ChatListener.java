package client.Entities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class ChatListener implements ChatManagerListener {

	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		// TODO Auto-generated method stub
		chat.addMessageListener(new MessageListener()
	      {
	        public void processMessage(Chat chat1, Message message)
	        {
	          System.out.println("Received message: " 
	            + (message != null ? chat1.getParticipant() + ": " +message.getBody() : "NULL"));
	        }
	      });
	}

	

}
