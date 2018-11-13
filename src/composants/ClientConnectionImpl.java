package composants;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Client;

public class ClientConnectionImpl extends UnicastRemoteObject implements ClientConnection,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client;
    Logger logger = Logger.getLogger("logger");
	protected ClientConnectionImpl() throws RemoteException {
		super();
		
	}
	public ClientConnectionImpl(Client client) throws RemoteException {
		this.client=client;
		
	}

	@Override
	public MessageBox connect(String nickname, MessageBox rcv) throws RemoteException {
		
		client.addBox(nickname, rcv);
		MessageBox msgbox=new MessageBoxImpl(client);
		try {
			Naming.rebind("MessageBox"+client.getPseudo()+nickname, msgbox);
		} catch (MalformedURLException e) {
			logger.log(Level.INFO, e.getMessage());
		
		}
		return msgbox;
	}

}
