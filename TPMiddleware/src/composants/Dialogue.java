package composants;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import main.*;

public interface Dialogue extends Remote {
	
	
	void connect(String pseudo) throws RemoteException;
    void disconnect(String pseudo) throws RemoteException;
    List<String> getClients() throws RemoteException;
    void sendMessage(Message m) throws RemoteException;
    List<Message> getMessages(String pseudo) throws RemoteException;
}
