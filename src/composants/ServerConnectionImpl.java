package composants;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Server;

public class ServerConnectionImpl extends UnicastRemoteObject implements ServerConnection,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Server server;
	Logger logger = Logger.getLogger("logger");
	protected ServerConnectionImpl() throws RemoteException {
		super();
		
	}
	
	public ServerConnectionImpl(Server s) throws RemoteException {
		this.server=s;
	}

	@Override
	public void connect(String nickname, ClientConnection cnx, ClientManager mgr) throws RemoteException {
		// conserver l ordre des instructions suivantes
		server.listeClientConnection.put(nickname, cnx);
		for (Map.Entry<String,ClientManager> entry :server.listeClientManager.entrySet()) {
		 
		    ClientManager c = entry.getValue();
		    c.addClients(nickname);
		}
		server.listeClientManager.put(nickname, mgr);
		mgr.initClients(server.getClients());
		server.addClient(nickname);
		
		
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		
		try {
			Naming.unbind("ClientConnection"+nickname);
			server.listeClientConnection.remove(nickname);
			Naming.unbind("ClientManager"+nickname);
			server.listeClientManager.remove(nickname);
			server.removeClient(nickname);
			
		   
		    for (Map.Entry<String,ClientManager> entry :server.listeClientManager.entrySet()) {
			  
			    ClientManager c = entry.getValue();
			    c.remClient(nickname);
			}
		
		} catch (MalformedURLException |NotBoundException e) {
			logger.log(Level.INFO, e.getMessage());	
			
		} 
		
		
	}

	@Override
	public ClientConnection getClient(String nickame) throws RemoteException {
		
		return server.listeClientConnection.get(nickame);
	}

}
