package composants;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.Client;
import main.Message;

public class MessageBoxImpl extends UnicastRemoteObject implements MessageBox {
    Client client;
	protected MessageBoxImpl() throws RemoteException {
		super();
	
	}
	public MessageBoxImpl(Client client) throws RemoteException {
		this.client=client;
	}

	@Override
	public void receive(Message message) throws RemoteException {
	
		client.addMessage(message);

	}

}
