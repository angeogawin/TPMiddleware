import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageBox extends Remote {
	
	void receive(Message message) throws RemoteException;

}
