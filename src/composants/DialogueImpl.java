package composants;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import main.Message;
import main.Server;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	Server server;
	String nickname;
	
	protected DialogueImpl() throws RemoteException {
		super();
	
	
	}
	protected DialogueImpl(String nickname,Server server) throws RemoteException {
		
		
		
		this.server=server;
		this.nickname=nickname;
		
	}
	
	
	@Override
	public List<String> getClients() throws RemoteException {
		
		ArrayList<String> retour=new ArrayList<>();
		for(String c:server.getClients()) {
			if(!(c.equals(nickname))) {
				retour.add(c);
				
			}
		}
		return retour;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
		
		server.addMessage(m);
		
	}



	@Override
	public List<Message> getMessages() throws RemoteException {
		
		ArrayList<Message> retour=new ArrayList<>();
		for (Message m:server.getMessages()) {
			if(m.getTo().equals(nickname)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
