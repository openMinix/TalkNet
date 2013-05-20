package client.Windows;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import client.Entities.Manager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bianca
 */

/**
 *  title = friend name or ID
 */
public class ChatFrame extends JFrame {
	
    String friendID;
    String message;
    JTextArea convArea;
    JTextArea messageArea;
    JButton send, sendFile, startAudio;
    
    
    Manager manager;
    
    public ChatFrame(String title) {
    	
        friendID = title;
        setTitle(title);
        
        JPanel general = new JPanel(new GridBagLayout());
        
        convArea = new JTextArea(13,40);
        convArea.setMaximumSize(new Dimension(13,  40));
        
        messageArea  = new JTextArea(5, 33);
        convArea.setEditable(false);
        
        
        JScrollPane jsp1 = new JScrollPane(convArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane jsp2 = new JScrollPane(messageArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e)
            {
                message = messageArea.getText();
                //System.out.println("Text is ready event");
                
                //clear message Area;
                messageArea.setText("");
                
                //update coversation Area
                convArea.setText(convArea.getText() + "\n" + 
                         "Me (" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) 
                        + "): " + message);
                manager.sendMessage(message, friendID + "@127.0.0.1/Spark 2.6.3");
            }
        
        });
        
        
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEFT));
 
        sendFile = new JButton("Send file");
        startAudio = new JButton("Audio!");
        sendFile.addActionListener(new SendFileListener(this));
        startAudio.addActionListener(new AudioListener());
        
        JPanel messPanel = new JPanel(new FlowLayout());
        
        
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        
        
        add(general);
        
        c1.gridx = 0;
        c1.gridy = 0;
        general.add(jsp1, c1);
        
        c2.gridx = 0;
        c2.gridy = 350;
        c2.anchor = GridBagConstraints.LINE_START;
        general.add(menu, c2);
        
        c3.gridx = 0;
        c3.gridy = 400;
        general.add(messPanel, c3);
        menu.add(sendFile);
        menu.add(startAudio);
        messPanel.add(jsp2);
        messPanel.add(send);
        
        setSize(500, 400);
       
        
        setVisible(true);
        this.setResizable(false);
        //setVisible(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        
        
        //alext - not sure if is best place
        //alext - but now it works
        manager = Manager.getManager();
        System.out.println("createEntry: " + friendID);
        manager.clean();//alext - clean manager between new login
        manager.createEntry(friendID, friendID);
    }
    
    //alext - displayMessage in current object chatFrame
    public void displayMessage(String buddy, String message ) {
    	System.out.println("budy: " + buddy);
    	this.show();
    	convArea.setText(convArea.getText() + "\n" + 
            buddy + " (" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) 
            + "): " + message);
    }

}

class SendListener implements ActionListener {
    String message;
    String friendID;
    public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("send button not implemented yet!");
            }
}

class SendFileListener implements ActionListener {
    ChatFrame parentFrame;
    
    public SendFileListener( ChatFrame jf ) {
    	super();
    	this.parentFrame = jf;
	}
    public void actionPerformed(ActionEvent e)
    {
    	
    	File dir=new File(".");
    	JFileChooser jfc=new JFileChooser(".");
    	jfc.setDialogTitle("Choose a file");
    	parentFrame.add(jfc);
    	jfc.setVisible(true);
    	
    	int returnValue = jfc.showOpenDialog( parentFrame);
    	File selectedFile;
    	if (returnValue == JFileChooser.APPROVE_OPTION) {
    		selectedFile = jfc.getSelectedFile();
    		
    		System.out.println("Am selectat" + selectedFile);
    		    		
    		Manager manager = Manager.getManager();
    	      // Create the outgoing file transfer
    	    manager.sendFile( selectedFile,parentFrame.friendID + "@127.0.0.1/Spark 2.6.3");
    	    
    	}	
                //Execute when button is pressed
    	
              //  System.out.println("SendFile button not implemented yet!");
    }
}

class AudioListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("Audio button not implemented yet!");
            }
}

