package client.Entities;


import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
	
//alext - regiser new user to server
public class Register {

	AccountManager am;
    
	public Register(String newuser, String password) {
	
        //try create an accountmanger and create new user
        try {
        	am = new AccountManager(ConnectionManager.connection);
        	am.createAccount(newuser, password);
        }
        catch (Exception e) {
        	System.out.println("Exception account manager: " + e.toString());
        }
	}
}
