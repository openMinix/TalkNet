package client.Entities;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;

import client.Windows.ChatFrame;


//alext - manager between display, and backend
public class Manager {

	private ChatManager chatManager;
	private ChatManagerListener messageListener;
	private Roster roster;
	public String data[] = null;
	public HashMap<String, ChatFrame> chatWindows = null;
	private int nr_friends;

	//alext - manager singleton
	private static Manager instance = null;
	private Manager() {
		//alext - defeat instances
		roster = ConnectionManager.connection.getRoster();
		chatManager = ConnectionManager.connection.getChatManager();
		messageListener = new ChatListener();
		chatManager.addChatListener(messageListener);
		chatWindows = new HashMap<String, ChatFrame>();
		data = new String[10000];//fail
		nr_friends = 0;
	}
	
	//alext - return always the same instance
	public static Manager getManager() {
		if ( instance == null ) {
			instance = new Manager();
			return instance;
		}
		else
			return instance;
	}
	
	//alext - maybe called on event ????
	
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
	
	//alext - add user in roster's user authed
	public void createEntry(String user, String name ) {
		try {
			if ( roster == null )
				System.out.println("mortii matii");
			roster.createEntry(user, name, null);
		}
		catch (Exception e) {
			System.out.println("Manager.Exception.createEntry: " + e.toString());
		}
	}
	
	//alext - send a message to toBuddy
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
	
	//alext - return friends of user authed
	//alext - TODO - bind with roster
	public String[] getFriends() {
		String[] myData = new String[nr_friends+1];
		for ( int i = 0 ; i < nr_friends ; i ++ )
			myData[i] = data[i];
		myData[nr_friends] = null;
		return myData;
	}
	
	//alext - TODO - bind with roster
	public void addFriend( String name ) {
		System.out.println("add: " + name);
		this.data[nr_friends] = name;
		nr_friends ++;
	}
	
	// alext  - TODO - bind with roster
	public void deleteFriend (String name ) {
		int pos = -1;
		for (int i = 0 ; i < nr_friends ; i ++)  {
			if ( name.compareTo(data[i]) == 0 ) {
				pos = i;
			}
		}
		for ( int i = pos ; i < nr_friends - 1; i ++)
			data[i] = data[i+1];
		nr_friends--;
		data[nr_friends] = null;
	}
	
	//alext - very important
	//alext - clean resources before login after logout
	public void clean() {
		roster = ConnectionManager.connection.getRoster();
		chatManager = ConnectionManager.connection.getChatManager();
		messageListener = new ChatListener();
		chatManager.addChatListener(messageListener);
		chatWindows = new HashMap<String, ChatFrame>();
		data = new String[10000];//fail
		nr_friends = 0;
	}
	
}
