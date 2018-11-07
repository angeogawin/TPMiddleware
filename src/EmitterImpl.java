package src;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class EmitterImpl extends UnicastRemoteObject implements Emitter {
	

	protected EmitterImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void sendMessage(Message m) throws RemoteException {
		// TODO Auto-generated method stub
		Receiver receiver;
		try {
			receiver = (Receiver) Naming.lookup("Receiver"+m.to);
			receiver.receive(m.from, m.message);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
