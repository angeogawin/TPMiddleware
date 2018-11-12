package composants;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import src.Client;
import src.Message;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {
    Client client;
	protected ReceiverImpl() throws RemoteException {
		super();
		
	
	}
	
	
	public ReceiverImpl(Client client)throws RemoteException  {
		this.client=client;
		
	}

	@Override
	public void receive(String from, String message) throws RemoteException {
		
		Message m =new Message(from,client.pseudo,message);
		client.messages.add(m);
		
	
	}

	@Override
	public void initClients(ArrayList<String> clientsSurServeur) throws RemoteException {
		
		for (String c:clientsSurServeur) {
			if(!c.equals(client.pseudo)) {
				client.clients.add(c);
				
			}
		}
		
		
	}

	@Override
	public void addClient(String clientArrive) throws RemoteException {
		
		if(!clientArrive.equals(client.pseudo)) {
			client.clients.add(clientArrive);
		}
		
	}

	@Override
	public void remClient(String clientParti) throws RemoteException {
		
		if(!clientParti.equals(client.pseudo)) {
			client.clients.remove(clientParti);
		}
	}

}
