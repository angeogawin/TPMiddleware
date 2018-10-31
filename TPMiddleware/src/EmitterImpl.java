import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmitterImpl extends UnicastRemoteObject implements Emitter {
	ArrayList<Message> messages;

	protected EmitterImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmitterImpl(ArrayList<Message> messages) throws RemoteException {
	this.messages=messages;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendMessage(Message m) throws RemoteException {
		// TODO Auto-generated method stub
		messages.add(m);
		
		
	}

}
