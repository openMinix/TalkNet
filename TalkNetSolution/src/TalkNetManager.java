
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

public class TalkNetManager {
    
    private String server;					//address to the server
    private int port;						//where runs your XMPP server
    
    private ConnectionConfiguration config;		//configuration of the connection attributes
    private XMPPConnection connection;			//xmpp connection with server
    
    public TalkNetManager(String server, int port) {
        this.server = server;
        this.port = port;
    }
    
    /* init a connection with server */
    public void init() throws XMPPException {
        
        System.out.println(String.format("Init connection to server " + server + " port " + port));
        
        SmackConfiguration.setPacketReplyTimeout(500);
        
        // create config
        config = new ConnectionConfiguration(server, port);
        config.setSASLAuthenticationEnabled(false);			//for testing
        config.setSecurityMode(SecurityMode.disabled);		//for testing
        
        connection = new XMPPConnection(config);
        connection.connect();								//create connection
        
        if(connection.isConnected())
        	System.out.println("Success");
        else
        	System.out.println("Fail");
    }
    
    /* login using connection */
    public void loginCredentials(String username, String password) throws XMPPException {
        if (connection!=null && connection.isConnected()) {
            connection.login(username, password);
        }
    }

    /*  set your online status using a Presence class */
    public void setStatus(boolean available, String status) {
        
        Presence.Type type = available? Type.available: Type.unavailable;
        Presence presence = new Presence(type);
        
        presence.setStatus(status);
        connection.sendPacket(presence);
        
    }
    
    /* destroy the connection with server */
    public void destroy() {
        if (connection!=null && connection.isConnected()) {
            connection.disconnect();
        }
    }
}