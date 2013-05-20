package client.Entities;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import client.Windows.ChatFrame;

public class TransferListener implements FileTransferListener {

	ChatFrame parentFrame;
	File recvFile;
	
	public TransferListener( ChatFrame cf) {
		parentFrame = cf;
	}
	
	public TransferListener( ) {
		parentFrame = null;
	}
	
	public File ShouldTransfer( FileTransferRequest request)
	{
		int n= JOptionPane.showConfirmDialog(
			    parentFrame,
			    "Would you like to receive file?",
			    "Incoming file!",
			    JOptionPane.YES_NO_OPTION);
		
	
		File recvFile = null;
		
		if ( n == JOptionPane.YES_OPTION)
		{
			
	    	JFileChooser jfc=new JFileChooser(".");
	    	jfc.setDialogTitle("Choose a file to receive file");
	   
	    	jfc.setVisible(true);
	    
	    	int returnValue = jfc.showOpenDialog( parentFrame);
	    	
	    	if (returnValue == JFileChooser.APPROVE_OPTION) {
	    		recvFile = jfc.getSelectedFile();
	    		
	    		System.out.println("Am selectat primire" + recvFile);
	    	}
		}
		
		return recvFile;
	}
	
	@Override
	public void fileTransferRequest(FileTransferRequest request ) {
		
		System.out.println("Am primit cerere");
		
		File recvFile = ShouldTransfer(request);
		
		System.out.println("Am terminat acceptare");
		
        if( recvFile != null ) {
              // Accept it
              IncomingFileTransfer transfer = request.accept();
              try {
          		System.out.println("Ajung sa primesc");
				transfer.recieveFile( recvFile );
			} catch (XMPPException e) {
				System.out.println("Error on receive:" + e.toString());
				e.printStackTrace();
			}
        } else {
              // Reject it
        	System.out.println("Rject pe cerere");
        	request.reject();
        }
	}

}
