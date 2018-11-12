package composants;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connexion extends Remote {
	
	Dialogue connect(String nickname) throws RemoteException;
	void disconnect(String nickname) throws RemoteException;


}
