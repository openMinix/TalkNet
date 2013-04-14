import client.Entities.*;

public class Test {
    
	/*
	 * simple class Test
	 */
	
    public static void main(String[] args) throws Exception {
                
    	//creates a connection with server
    	ConnectionManager cm = new ConnectionManager();
    	Register rm = new Register("usertest", "123456");
        Login login = new Login();
        login.loginCredentials("usertest", "123456");
     
        //wait logged in 10 s
        Thread.sleep(10000);
        
        //bye bye
        Logout logout = new Logout();
        logout.destroy();
        
    }
}