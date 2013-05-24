package client.Entities;

import java.awt.Toolkit; 
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;


import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
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
import client.Windows.MainFrame;



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
		
		roster.addRosterListener(new RosterListener() {
		    // Ignored events public void entriesAdded(Collection<String> addresses) {}
		    public void entriesDeleted(Collection<String> addresses) {}
		    public void entriesUpdated(Collection<String> addresses) {}
		    public void presenceChanged(Presence presence) {
		    	
		    System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
		    MainFrame.updatePanel();
		    }
			@Override
			public void entriesAdded(Collection<String> arg0) {
				System.out.println("aDDED " + arg0);
			}
		});
		
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
		
	
		PacketFilter filter = new PacketTypeFilter( Presence.class );
		
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
	                       
	                       Manager manager = Manager.getManager();
	                       
	                       String name = from.split("@")[0];
	                       
	                       if ( !Arrays.asList( manager.data).contains(name) )
	                       {
	                    	   manager.addFriend(name);
	                       }
	        		} else
	        		{
	        			Presence response = new Presence(Presence.Type.unsubscribed);
	                    response.setTo( from );
	                    ConnectionManager.connection.sendPacket(response);
	        		}
	        	} else if ( p.	getType() == Presence.Type.subscribed )
	        	{
	        		System.out.println("Primite DsubscribeD");
	        		Presence response = new Presence(Presence.Type.available);
	                response.setTo( from );
	                ConnectionManager.connection.sendPacket(response);
	                
	                Manager manager = Manager.getManager();
	                
	        		System.out.println("add: " + from);
	        		manager.data[nr_friends] = from.split("@")[0];
	        		nr_friends ++;
	        		MainFrame.updatePanel();
	        		
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
		
		/* Get all entries in roster. */
        Collection<RosterEntry> entries = roster.getEntries();
        Iterator<RosterEntry> it = entries.iterator();

        int i;

        SortedSet<String> list = new TreeSet<String>();

        /* Get friends' names. */
        while (it.hasNext())
            list.add(it.next().getUser().split("@")[0]);

        String[] friends = new String[list.size()];

        for (i = 0; i < list.size(); i++) {
        	
        	Presence p = roster.getPresence(list.first() + "@127.0.0.1");
        	String profile = list.first() ;
        	if( p != null )
        		profile += " " + (p.getMode() !=null? p.getMode():"") + " \"" + p.getStatus() + "\""; 
            friends[i] = list.first();
            list.remove(friends[i]);
            
            friends[i] = profile;
        }

        return friends;

	}
	
public TreeSet<String> friends() {
		
		/* Get all entries in roster. */
        Collection<RosterEntry> entries = roster.getEntries();
        Iterator<RosterEntry> it = entries.iterator();

        int i;

        TreeSet<String> list = new TreeSet<String>();

        /* Get friends' names. */
        while (it.hasNext())
            list.add(it.next().getUser().split("@")[0]);
        
        return list;

	}
	
	//alext - TODO - bind with roster
	public void addFriend( String name ) {
		
		StringBuffer userID = new StringBuffer(name);
        userID.append("@127.0.0.1");
        
    	Manager.getManager().createEntry(name +"@127.0.0.1" , name);

    //    Presence subscribe = new Presence(Presence.Type.subscribe);
   //     subscribe.setTo(userID.toString());
    //    ConnectionManager.connection.sendPacket(subscribe);
        
	}
	
	// alext  - TODO - bind with roster
	public void deleteFriend (String name ) {
		/* Set user ID. */
        StringBuffer userID = new StringBuffer(name);
        userID.append("@127.0.0.1");

        /* Get all entries in roster. */
        Collection<RosterEntry> friends = roster.getEntries();
        Iterator<RosterEntry> it = friends.iterator();

        while (it.hasNext()) {
            RosterEntry entry = it.next();
            System.out.println("got " + entry.getUser() + " and " + userID.toString());
            String username = entry.getUser().split("@")[0];
            if (name.equals(username))
                try {
                    roster.removeEntry(entry);
                    System.out.println("deleted " + userID);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
        }

        /* Unsubscribe. */
        Presence unsubscribed = new Presence(Presence.Type.unsubscribed);
        unsubscribed.setTo(userID.toString());
        ConnectionManager.connection.sendPacket(unsubscribed);

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
