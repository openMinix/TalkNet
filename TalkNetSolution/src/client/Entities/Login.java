package client.Entities;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;


//alext - login user to server
public class Login {
	
        
    /* login using connection */
    public void loginCredentials(String username, String password) throws XMPPException {
        if (ConnectionManager.connection!=null && ConnectionManager.connection.isConnected()) {
            ConnectionManager.connection.login(username, password);
        }
    }

    /*  set your online status using a Presence class */
    public void setStatus(boolean available, String status) {
        
        Presence.Type type = available? Type.available: Type.unavailable;
        Presence presence = new Presence(type);
        
        presence.setStatus(status);
        ConnectionManager.connection.sendPacket(presence);
        
    }
}
