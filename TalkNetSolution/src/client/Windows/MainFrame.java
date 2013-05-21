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
import org.jivesoftware.smackx.muc.*;

import org.jivesoftware.smackx.muc.MultiUserChat;

import client.Entities.*;
import static client.Windows.LoginFrame.loginFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bianca
 */
public class MainFrame extends JFrame {
    String userID;
    String passwd;
    Manager manager;
    
    
    public static JList friendList;
    JLabel hello;
    JLabel spaceBibi;
    MenuBar menu;
    public static JScrollPane jsp;
    public static JPanel general;
    public static JFrame up;
    
    public MainFrame(String user, String passwd){
    	
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
    	
    	menu = new MenuBar();
        
        general = new JPanel(new BorderLayout());
        Menu talkNet = new Menu(" TalkNet ");
        MenuItem logout = new MenuItem(" LogOut ");
        MenuItem exit = new MenuItem(" Exit ");
        talkNet.add(logout);
        talkNet.addSeparator ();
        talkNet.add(exit);
        
        Menu contacts = new Menu (" Contacts ");
        MenuItem add = new MenuItem( " Add Friend ");
        MenuItem delete = new MenuItem(" Delete Friend ");
        MenuItem ignore = new MenuItem(" Ignore Friend ");
        contacts.add(add);
        contacts.addSeparator ();
        contacts.add(delete);
        contacts.addSeparator ();
        contacts.add(ignore);
        
        Menu conference = new Menu (" Conference ");
        MenuItem createConf = new MenuItem(" Create conference ");
        conference.add(createConf);
        
        Menu help = new Menu( "Help" );
        
        //add Action liteners on menu items
        logout.addActionListener(new ItemAction());
        exit.addActionListener(new ItemAction());
        add.addActionListener(new ItemAction());
        delete.addActionListener(new ItemAction());
        ignore.addActionListener(new ItemAction());
        createConf.addActionListener(new ItemAction());
        menu.add( talkNet );
        menu.add( contacts );
        menu.add( conference );
        menu.add( help );
        
        setMenuBar(menu);
    	
    	
    	manager = Manager.getManager();
        setTitle("TalkNet");
        userID = user;
        this.passwd = passwd;
       
        
        friendList = new JList(manager.getFriends());

        friendList.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

                if (manager.chatWindows.get(friendList.getSelectedValue().toString()) == null ) {
                	ChatFrame fr = new ChatFrame(friendList.getSelectedValue().toString());
                	manager.chatWindows.put(friendList.getSelectedValue().toString(), fr);
                }
                else 
                	manager.chatWindows.get(friendList.getSelectedValue().toString()).show();
                System.out.println("map: " + friendList.getSelectedValue().toString()+". "+manager.chatWindows.size());
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
        //friendList.setSize(270, 400);
        
        jsp = new JScrollPane(friendList, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        hello = new JLabel("Hello, " + userID + "!");
        hello.setFont(new Font("Arial", Font.BOLD, 24));
        
        spaceBibi = new JLabel(" ");
        JPanel jp = new JPanel(new GridLayout(2, 1));
        
        add(general);
       
        general.add(jp, BorderLayout.NORTH);
        jp.add(hello);
        jp.add(spaceBibi);
        
        general.add(jsp, BorderLayout.CENTER);
        
        setSize(340, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        up = this;

    }
    
    //alext - update display list with data from manager
    public static void updatePanel() {
    	System.out.println("updatePanel: " + Manager.getManager().data[0]);
    	general.remove(1);
    	friendList.setListData(Manager.getManager().getFriends());
    	jsp = new JScrollPane(friendList, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	
    	general.add(jsp, BorderLayout.CENTER);
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
            JLabel action = new JLabel("An irritating one, huh? Simply ignore his messages!");
            JLabel frndID = new JLabel("Friend ID");
            final JTextField tf = new JTextField(20);
            JButton ignore = new JButton(" Ignore ");
            JButton cancel = new JButton(" Cancel ");
            
            JPanel general = new JPanel(new BorderLayout());
            JPanel simple1 = new JPanel(new FlowLayout());
            JPanel simple2 = new JPanel(new FlowLayout());
            
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
    }
    
}