import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {
    ArrayList<String> clients;
    String nickname;
	protected ReceiverImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReceiverImpl(String nickname,ArrayList<String> clients) throws RemoteException {
		this.clients=clients;
		this.nickname=nickname;
	}

	@Override
	public void receive(String from, String message) throws RemoteException {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void initClients(ArrayList<String> clientsSurServeur) throws RemoteException {
		// TODO Auto-generated method stub
		for (String c:clientsSurServeur) {
			if(!c.equals(nickname)) {
				clients.add(c);
			}
		}
		
	}

	@Override
	public void addClient(String client) throws RemoteException {
		// TODO Auto-generated method stub
		if(!client.equals(nickname)) {
			clients.add(client);
		}
		
	}

	@Override
	public void remClient(String client) throws RemoteException {
		// TODO Auto-generated method stub
		if(!client.equals(nickname)) {
			clients.remove(client);
		}
	}

}
