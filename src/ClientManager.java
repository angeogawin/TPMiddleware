import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientManager extends Remote {
	
	void initClients(ArrayList<String> clients) throws RemoteException;
	void addClients(String client) throws RemoteException;
	void remClient(String client) throws RemoteException;

}
