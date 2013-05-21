package client.Entities;

import client.Windows.ConferenceFrame;
import org.jivesoftware.smackx.muc.*;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.*;
import java.util.Collection;
import java.util.Iterator;

public class ConferenceManager {

	private MultiUserChat muc;
        
        public static String userID;
	
	public ConferenceManager(String roomName, final ConferenceFrame frame) {
		/* Only creats the MultiUserChat. */
		/* A user MUST instantiate this class in order to be able to listen for
		 * invites
		 */
		muc = new MultiUserChat(ConnectionManager.connection, roomName);
		/* Listen for invitations. */
		muc.addMessageListener(new PacketListener() {
			public void processPacket(Packet packet) {
                                System.out.println("get message");
                                if(packet instanceof Message) {
                                    Message message = (Message)packet;
                                    System.out.println(message.getBody());
                                    String user = packet.getFrom().substring(packet.getFrom().indexOf("/") + 1);
                                    if (!user.equals(ConferenceManager.userID))
                                        frame.displayMessage(user, message.getBody());
                                }
				
			}
		}); 
                muc.addParticipantListener(new PacketListener() {

                            @Override
                            public void processPacket(Packet packet) {
                                if (packet instanceof Presence) {
                                    Presence presence = (Presence) packet;
                                    String user = packet.getFrom().substring(packet.getFrom().indexOf("/") + 1);
                                    System.out.println("got presence " + packet.getFrom());
                                    if (presence.getType() == Presence.Type.available) {
                                        frame.addParticipant(user);
                                        frame.displayMessage("System", user + " has joined the conference");
                                        System.out.println("ok");
                                    }
                                    else
                                        if (presence.getType() == Presence.Type.unavailable &&
                                                !user.equals(ConferenceManager.userID)) {
                                        frame.removeParticipant(user);
                                        frame.displayMessage("System", user + " has quit the conference");
                                        System.out.println("he's out");
                                        }
                                    
                                
                            }
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
                try {
                    muc.grantMembership(user);
                    muc.invite(user, reason);
                }
                catch (XMPPException e) {
                    System.out.println("Invite User: " + e.toString());
                }
	}
	
	public void joinRoom() {
                /* Join room and listen for potential changes in presence. */
		try {
			muc.join(userID);
                        
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
        
        public void leaveRoom() {
            muc.leave();
        }
        
        public Iterator<String> getMembers() {
                Iterator<String> it = null;
                //try {
                    it = muc.getOccupants();
                //}
                //catch (XMPPException e) {
                  //      System.out.println("Member get exception: " + e.toString());
                //}
                
                return it;
        }
}
