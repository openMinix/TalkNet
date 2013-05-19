package client.Entities;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.*;

public class ConferenceInvitationListener implements InvitationListener {

    public void invitationReceived(Connection conn, String room, String inviter, String reason, String password, Message message) {
        // Reject the invitation
        System.out.println("Received invitation from user " + inviter + "   " + room);
        //MultiUserChat.decline(conn, room, inviter, "I'm busy right now");
        ConferenceManager cm = new ConferenceManager(room);
        cm.joinRoom("lili");
        cm.sendMessage("portocale");

    }

}
