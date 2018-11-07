package src;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {
    Client client;
	protected ReceiverImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}
	
	
	public ReceiverImpl(Client client)throws RemoteException  {
		this.client=client;
		
	}

	@Override
	public void receive(String from, String message) throws RemoteException {
		// TODO Auto-generated method stub
		Message m =new Message(from,client.pseudo,message);
		client.messages.add(m);
		
	
	}

	@Override
	public void initClients(ArrayList<String> clientsSurServeur) throws RemoteException {
		// TODO Auto-generated method stub
		for (String c:clientsSurServeur) {
			if(!c.equals(client.pseudo)) {
				client.clients.add(c);
				
			}
		}
		
		
	}

	@Override
	public void addClient(String clientArrive) throws RemoteException {
		// TODO Auto-generated method stub
		if(!client.equals(client.pseudo)) {
			client.clients.add(clientArrive);
		}
		
	}

	@Override
	public void remClient(String clientParti) throws RemoteException {
		// TODO Auto-generated method stub
		if(!client.equals(client.pseudo)) {
			client.clients.remove(clientParti);
		}
	}

}
