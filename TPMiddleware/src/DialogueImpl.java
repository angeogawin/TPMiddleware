
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	ArrayList<String> clients;
	ArrayList<Message> messages;
	String nickname;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	
	}
	protected DialogueImpl(String nickname,ArrayList<String> clients,ArrayList<Message> messages) throws RemoteException {
		
		
		
		this.clients=clients;
		this.messages=messages;
		this.nickname=nickname;
		
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
	public ArrayList<Message> getMessages() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Message> retour=new ArrayList<>();
		for (Message m:messages) {
			if(m.getTo().equals(nickname)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
