

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




public class DialogueImpl extends UnicastRemoteObject implements Dialogue {

	Server server;
	String nickname;
	
	protected DialogueImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	
	}
	protected DialogueImpl(String nickname,Server server) throws RemoteException {
		
		
		
		this.server=server;
		this.nickname=nickname;
		
	}
	
	
	@Override
	public ArrayList<String> getClients() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<String> retour=new ArrayList<>();
		for(String c:server.clients) {
			if(!(c.equals(nickname))) {
				retour.add(c);
				
			}
		}
		return retour;
		
	}



	@Override
	public void sendMessage(Message m) throws RemoteException {
		// TODO Auto-generated method stub
		server.messages.add(m);
		
	}



	@Override
	public ArrayList<Message> getMessages() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Message> retour=new ArrayList<>();
		for (Message m:server.messages) {
			if(m.getTo().equals(nickname)) {
				retour.add(m);
			}
		}
		return retour;
	}
}
