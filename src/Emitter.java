package src;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Emitter extends Remote {
	
	void sendMessage(Message m) throws RemoteException;

}
