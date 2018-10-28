import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {

	protected ReceiverImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receive(String from, String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initClients(String[] clients) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClients(String client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remClients(String client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
