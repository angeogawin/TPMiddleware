package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;

import main.Message;

public interface MessageBox extends Remote {
	
	void receive(Message message) throws RemoteException;

}
