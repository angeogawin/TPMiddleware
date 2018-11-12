package composants;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import src.Message;

public class EmitterImpl extends UnicastRemoteObject implements Emitter,Serializable {
	
	Logger logger = Logger.getLogger("logger");
	protected EmitterImpl() throws RemoteException {
		super();
		
	}
	
	@Override
	public void sendMessage(Message m) throws RemoteException {
		
		Receiver receiver;
		try {
			receiver = (Receiver) Naming.lookup("Receiver"+m.getTo());
			receiver.receive(m.getFrom(), m.getContenu());
		} catch (MalformedURLException|NotBoundException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		
		
		
	}

}
