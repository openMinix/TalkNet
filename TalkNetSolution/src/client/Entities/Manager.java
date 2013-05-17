package client.Entities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;


public class Manager {

	private ChatManager chatManager;
	private ChatManagerListener messageListener;
	private Roster roster;
	

	//manager singleton
	private static Manager instance = null;
	private Manager() {
		//defeat instances
		roster = ConnectionManager.connection.getRoster();
		chatManager = ConnectionManager.connection.getChatManager();
		messageListener = new ChatListener();
		chatManager.addChatListener(messageListener);	
	}
	
	public static Manager getManager() {
		if ( instance == null )
			return new Manager();
		else
			return instance;
	}
	
	//maybe called on event ????
	
	public void setStatus(boolean avaiable, String status) {
		Presence.Type type;
		if ( avaiable )
			type = Presence.Type.available;
		else
			type = Presence.Type.unavailable;
		
		Presence presence = new Presence(type);
		presence.setStatus(status);
		ConnectionManager.connection.sendPacket(presence);
	}
	
	public void createEntry(String user, String name ) {
		try {
			roster.createEntry(user, name, null);
		}
		catch (Exception e) {
			System.out.println("Manager.Exception: " + e.toString());
		}
	}
	
	public void sendMessage(String message, String toBuddy) {
		try {
			/*
			Message m = new Message();
			m.setTo(toBuddy);
			m.setSubject("adasdsa");
			m.setBody(message);
			ConnectionManager.connection.sendPacket(m);
			*/
			Chat chat = chatManager.createChat(toBuddy, null);
			chat.sendMessage(message);
		}
		catch (Exception e) {
			System.out.println("Manager.sendMessage: " + e.toString());
		}
	}
	
}
