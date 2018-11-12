package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;

import src.Message;

public interface Emitter extends Remote {
	
	void sendMessage(Message m) throws RemoteException;

}
