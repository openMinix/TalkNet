package client.Entities;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;

import client.Utils.Parameters;

//alext - create connection to server
public class ConnectionManager {
	
	private ConnectionConfiguration config;		//configuration of the connection attributes
	static public XMPPConnection connection;			//xmpp connection with server
	
	private Register reg;
	private Login login;
	private Logout logout;
	
	public ConnectionManager() {
        SmackConfiguration.setPacketReplyTimeout(500);
        
        // create config
        config = new ConnectionConfiguration(Parameters.server, Parameters.port);
        config.setSASLAuthenticationEnabled(true);			//for testing
        config.setSecurityMode(SecurityMode.required);		//for testing
        
        try {
        	connection = new XMPPConnection(config);
        	connection.connect();								//create connection
        }
        catch (Exception e) {
        	System.out.println("Exception connect: " + e.toString());
        }
        	
        if(connection.isConnected())
        	System.out.println("Success");
        else
        	System.out.println("Fail");

	}
}
