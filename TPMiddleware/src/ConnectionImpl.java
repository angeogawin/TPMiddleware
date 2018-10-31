import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConnectionImpl extends UnicastRemoteObject implements Connexion {
	ArrayList<String> clients;
	ArrayList<Message> messages;

	protected ConnectionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	ConnectionImpl(ArrayList<String> clients,ArrayList<Message> messages) throws RemoteException {
		this.clients=clients;
		this.messages=messages;
	}

	@Override
	public Emitter connect(String nickname,Receiver rcv) throws RemoteException {
		// TODO Auto-generated method stub
		
	    
		clients.add(nickname);
		Emitter emitterComponent=new EmitterImpl(messages);
		
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
		clients.remove(nickname);
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
