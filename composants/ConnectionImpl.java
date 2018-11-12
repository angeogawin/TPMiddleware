package composants;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.logging.Level;
import java.util.logging.Logger;

import src.Server;

public class ConnectionImpl extends UnicastRemoteObject implements Connexion , Serializable {
	Server server;
	
	Logger logger = Logger.getLogger("logger");
	protected ConnectionImpl() throws RemoteException {
		super();
	
		
	}
	public ConnectionImpl(Server s)  throws RemoteException {
		this.server=s;
	}
	
	@Override
	public Emitter connect(String nickname,Receiver rcv) throws RemoteException {
		
		//associer nickname et receiver dans un hashmap
	    
		server.clients.add(nickname);
		Emitter emitterComponent=new EmitterImpl();
		
		try {
			Naming.rebind("Emitter"+nickname, emitterComponent);
			
		} catch (MalformedURLException e) {
		
			logger.log(Level.INFO, e.getMessage());
		}
		

		return emitterComponent;
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		
		server.clients.remove(nickname);
		server.clientsRecemmentPartis.add(nickname);
		try {
			Naming.unbind("Emitter"+nickname);
		} catch (MalformedURLException|NotBoundException e) {
		
			logger.log(Level.INFO, e.getMessage());
		} 

	}

}
