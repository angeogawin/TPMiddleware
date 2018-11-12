package composants;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

import src.Message;

public interface Dialogue extends Remote {
	
	
    List<String> getClients() throws RemoteException;
    void sendMessage(Message m) throws RemoteException;
    List<Message> getMessages() throws RemoteException;
}
