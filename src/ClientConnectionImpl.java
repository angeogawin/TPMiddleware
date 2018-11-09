import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientConnectionImpl extends UnicastRemoteObject implements ClientConnection {
    Client client;
	protected ClientConnectionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClientConnectionImpl(Client client) throws RemoteException {
		this.client=client;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MessageBox connect(String nickname, MessageBox rcv) throws RemoteException {
		// TODO Auto-generated method stub
		client.listeBox.put(nickname, rcv);
		MessageBox msgbox=new MessageBoxImpl(client);
		try {
			Naming.rebind("MessageBox"+client.pseudo+nickname, msgbox);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgbox;
	}

}
