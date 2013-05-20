package client.Entities;


public class Logout {
	
	//alext - destroy the connection with server
    public void destroy() {
        if (ConnectionManager.connection!=null && ConnectionManager.connection.isConnected()) {
        	ConnectionManager.connection.disconnect();
        }
    }
    
}
