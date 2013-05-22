package client.Entities;

import java.awt.Toolkit;

import javax.swing.SwingWorker;

import org.jivesoftware.smackx.filetransfer.FileTransfer;


class ProgressTask extends SwingWorker<Void, Void> {
	
	FileTransfer transfer;
	ProgressTask( FileTransfer transfer )
	{
		super();
		this.transfer = transfer;
	}
	
	
        @Override
        public Void doInBackground() {
        	
        	
        	int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(400);
                while (progress < 101 && !transfer.isDone()) {
                    //Sleep for up to one second.
                   
                    progress = (int)(100.0 *transfer.getProgress());
                    setProgress(Math.min(progress, 100));
                    
                }
            } catch (InterruptedException ignore) {}
            return null;
        }
 
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
        }
    }
