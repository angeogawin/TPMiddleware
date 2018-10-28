import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Receiver extends Remote{
	
	void receive(String from,String message)  throws RemoteException;
	void initClients(String[] clients)  throws RemoteException;
	void addClients(String client)  throws RemoteException;
	void remClients(String client)  throws RemoteException;
	

}
