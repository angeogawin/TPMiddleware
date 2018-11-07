package src;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConnectionImpl extends UnicastRemoteObject implements Connexion {
	Server server;
	

	protected ConnectionImpl() throws RemoteException {
		super();
	
		// TODO Auto-generated constructor stub
	}
	public ConnectionImpl(Server s)  throws RemoteException {
		this.server=s;
	}
	
	@Override
	public Emitter connect(String nickname,Receiver rcv) throws RemoteException {
		// TODO Auto-generated method stub
		//associer nickname et receiver dans un hashmap
	    
		server.clients.add(nickname);
		Emitter emitterComponent=new EmitterImpl();
		
		try {
			Naming.rebind("Emitter"+nickname, emitterComponent);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return emitterComponent;
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		server.clients.remove(nickname);
		server.clientsRecemmentPartis.add(nickname);
		try {
			Naming.unbind("Emitter"+nickname);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
