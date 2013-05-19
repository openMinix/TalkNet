package client.Client;

import client.Entities.*;
import client.Utils.Parameters;
import client.Windows.ChatFrame;
import client.Windows.LoginFrame;
import client.Windows.MainFrame;

public class Main {
	
	//public static MainFrame mainFrame;
    public static LoginFrame loginFrame;
	/*
	 * simple class Test
	 */
    public static void main(String[] args) throws Exception {

    	//mainFrame = new MainFrame("usertest1", "123456");
    	loginFrame = new LoginFrame();
        
        
        //chatFrame = new ChatFrame("usertest2");
        
        /*
        //wait logged in 10 s
        //Thread.sleep(10000);
        while ( true ) {
        	if ( 5 > 10 )
        		break;
        	Thread.sleep(1000);
        	//manager.sendMessage("hello world", "usertest2@127.0.0.1/Spark 2.6.3");
        }
        
        //bye bye
        Logout logout = new Logout();
        logout.destroy();
        */
        
    }

}