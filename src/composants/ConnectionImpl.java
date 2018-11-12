package composants;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Server;

public class ConnectionImpl extends UnicastRemoteObject implements Connexion {
	Server server;
	Logger logger = Logger.getLogger("logger");
	protected ConnectionImpl() throws RemoteException {
		super();
	
	}
	public ConnectionImpl(Server server) throws RemoteException {
		this.server=server;
	}

	@Override
	public Dialogue connect(String nickname) throws RemoteException {
		
		
	
		server.addClient(nickname);
		Dialogue dialogue =new DialogueImpl(nickname,server);
		try {
			
			Naming.rebind("Dialogue"+nickname, dialogue);
		} catch (MalformedURLException e) {
			logger.log(Level.INFO, e.getMessage());
			
		}

		return dialogue;
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		
		server.removeClient(nickname);
		try {
			Naming.unbind("Dialogue"+nickname);
		} catch (MalformedURLException|NotBoundException e) {
			
			logger.log(Level.INFO, e.getMessage());
		} 

	}

}
