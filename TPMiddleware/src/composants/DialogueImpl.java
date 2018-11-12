package composants;
import main.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import main.Message;
import main.Server;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	Server server;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
	
	
	}
	public DialogueImpl(Server server) throws RemoteException {
		
		
		
		this.server=server;
		
		
	}
	
	

	
	
	@Override
	public void connect(String pseudo) throws RemoteException{
		
		
		server.clients.add(pseudo);
		
		
	}



	@Override
	public void disconnect(String pseudo) throws RemoteException {
	
		server.clients.remove(pseudo);
		
		
	}



	@Override
	public List<String> getClients() throws RemoteException {

		return server.clients;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
	
		server.messages.add(m);
		
	}



	@Override
	public List<Message> getMessages(String pseudo) throws RemoteException {
		
		List<Message> retour=new ArrayList<>();
		for (Message m:server.messages) {
			if(m.getTo().equals(pseudo)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
