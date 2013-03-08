public class Test {
    
	/* Simple class for testing a connection and login to server 
	 * !!! (username and password) must be created before
	 */
	
    public static void main(String[] args) throws Exception {
        
        String username = "alex2";
        String password = "test";
        
        TalkNetManager xmppManager = new  TalkNetManager("127.0.0.1", 5222);
        
        xmppManager.init();
        xmppManager.loginCredentials(username, password);
        xmppManager.setStatus(true, "Hello everyone");
        
        
        while (true) {					//go to Sessions and
        	if( 4 == 5)					//user alex2 should be Authenticated 
        		break;
        }
        
        xmppManager.destroy();
    }
}