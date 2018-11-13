package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerConnection extends Remote {
	
	void connect(String nickname,ClientConnection cnx,ClientManager mgr) throws RemoteException;
	void disconnect(String nickname) throws RemoteException;
	ClientConnection getClient(String nickame) throws RemoteException;

}
