
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	ArrayList<String> clients;
	ArrayList<Message> messages;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	
	}
	protected DialogueImpl(ArrayList<String> clients,ArrayList<Message> messages) throws RemoteException {
		
		
		
		this.clients=clients;
		this.messages=messages;
		
	}
	
	
	@Override
	public ArrayList<String> getClients() throws RemoteException {
		// TODO Auto-generated method stub
		return clients;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
		// TODO Auto-generated method stub
		messages.add(m);
		
	}



	@Override
	public ArrayList<Message> getMessages(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Message> retour=new ArrayList<>();
		for (Message m:messages) {
			if(m.getTo().equals(pseudo)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
