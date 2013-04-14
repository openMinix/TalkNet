package client.Entities;


public class Logout {
	
	/* destroy the connection with server */
    public void destroy() {
        if (ConnectionManager.connection!=null && ConnectionManager.connection.isConnected()) {
        	ConnectionManager.connection.disconnect();
        }
    }
    
}
