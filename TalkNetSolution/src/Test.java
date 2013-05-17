import client.Entities.*;
import client.Utils.Parameters;

public class Test {
    
	/*
	 * simple class Test
	 */
	
    public static void main(String[] args) throws Exception {
                
    	//creates a connection with server
    	ConnectionManager cm = new ConnectionManager();
    	//Register rm = new Register("usertest2", "123456");
        Login login1 = new Login();
        login1.loginCredentials("usertest1", "123456");
        
        
        Manager manager = Manager.getManager();
        manager.setStatus(true, "hello");
        manager.createEntry("usertest2", "usertest2");
        
        //wait logged in 10 s
        //Thread.sleep(10000);
        while ( true ) {
        	if ( 5 > 10 )
        		break;
        	Thread.sleep(1000);
        	manager.sendMessage("hello world", "usertest2@127.0.0.1/Spark 2.6.3");
        }
        
        //bye bye
        Logout logout = new Logout();
        logout.destroy();
        
    }
}