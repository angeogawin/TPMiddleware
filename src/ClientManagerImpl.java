import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientManagerImpl extends UnicastRemoteObject implements ClientManager {
	Client client;
	protected ClientManagerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClientManagerImpl(Client client) throws RemoteException {
		this.client=client;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initClients(ArrayList<String> clients) throws RemoteException {
		// TODO Auto-generated method stub
		client.clients=clients;

	}

	@Override
	public void addClients(String client1) throws RemoteException {
		// TODO Auto-generated method stub
		client.clients.add(client1);

	}

	@Override
	public void remClient(String client1) throws RemoteException {
		// TODO Auto-generated method stub
		client.clients.remove(client1);

	}

}
