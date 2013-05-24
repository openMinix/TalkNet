package client.Entities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.*;
import client.Windows.*;

public class ConferenceInvitationListener implements InvitationListener {

    public void invitationReceived(final Connection conn, final String room, final String inviter, String reason, String password, Message message) {
        // Reject the invitation
        System.out.println("Received invitation from user " + inviter + "   " + room);
        final JDialog popup = new JDialog();
        JLabel action = new JLabel(inviter + " invites you to join conference.");
        JButton accept = new JButton(" Accept ");
        JButton decline = new JButton(" Decline ");
        
        JPanel general = new JPanel(new GridLayout(2, 1)); 
        JPanel p1 = new JPanel(new FlowLayout());
        JPanel p2 = new JPanel(new FlowLayout());
        popup.add(general);
        general.add(p2);
        general.add(p1);
        p2.add(action);
        p1.add(accept);
        p1.add(decline);
        
        popup.setSize(500, 100);
        popup.setTitle("TalkNet - Invitation");
        popup.setVisible(true);
        popup.setResizable(false);
        
         accept.addActionListener(new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //TODO --  Creare fereastra conference
                ConferenceFrame cf = new ConferenceFrame(room, false);
                popup.dispose();
                
            }
            
        });
         
         decline.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                MultiUserChat.decline(conn, room, inviter, "Pa!");
                //TODO -- add invitation rejection Listener
                popup.dispose();
                //TODO -- send info message to inviter
            }
             
         });
        //MultiUserChat.decline(conn, room, inviter, "I'm busy right now");
        //ConferenceManager cm = new ConferenceManager(room);
        //cm.joinRoom("lili");
        //cm.sendMessage("portocale");

    }

}
