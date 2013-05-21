/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Windows;

import javax.swing.*;
import client.Entities.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Bianca
 */


/*public class ConferenceFrame extends JFrame{
    
    JTextArea messageArea;
    JTextArea convArea;
    JList people;
    JButton send;
    
    ConferenceManager cm;
    
    public ConferenceFrame(String roomName){
        
        cm = new ConferenceManager(roomName);
        cm.joinRoom("Gigel");  //TODO  -- add real username
                
        convArea = new JTextArea(13,40);
        convArea.setMaximumSize(new Dimension(13,  40));
        convArea.setEditable(false);
        
        messageArea  = new JTextArea(5, 33);
        
        send = new JButton("Send");
        
        String []data =  {"ana", "maria", "ion"};    //TODO - get conference people   
        people = new JList(data);
        
        JScrollPane jsp1 = new JScrollPane(convArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane jsp2 = new JScrollPane(messageArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane jsp3 = new JScrollPane(people, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel general = new JPanel(new BorderLayout());
        JPanel chat = new JPanel(new GridBagLayout());
        JPanel messPanel = new JPanel(new FlowLayout());
        
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        
        add(general);
        general.add(chat, BorderLayout.CENTER);
        general.add(jsp3, BorderLayout.EAST);
        
        c1.gridx = 0;
        c1.gridy = 0;
        chat.add(jsp1, c1);
        
        c2.gridx = 0;
        c2.gridy = 400;
        chat.add(messPanel, c2);
        
        messPanel.add(jsp2);
        messPanel.add(send);
        
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("TalkNet - " + roomName);
        
        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String message = messageArea.getText();
               
                //clear message Area;
                messageArea.setText("");
                
                //update coversation Area
                convArea.setText(convArea.getText() + "\n" + 
                         "Me (" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) 
                        + "): " + message);
                //cm.sendMessage(message);
            
            }
            
        });
    }
    
}

*/

import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
 
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smackx.muc.*;

public class ConferenceFrame extends JFrame {
 
        JPanel contentPane;
        JTextArea textField;
        JList list;
        JTextArea textField_1;
        JButton btnInviteBuddy;
        JButton btnSetTopic;
        JButton btnNewButton_1;
        JScrollPane scrollPane;
        JScrollPane scrollPane_1;
        JScrollPane scrollPane_2;
        
        ConferenceManager cm;
        Iterator<String> members;
        ArrayList<String> participants = new ArrayList<String>();
        final DefaultListModel model;
        
        ConferenceFrame cfr;
        
        public ConferenceFrame(String roomName, boolean initiated) {
                
            
                cm = new ConferenceManager(roomName, this);
                
                if (initiated) {
                    cm.createRoom(ConferenceManager.userID);
                }
                else
                    cm.joinRoom();  //TODO  -- add real username
            
                members = cm.getMembers();
                
                while (members.hasNext()) {
                    String name = members.next();
                    System.out.println("From MEMBERS " + name);
                    participants.add(name.substring(name.indexOf("/") + 1));
                } 
                
                setBounds(new Rectangle(65, 24, 500, 400));
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setResizable(false);
                setVisible(true);
//              setBounds(65, 24, 500, 400);
                contentPane = new JPanel();
                contentPane.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                contentPane.setForeground(Color.RED);
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
                JButton btnNewButton = new JButton("Send");
                btnNewButton.setBounds(305, 313, 75, 25);
                contentPane.add(btnNewButton);
               
                model = new DefaultListModel();
               for (String participant : participants) {
                   synchronized(model) {
                       if (!model.contains(participant))
                        model.addElement(participant);
                   }
               }
               // model.addElement(ConferenceManager.userID);
                
              
                  
                                JLabel lblBuddies = new JLabel("Buddies");
                                lblBuddies.setBounds(409, 22, 66, 25);
                                contentPane.add(lblBuddies);
                               
                                btnInviteBuddy = new JButton("Invite buddy");
                                btnInviteBuddy.setMargin(new Insets(2, 5, 2, 5));
                                btnInviteBuddy.setAlignmentY(0.2f);
                                btnInviteBuddy.setBounds(12, 10, 103, 25);
                                contentPane.add(btnInviteBuddy);
                               
                                btnSetTopic = new JButton("Set topic");
                                btnSetTopic.setBounds(149, 10, 103, 25);
                                contentPane.add(btnSetTopic);
                               
                                btnNewButton_1 = new JButton("Quit");
                                btnNewButton_1.setBounds(286, 10, 94, 25);
                                contentPane.add(btnNewButton_1);
                               
                                scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                                scrollPane.setBounds(12, 293, 281, 66);
                                contentPane.add(scrollPane);
                               
                                textField = new JTextArea();
                                scrollPane.setViewportView(textField);
                                //textField.setColumns(10);
                                textField.setCaretPosition(0);
                               
                                scrollPane_1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                                scrollPane_1.setBounds(12, 47, 368, 234);
                                contentPane.add(scrollPane_1);
                               
                                textField_1 = new JTextArea();
                                textField_1.setEditable(false);
                                scrollPane_1.setViewportView(textField_1);
                                //textField_1.setColumns(10);
                                textField_1.setCaretPosition(0);
                                               
                                                scrollPane_2 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                                scrollPane_2.setBounds(392, 47, 94, 312);
                                                contentPane.add(scrollPane_2);
                               
                                                list = new JList(model);
                                                scrollPane_2.setViewportView(list);
                                                list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
                                               
                                                list.setVisible(true);
                                                
        cfr = this;
                btnNewButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String message = textField.getText();

                        //clear message Area;
                        textField.setText("");

                        //update coversation Area
                        textField_1.setText(textField_1.getText() + "\n" + 
                                 "Me (" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) 
                                + "): " + message);
                        cm.sendMessage(message);

                    }

                });
                
                btnNewButton_1.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        cm.leaveRoom();
                        cfr.dispose();
                    }

                });
               
        }
        
        public void displayMessage(String buddy, String message ) {
    	System.out.println("budy: " + buddy);
    	this.show();
    	textField_1.setText(textField_1.getText() + "\n" + 
            buddy + " (" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) 
            + "): " + message);
        }
        
        public ConferenceManager getConferenceManager() {
            return cm;
        }
        
        public void addParticipant(String user) {
            System.out.println("Called " + user);
            participants.add(user);
            synchronized(model) {
                if (!model.contains(user))
                   model.addElement(user);
            }
        }
        
        public void removeParticipant(String user) {
            participants.remove(user);
            synchronized(model) {
                model.removeElement(user);
            }
            
        }
}
