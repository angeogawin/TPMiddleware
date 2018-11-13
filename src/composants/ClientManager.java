package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientManager extends Remote {
	
	void initClients(List<String> clients) throws RemoteException;
	void addClients(String client) throws RemoteException;
	void remClient(String client) throws RemoteException;

}
