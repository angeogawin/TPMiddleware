import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ServerConnectionImpl extends UnicastRemoteObject implements ServerConnection{
	Server server;

	protected ServerConnectionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ServerConnectionImpl(Server s) throws RemoteException {
		this.server=s;
	}

	@Override
	public void connect(String nickname, ClientConnection cnx, ClientManager mgr) throws RemoteException {
		// conserver l ordre des instructions suivantes
		server.listeClientConnection.put(nickname, cnx);
		for (Map.Entry<String,ClientManager> entry :server.listeClientManager.entrySet()) {
		    String key = entry.getKey();
		    ClientManager c = entry.getValue();
		    c.addClients(nickname);
		}
		server.listeClientManager.put(nickname, mgr);
		mgr.initClients(server.clients);
		server.clients.add(nickname);
		
		
	}

	@Override
	public void disconnect(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			Naming.unbind("ClientConnection"+nickname);
			server.listeClientConnection.remove(nickname);
			Naming.unbind("ClientManager"+nickname);
			server.listeClientManager.remove(nickname);
			server.clients.remove(nickname);
		    for(String c:server.clients) {
		    	//on supprime tous les composants messages box eventuellement créés pour faire communiquer nickname et d'autres clients
		    	String nomComposant1="MessageBox"+nickname+c;
		    	String nomComposant2="MessageBox"+c+nickname;
		    	Naming.unbind(nomComposant1);
		    	Naming.unbind(nomComposant2);
		    }
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public ClientConnection getClient(String nickame) throws RemoteException {
		// TODO Auto-generated method stub
		return server.listeClientConnection.get(nickame);
	}

}
