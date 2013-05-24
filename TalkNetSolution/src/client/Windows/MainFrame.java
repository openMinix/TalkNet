package client.Windows;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.*;

import client.Entities.*;
import static client.Windows.LoginFrame.loginFrame;
import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bianca
 */

public class MainFrame extends JFrame {

	//private JPanel contentPane;
        String userID;
        String passwd;
        Manager manager;


        public static JList friendList;
        JLabel hello;
        MenuBar menuBar;
        public static JScrollPane jsp;
        public static JPanel general;
        public static JFrame up;

	/**
	 * Create the frame.
	 */
	public MainFrame(String user, String passwd) {
            
                boolean isAuthed =  false;
                try {
                        //creates a connection with server
                        ConnectionManager cm = new ConnectionManager();

                        Thread.sleep(1000);

                        //Register reg = new Register("usertest3", "123456");
                        Login login1 = new Login();
                        login1.loginCredentials(user, passwd);
                        if ( ConnectionManager.connection.isAuthenticated() ) {
                                Manager.getManager().setStatus(true, "Avaiable");
                                isAuthed = true;
                        }

                        LoginFrame.loginFrame.hide();
                        // add listener for conference invitation
                        MultiUserChat.addInvitationListener(ConnectionManager.connection, new ConferenceInvitationListener() ); 

                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(loginFrame,
                          "Please make sure that your username and password are correct!",
                          "TalkNet error",
                            JOptionPane.ERROR_MESSAGE);


                }
                
                if(isAuthed == false ) {
                    LoginFrame.loginFrame.show();
                    return ;
                }

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 500);
		
		menuBar = new MenuBar();
		setMenuBar(menuBar);
		
		Menu talkNet = new Menu(" TalkNet ");
		menuBar.add(talkNet);
		
		MenuItem logout = new MenuItem(" LogOut ");
		talkNet.add(logout);
		
		talkNet.addSeparator();
		
		MenuItem exit = new MenuItem(" Exit ");
		talkNet.add(exit);
		
		Menu contacts = new Menu(" Contacts ");
		menuBar.add(contacts);
		
		MenuItem add = new MenuItem(" Add Friend ");
		contacts.add(add);
		
		contacts.addSeparator();
		
		MenuItem delete = new MenuItem(" Delete Friend ");
		contacts.add(delete);
		
		JSeparator separator_2 = new JSeparator();
		contacts.addSeparator();
		
		MenuItem ignore = new MenuItem(" Ignore Friend ");
		contacts.add(ignore);
		
		Menu conference = new Menu(" Conference ");
		menuBar.add(conference);
		
		MenuItem createConf = new MenuItem(" Create conference ");
		conference.add(createConf);
		
		Menu help = new Menu(" Help ");
		menuBar.add(help);
                
                MenuItem helpItem = new MenuItem(" Help ");
                help.add(helpItem);
                
                //add Action liteners on menu items
                logout.addActionListener(new ItemAction());
                exit.addActionListener(new ItemAction());
                add.addActionListener(new ItemAction());
                delete.addActionListener(new ItemAction());
                ignore.addActionListener(new ItemAction());
                createConf.addActionListener(new ItemAction());
                helpItem.addActionListener(new ItemAction());
               
                manager = Manager.getManager();
                setTitle("TalkNet");
                userID = user;
                this.passwd = passwd;
                
                
                friendList = new JList(manager.getFriends());

                friendList.addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent me) {

                    		
                    		if (friendList.getSelectedValue() != null) {
                    			String selectedValue = friendList.getSelectedValue().toString().split(" ")[0];
                    			
                    			if (manager.chatWindows.get(selectedValue )== null ){
                    				ChatFrame fr = new ChatFrame(selectedValue);
                    				manager.chatWindows.put(selectedValue, fr);
                    			}
                    			else 
                    				manager.chatWindows.get(selectedValue).show();
                    				System.out.println("map: " + selectedValue+". "+manager.chatWindows.size());
                    	}
                    }

                    @Override
                    public void mousePressed(MouseEvent me) { }

                    @Override
                    public void mouseReleased(MouseEvent me) { }

                    @Override
                    public void mouseEntered(MouseEvent me) { }

                    @Override
                    public void mouseExited(MouseEvent me) { }

                });
                
                /*ImageIcon image = new ImageIcon("glossy.png");
                JLabel imageLabel = new JLabel(image);
                imageLabel.setBounds(270, 24, 60, 60);
              */  
                
		general = new JPanel();
		general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		general.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(general);
		general.setLayout(null);
		
		JLabel lblHelloFriend = new JLabel("Hello, " + userID + "!");
		lblHelloFriend.setBounds(10, 24, 314, 33);
		lblHelloFriend.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
		general.add(lblHelloFriend);
		
		JTextField statusField = new JTextField("Set status here", 20);
		statusField.setBackground( Color.LIGHT_GRAY);
		statusField.setBounds(85, 60, 200, 22);
		Border b = new LineBorder( Color.darkGray, 3, false);
		statusField.setBorder(b);
		general.add(statusField);
		
		statusField.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				System.out.println( ConnectionManager.connection.getUser() );
				
				Roster roster = ConnectionManager.connection.getRoster();
				Presence p = roster.getPresence( ConnectionManager.connection.getUser());
				p.setStatus( event.getActionCommand());
				ConnectionManager.connection.sendPacket( p );
			}
		});
                ////general.add(imageLabel);		
		JLabel label = new JLabel("Buddies");
		label.setBounds(30, 84, 65, 24);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial Black", Font.BOLD, 13));
		general.add(label);
		
		jsp = new JScrollPane(friendList, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
                jsp.setBounds(30, 108, 275, 323);
		general.add(jsp);
                
        up = this;
        setVisible(true);
        setResizable(false);
	}
        
          
  
    public static void updatePanel() {
    	System.out.println("updatePanel: " + Manager.getManager().data[0]);
    	general.remove(jsp);
    	friendList.setListData(Manager.getManager().getFriends());
    	jsp = new JScrollPane(friendList, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
        jsp.setBounds(30, 108, 275, 323);
        general.add(jsp);
    	up.show();
    }
}

class ItemAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        if ( command.equals(" Add Friend ")){
            
            final JDialog addFrame = new JDialog();
            JLabel action = new JLabel("We're excited that you made a new friend! Type his/her ID to chat all day long.");
            JLabel frndID = new JLabel("Friend ID");
            final JTextField tf = new JTextField(20);
            JButton add = new JButton(" Add ");
            JButton cancel = new JButton(" Cancel ");
            
            JPanel general = new JPanel(new BorderLayout());
            JPanel simple1 = new JPanel(new FlowLayout());
            JPanel simple2 = new JPanel(new FlowLayout());
            general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple1.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            
            addFrame.add(general);
            general.add(action, BorderLayout.NORTH);
            general.add(simple1, BorderLayout.CENTER);
            general.add(simple2, BorderLayout.SOUTH);
            simple1.add(frndID);
            simple1.add(tf);
            simple2.add(add);
            simple2.add(cancel);
            
            add.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    System.out.println("." + tf.getText()+".");
                    Manager.getManager().addFriend(tf.getText());
                    addFrame.dispose();
                    MainFrame.updatePanel();
                }
            });

            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                   addFrame.dispose();
                   
                }
            
            });
            addFrame.setVisible(true);
            addFrame.setTitle("TalkNet");
            addFrame.setSize(500, 150);
            addFrame.setResizable(false);
            addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addFrame.pack();
            
            
        }
        
        if ( command.equals(" Delete Friend ")){
            final JDialog deleteFrame = new JDialog();
            JLabel action = new JLabel("Did someone upset you? Tell us his name and we'll make him gone.");
            JLabel frndID = new JLabel("Friend ID");
            final JTextField tf = new JTextField(20);
            JButton delete = new JButton(" Delete ");
            JButton cancel = new JButton(" Cancel ");
            
            JPanel general = new JPanel(new BorderLayout());
            JPanel simple1 = new JPanel(new FlowLayout());
            JPanel simple2 = new JPanel(new FlowLayout());
            general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple1.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            
            deleteFrame.add(general);
            general.add(action, BorderLayout.NORTH);
            general.add(simple1, BorderLayout.CENTER);
            general.add(simple2, BorderLayout.SOUTH);
            simple1.add(frndID);
            simple1.add(tf);
            simple2.add(delete);
            simple2.add(cancel);
            
            delete.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                	System.out.println("." + tf.getText()+".");
                    Manager.getManager().deleteFriend(tf.getText());
                    deleteFrame.dispose();
                    MainFrame.updatePanel();
                }
            });

            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                   deleteFrame.dispose();
                }
            
            });
            deleteFrame.setVisible(true);
            deleteFrame.setTitle("TalkNet");
            deleteFrame.setSize(400, 150);
            deleteFrame.setResizable(false);
            deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            deleteFrame.pack();
        }
        
        if ( command.equals(" Ignore Friend ")){
            final JDialog ignoreFrame = new JDialog();
            JLabel action = new JLabel("An irritating one, huh? Simply ignore his messages!\n\n");
            JLabel frndID = new JLabel("Friend ID");
            final JTextField tf = new JTextField(20);
            JButton ignore = new JButton(" Ignore ");
            JButton cancel = new JButton(" Cancel ");
            
            JPanel general = new JPanel(new BorderLayout());
            JPanel simple1 = new JPanel(new FlowLayout());
            JPanel simple2 = new JPanel(new FlowLayout());
            general.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple1.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            simple2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
            
            ignoreFrame.add(general);
            general.add(action, BorderLayout.NORTH);
            general.add(simple1, BorderLayout.CENTER);
            general.add(simple2, BorderLayout.SOUTH);
            simple1.add(frndID);
            simple1.add(tf);
            simple2.add(ignore);
            simple2.add(cancel);
            
            ignore.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    System.out.println(tf.getText());
                    
                    // TODO - IGNORA MESAJE actualizare lista prieteni GUI + server 
                    
                    ignoreFrame.dispose();
                }
            });

            cancel.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                   ignoreFrame.dispose();
                }
            
            });
            ignoreFrame.setVisible(true);
            ignoreFrame.setTitle("TalkNet");
            ignoreFrame.setSize(400, 150);
            ignoreFrame.setResizable(false);
            ignoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ignoreFrame.pack();
        }
        
        if ( command.equals(" LogOut ")){
            System.out.println("Log out");
            MainFrame.up.dispose();
            LoginFrame.loginFrame.show();
            Logout logOut = new Logout();
            logOut.destroy();
            //alext - after logout clean manager
            Manager.getManager().clean();
        }
        
        if ( command.equals(" Exit ")){
            System.out.println("exit");
            System.exit(0);
        }
        
        if ( command.equals(" Create conference ")){
            InviteFrame invf = new InviteFrame();
        }
        
        if ( command.equals(" Help ")){
            JOptionPane.showMessageDialog(ChatFrame.chat,
                "Having questions or problems?\nContact our account administrator at "
                    + "admin@talknet.ro",
                "Help",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
}
