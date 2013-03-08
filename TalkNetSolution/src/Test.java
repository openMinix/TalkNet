public class Test {
    
	/* Simple class for testing a connection and login to server 
	 * !!! (username and password) must be created before
	 */
	
    public static void main(String[] args) throws Exception {
        
        String username = "vlad";
        String password = "123456";
        
        TalkNetManager xmppManager = new  TalkNetManager("projects.rosedu.org", 5222);
        
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