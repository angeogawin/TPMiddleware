package composants;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;
import main.Server;

public class ServerConnectionImplTest {
	private Server server;
	private ServerConnectionImpl sImpl;
	private Client client;

	@Before
	public void setUp() throws Exception {
		server=new Server();
		sImpl=new ServerConnectionImpl(server);
		client=new Client();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClientConnection() throws RemoteException {
		
		ClientConnection cnx=new ClientConnectionImpl(client);
		ClientManager mgr=new ClientManagerImpl(client);
		sImpl.connect("toto", cnx, mgr);
		assertTrue(server.listeClientConnection.get("toto").equals(cnx));
		assertTrue(server.listeClientManager.get("toto").equals(mgr));
	}
	
	@Test
	public void testClientDeConnection() throws RemoteException  {
		
		ClientConnection cnx=new ClientConnectionImpl(client);
		ClientManager mgr=new ClientManagerImpl(client);
		sImpl.connect("toto", cnx, mgr);
		
		try {
			sImpl.disconnect("toto");
			assertTrue(!server.listeClientConnection.containsKey("toto"));
			assertTrue(!server.listeClientManager.containsKey("toto"));
			assertTrue(!server.getClients().contains("toto"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
