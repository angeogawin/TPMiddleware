package composants;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import src.Message;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	ArrayList<String> clients;
	ArrayList<Message> messages;
	String nickname;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
	
	}
	protected DialogueImpl(String nickname,ArrayList<String> clients,ArrayList<Message> messages) throws RemoteException {
		
		
		
		this.clients=clients;
		this.messages=messages;
		this.nickname=nickname;
		
	}
	
	
	@Override
	public List<String> getClients() throws RemoteException {
	
		return clients;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
		
		messages.add(m);
		
	}



	@Override
	public List<Message> getMessages() throws RemoteException {
		
		List<Message> retour=new ArrayList<>();
		for (Message m:messages) {
			if(m.getTo().equals(nickname)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
