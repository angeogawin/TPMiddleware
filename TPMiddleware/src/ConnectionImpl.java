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
	public Dialogue connect(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		
	
		clients.add(nickname);
		Dialogue dialogue =new DialogueImpl(nickname,clients,messages);
		try {
			
			Naming.rebind("Dialogue"+nickname, dialogue);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dialogue;
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		clients.remove(nickname);
		try {
			Naming.unbind("Dialogue"+nickname);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
