package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientConnection extends Remote {
	
	MessageBox connect(String nickname,MessageBox rcv) throws RemoteException;

}
