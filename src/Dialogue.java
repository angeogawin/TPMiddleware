package src;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Dialogue extends Remote {
	
	
    ArrayList<String> getClients() throws RemoteException;
    void sendMessage(Message m) throws RemoteException;
    ArrayList<Message> getMessages() throws RemoteException;
}
