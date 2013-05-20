package client.Entities;

import org.jivesoftware.smackx.muc.*;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.*;

public class ConferenceManager {

	private MultiUserChat muc;
	
	public ConferenceManager(String roomName) {
		/* Only creats the MultiUserChat. */
		/* A user MUST instantiate this class in order to be able to listen for
		 * invites
		 */
		muc = new MultiUserChat(ConnectionManager.connection, roomName);
		/* Listen for invitations. */
		muc.addMessageListener(new PacketListener() {
			public void processPacket(Packet packet) {
				Message message = muc.nextMessage();
				System.out.println(message.getBody());
			}
		}); 
	}
	
	public void createRoom(String username) {
		try {
			muc.create(username);
			muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		}
		catch (XMPPException e) {
			System.out.println("Manager.Exception: " + e.toString());
		}
	}
	
	public void inviteUser(String user, String reason) {
		muc.invite(user, reason);	
	}
	
	public void joinRoom(String username) {
		try {
			muc.join(username);
		}
		catch (XMPPException e) {
			System.out.println("Join Exception: " + e.toString());
		}
	}
	
	public void sendMessage(String message) {
		try {
			muc.sendMessage(message);
		}
		catch (XMPPException e) {
			System.out.println("Send Message Exception: " + e.toString());
		}
	}
}
