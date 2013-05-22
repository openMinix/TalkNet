package client.Entities;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import client.Windows.ChatFrame;

public class TransferListener implements FileTransferListener, PropertyChangeListener {

	ChatFrame parentFrame;
	File recvFile;
	ProgressTask task;
	ProgressMonitor progressMonitor;
	
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
				
				
	    		 task = new ProgressTask( transfer );
	    		 task.addPropertyChangeListener(this);
	    		 
				 progressMonitor = new ProgressMonitor( null,
                         "Receiving file ...",
                         "", 0, 100);
				 progressMonitor.setProgress(0);
				 
	    		 task.execute();
				
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

	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
            		String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
            
                } else {
            
                }
            }
        }
    }
}
