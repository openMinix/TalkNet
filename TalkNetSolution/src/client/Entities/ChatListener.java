package client.Entities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import client.Client.Main;
import client.Windows.ChatFrame;

//alext - listen message from network
public class ChatListener implements ChatManagerListener {

	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		// TODO Auto-generated method stub
		chat.addMessageListener(new MessageListener()
	      {
			//alext - process a message for network
	        public void processMessage(Chat chat1, Message message)
	        {
	          System.out.println("Received message: " 
	            + (message != null ? chat1.getParticipant() + ": " +message.getBody() : "NULL"));
	          
	          /* If the message is an invitation, ignore it. */
	          if (message.getFrom().contains("@conference.127.0.0.1"))
	        	  return;
	          
	          String from = message.getFrom().split("@")[0];
	          System.out.println("from: " + from);
	          ChatFrame cf = ((ChatFrame) Manager.getManager().chatWindows.get(from));
	          if ( cf != null )
	        	  cf.displayMessage(from, message.getBody());
	          else {
	        	  System.out.println("cf e null: "+Manager.getManager().chatWindows.size());
	        	  ChatFrame fr = new ChatFrame(from);
	              Manager.getManager().chatWindows.put(from, fr);
	              fr.displayMessage(from, message.getBody());
	          }
	        }
	      });
	}

	

}
