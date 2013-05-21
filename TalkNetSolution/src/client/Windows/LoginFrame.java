package client.Windows;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import client.Entities.ConnectionManager;
import client.Entities.Login;
import client.Entities.Manager;
import client.Entities.Register;
import client.Entities.ConferenceManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bianca
 */
public class LoginFrame extends JFrame{
    JLabel appLabel;
    JLabel user;
    JLabel password;
    JLabel noAccount;
    TextField jt1;
    TextField jt2;
    JButton signup, login;
    public static LoginFrame loginFrame;
    
     public LoginFrame(){
        setTitle("TalkNet");
        
        appLabel = new JLabel("TalkNet");
        appLabel.setFont(new Font("Arial", Font.BOLD, 30));
        user = new JLabel("Username");
        password = new JLabel("Password");
        noAccount = new JLabel("Join us! Get a new TalkNet ID...");
        jt1 = new TextField(20);
        jt2 = new TextField(20);
        jt2.setEchoChar('*');
        signup = new JButton("Sign up");
        login = new JButton("Login");
        
        JPanel logo = new JPanel(new FlowLayout());
        JPanel credentials = new JPanel(new GridLayout(2,1));
        JPanel userPanel = new JPanel(new FlowLayout());
        JPanel passPanel = new JPanel(new FlowLayout());
        JPanel loginPanel = new JPanel(new FlowLayout());
        JPanel p1 = new JPanel(new FlowLayout());
        JPanel p2 = new JPanel(new FlowLayout());
        JPanel signupAction = new JPanel(new BorderLayout());
        JPanel general = new JPanel(new GridLayout(5,1));
        general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        p1.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        p2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        signupAction.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        loginPanel.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        passPanel.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        userPanel.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        credentials.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        logo.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        
        
        add(general);
        general.add(logo);
        general.add(credentials);
        general.add(loginPanel);
        general.add(signupAction);
        credentials.add(userPanel);
        credentials.add(passPanel);
        logo.add(appLabel);
        userPanel.add(user);
        userPanel.add(jt1);
        passPanel.add(password);
        passPanel.add(jt2);
        loginPanel.add(login);
        signupAction.add(p1, BorderLayout.CENTER);
        signupAction.add(p2, BorderLayout.SOUTH);
        p1.add(noAccount);
        p2.add(signup);
        
        signup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                final JDialog signupFrame = new JDialog();
                signupFrame.setTitle("Sign up to TalkNet!");
                signupFrame.setSize(450, 180);
                signupFrame.setResizable(false);
                
                JLabel passLabel = new JLabel("Choose a password: ");
                JLabel rePassLabel = new JLabel("Retype password: ");
                JLabel user = new JLabel("Select Username:");
                final TextField tf1 = new TextField(20);
                final TextField tf2 = new TextField(20);
                final TextField tf3 = new TextField(20);
                tf2.setEchoChar('*');
                tf3.setEchoChar('*');
                JButton getAccount = new JButton(" Sign Up! ");
                

                JPanel general = new JPanel(new GridLayout(4, 1));
                general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel p3 = new JPanel(new FlowLayout());
                JPanel p4 = new JPanel(new FlowLayout());
                p1.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                p2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                p3.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                p4.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
                
                signupFrame.add(general);
                general.add(p1);
                general.add(p2);
                general.add(p3);
                general.add(p4);
                
                p1.add(user);
                p1.add(tf1);
                p2.add(passLabel);
                p2.add(tf2);
                p3.add(rePassLabel);
                p3.add(tf3);
                p4.add(getAccount);
                
                signupFrame.setVisible(true);
                signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                
                    getAccount.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String newUser = tf1.getText();
                        String newPass = tf2.getText();
                        String passCopy = tf3.getText();
                        
                        //alext - register new user to server
                        if ( newUser.compareTo("") != 0 && newPass.compareTo("")!= 0
                        	&& passCopy.compareTo("")!= 0 && newPass.compareTo(passCopy) == 0) {
                        	signupFrame.dispose();
                        	
                        	ConnectionManager cm = new ConnectionManager();
                        	Register newUserReg = new Register(newUser, newPass);
                    	
                        }
                        
                    }
                    
                }); 
                
            }
            
        });
        
        loginFrame = this;
        
        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String userID = jt1.getText();
                String passwd = jt2.getText();
                
                /* Add userID for further use in ConferenceManager */
                ConferenceManager.userID = userID;
                
                // TODO - Validate credentials
                
                if (userID.equals(""))
                    JOptionPane.showMessageDialog(loginFrame,
                        "No username specified!",
                        "TalkNet warning",
                          JOptionPane.WARNING_MESSAGE);
                
                else if (passwd.equals(""))
                    JOptionPane.showMessageDialog(loginFrame,
                        "No password specified!",
                        "TalkNet warning",
                          JOptionPane.WARNING_MESSAGE);
                
                //alext - launch mainframe
                if ( userID.compareTo("") != 0 && passwd.compareTo("") != 0 ) {
                	Point loc = loginFrame.getLocation();
                	//
                        loginFrame.hide();
                	
                	MainFrame mf = new MainFrame(userID, passwd);
                	mf.setLocation(loc);
                }
                
            }
            
        });
        
        setSize(340,500);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
}