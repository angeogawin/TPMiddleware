import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageBoxImpl extends UnicastRemoteObject implements MessageBox {
    Client client;
	protected MessageBoxImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageBoxImpl(Client client) throws RemoteException {
		this.client=client;
	}

	@Override
	public void receive(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		client.messages.add(message);

	}

}
