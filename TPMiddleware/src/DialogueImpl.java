
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ArrayList;


public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	Server server;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	
	}
	protected DialogueImpl(Server server) throws RemoteException {
		
		
		
		this.server=server;
		
		
	}
	
	

	
	
	@Override
	public void connect(String pseudo) throws RemoteException{
		
		
		server.clients.add(pseudo);
		
		
	}



	@Override
	public void disconnect(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		server.clients.remove(pseudo);
		
		
	}



	@Override
	public ArrayList<String> getClients() throws RemoteException {
		// TODO Auto-generated method stub
		return server.clients;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
		// TODO Auto-generated method stub
		server.messages.add(m);
		
	}



	@Override
	public ArrayList<Message> getMessages(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Message> retour=new ArrayList<>();
		for (Message m:server.messages) {
			if(m.getTo().equals(pseudo)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
