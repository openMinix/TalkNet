package client.Entities;

import org.jivesoftware.smackx.muc.*;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.Message;

public class ConferenceManager {

	private MultiUserChat muc;
	
	public ConferenceManager(String roomName) {
		/* Only creats the MultiUserChat. */
		/* A user MUST instantiate this class in order to be able to listen for
		 * invites
		 */
		muc = new MultiUserChat(ConnectionManager.connection, roomName);
		/* Listen for invitations. */
		MultiUserChat.addInvitationListener(ConnectionManager.connection, new InvitationListener()
			{
				public void invitationReceived(Connection conn, String room, String inviter, String reason, String password, Message message) {
					// Reject the invitation
					System.out.println("Received invitation from user " + inviter);
					MultiUserChat.decline(conn, room, inviter, "I'm busy right now");
				}
			});
	}
	
	public void createRoom(String username) {
		try {
			muc.create(username);
		}
		catch (XMPPException e) {
			System.out.println("Manager.Exception: " + e.toString());
		}
	}
	
	public void inviteUser(String user, String reason) {
		muc.invite(user, reason);	
	}
	
}
