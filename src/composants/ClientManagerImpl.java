package composants;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import main.Client;

public class ClientManagerImpl extends UnicastRemoteObject implements ClientManager,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client;
	protected ClientManagerImpl() throws RemoteException {
		super();
		
	}
	
	public ClientManagerImpl(Client client) throws RemoteException {
		this.client=client;
	
	}

	@Override
	public void initClients(List<String> clients) throws RemoteException {
		
		client.setClients(clients);

	}

	@Override
	public void addClients(String client1) throws RemoteException {
	
		client.addClient(client1);

	}

	@Override
	public void remClient(String client1) throws RemoteException {
		
		client.removeClient(client1);

	}

}
