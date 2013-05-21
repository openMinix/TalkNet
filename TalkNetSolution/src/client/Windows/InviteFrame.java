/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Windows;

/**
 *
 * @author Bianca
 */

import client.Entities.ConferenceManager;
import client.Windows.ConferenceFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
 
public class InviteFrame extends JFrame {
 
        JPanel contentPane;
        JList list;
        JList list_1;
        DefaultListModel model;
        DefaultListModel model_1;
        InviteFrame invfr;
 
        /**
         * Create the frame.
         */
        public InviteFrame() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 450, 300);
                setVisible(true);
                setTitle("TalkNet - Conference");
                setResizable(false);
                
                invfr = this;
                contentPane = new JPanel();
                contentPane.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
                model = new DefaultListModel();
                model_1 = new DefaultListModel();
               
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setBounds(323, 37, 92, 222);
                contentPane.add(scrollPane);
               
                list_1 = new JList(model_1);
                scrollPane.setViewportView(list_1);
               
                JScrollPane scrollPane_1 = new JScrollPane();
                scrollPane_1.setBounds(27, 37, 92, 222);
                contentPane.add(scrollPane_1);
               
                model.addElement("bubulina");
                model.addElement("tina");
                
                list = new JList(model);
                scrollPane_1.setViewportView(list);
               
                JLabel lblBuddies = new JLabel("Buddies");
                lblBuddies.setBounds(39, 10, 70, 15);
                contentPane.add(lblBuddies);
               
                JLabel lblInvitees = new JLabel("Invitees");
                lblInvitees.setBounds(337, 10, 70, 15);
                contentPane.add(lblInvitees);
               
                JButton btnAdd = new JButton("Add >>");
                btnAdd.setBounds(162, 58, 117, 25);
                contentPane.add(btnAdd);
               
                JButton button = new JButton("<< Remove");
                button.setBounds(162, 106, 117, 25);
                contentPane.add(button);
               
                JButton btnInvite = new JButton("Invite!");
                btnInvite.setBounds(162, 206, 117, 25);
                contentPane.add(btnInvite);
                
                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        
                        String selected = (String)list_1.getSelectedValue();
                        model_1.removeElement(selected);
                        
                    }
                    
                });
                
                btnAdd.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        
                        String selected = (String)list.getSelectedValue();
                        model_1.addElement(selected);
                        
                    }
                    
                });
                
                btnInvite.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Not implemented yet!");
                        int i;
                        StringBuffer roomName = new StringBuffer(ConferenceManager.userID + "@conference.127.0.0.1");
            
                        ConferenceFrame cf = new ConferenceFrame(roomName.toString(), true);
                        
                        for (i = 0; i < model_1.size(); i++) {
                            StringBuffer element = new StringBuffer((String)model_1.getElementAt(i));
                            element.append("@127.0.0.1/Smack");
                            System.out.println(element.toString());
                            cf.getConferenceManager().inviteUser(element.toString(), "sadasas");
                        }
                         
                        invfr.dispose();
                    }
                    
                });
        
        }
}


