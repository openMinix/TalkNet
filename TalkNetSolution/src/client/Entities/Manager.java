package client.Entities;

import java.awt.Toolkit; 
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;


import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;

import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.pubsub.PresenceState;

import client.Windows.ChatFrame;



//alext - manager between display, and backend
public class Manager implements PropertyChangeListener  {

	private ChatManager chatManager;
	private ChatManagerListener messageListener;
	private FileTransferManager transferManager;
	private TransferListener transferListener;
	private Roster roster;
	public String data[] = null;
	public HashMap<String, ChatFrame> chatWindows = null;
	private int nr_friends;
	private ProgressMonitor progressMonitor;

	
	private ProgressTask task;
	 

	//alext - manager singleton
	private static Manager instance = null;
	private Manager() {
		//alext - defeat instances
		roster = ConnectionManager.connection.getRoster();
		roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
		
		chatManager = ConnectionManager.connection.getChatManager();
		messageListener = new ChatListener();
		chatManager.addChatListener(messageListener);
		
		transferManager = new FileTransferManager( ConnectionManager.connection );
		FileTransferNegotiator.setServiceEnabled(ConnectionManager.connection, true);
		transferListener = new TransferListener();
		transferManager.addFileTransferListener( transferListener );
		
		chatWindows = new HashMap<String, ChatFrame>();
		data = new String[10000];//fail
		nr_friends = 0;
		
	
		PacketFilter filter = new PacketTypeFilter( Presence.class);
		
		PacketListener myListener = new PacketListener() {
	        public void processPacket(Packet packet) {
	        	
	        	Presence p = (Presence) packet;
	        	String from = p.getFrom();
	        	
	        	if ( p.getType() == Presence.Type.subscribe )
	        	{
	        		System.out.println("Primite subscribe");
	        		int n= JOptionPane.showConfirmDialog(
	        			    null,
	        			    "Would you like to add " + p.getFrom() + " ?",
	        			    "Incoming friend!",
	        			    JOptionPane.YES_NO_OPTION);
	        		
	        		if ( n == JOptionPane.YES_OPTION )
	        		{   
	        			   Presence response = new Presence(Presence.Type.subscribed);
	                       response.setTo( from );
	                       ConnectionManager.connection.sendPacket(response);
	        		} else
	        		{
	        			Presence response = new Presence(Presence.Type.unsubscribed);
	                    response.setTo( from );
	                    ConnectionManager.connection.sendPacket(response);
	        		}
	        	} else if ( p.getType() == Presence.Type.subscribed )
	        	{
	        		System.out.println("Primite DsubscribeD");
	        		Presence response = new Presence(Presence.Type.available);
	                response.setTo( from );
	                ConnectionManager.connection.sendPacket(response);
	                
	                Manager manager = Manager.getManager();
	                
	        		System.out.println("add: " + from);
	        		manager.data[nr_friends] = from.split(":")[0];
	        		nr_friends ++;
	        	} else if ( p.getType() == Presence.Type.available)
	        	{
	        		System.out.println("Primrire av");
	        	} else 
	        	{
	        		System.out.println("ERROR Primire " + p.getType());
	        	}
	        }
	    };
	    
	// Register the listener.
	    ConnectionManager.connection.addPacketListener(myListener, filter);
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
				System.out.println("mortii ma-tii");
			System.out.println("Apelez create entry");
			roster.createEntry(user, name, null);
		}
		catch (Exception e) {
			System.out.println("Manager.Exception.createEntry: " + e.toString());
		}
	}
	
	
	public void createEntry(String user, String name, String [] groups) throws XMPPException {
        // Create and send roster entry creation packet.
        RosterPacket rosterPacket = new RosterPacket();
        rosterPacket.setType(IQ.Type.SET);
        RosterPacket.Item item = new RosterPacket.Item(user, name);
       
        rosterPacket.addRosterItem(item);
        // Wait up to a certain number of seconds for a reply from the server.
        PacketCollector collector = ConnectionManager.connection.createPacketCollector(
                new PacketIDFilter(rosterPacket.getPacketID()));
        ConnectionManager.connection.sendPacket(rosterPacket);
        IQ response = (IQ)collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        if (response == null) {
        	System.out.println("Niciun raspuns");
            throw new XMPPException("No response from the server.");
        }
        // If the server replied with an error, throw an exception.
        else if (response.getType() == IQ.Type.ERROR) {
        	System.out.println("Raspuns eroare");
            throw new XMPPException(response.getError());
        }
        
        System.out.println("Raspuns bun");
        collector.cancel();

        // Create a presence subscription packet and send.
        Presence presencePacket = new Presence(Presence.Type.subscribe);
        presencePacket.setTo(user);
        ConnectionManager.connection.sendPacket(presencePacket);
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
	
	public void sendFile ( File file, String friend)
	{
	    OutgoingFileTransfer transfer = transferManager.
	    		createOutgoingFileTransfer(friend);
		
	      // Send the file
	    try {
	    		System.out.println("Sunt " +FileTransferNegotiator.isServiceEnabled(ConnectionManager.connection));
	    		System.out.println("Voi transfera!");
	    		transfer.sendFile(file, "You won't believe this!");
			
	    		 progressMonitor = new ProgressMonitor( null,
                         "Sending file ...",
                         "", 0, 100);
	    		 
	    		 progressMonitor.setProgress(0);
	    		 task = new ProgressTask( transfer );
	    		 task.addPropertyChangeListener(this);
	    		 task.execute();
	    		 
	    		
		} catch (XMPPException e) {
			System.out.println("Send file exception." + e.toString());
			e.printStackTrace();
		}

	}
	
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
            		String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
            
                } else {
            
                }
            }
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
		
		Manager manager = Manager.getManager();
	    System.out.println("createEntry: " + name);
	    manager.clean();//alext - clean manager between new login
	    
	    try {
			manager.createEntry(name +"@127.0.0.1" , name, null);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
