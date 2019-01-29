package composants;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;

public class ClientManagerImplTest {
	private Client client;
	private ClientManagerImpl clientManager;
	List<String> clients;
	
	@Before
	public void setUp() throws Exception {
		client=new Client();
		clientManager =new ClientManagerImpl(client);
		clients=new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitialisationClients() throws RemoteException {
		
		clients.add("toto");
		clients.add("titi");
		
		clientManager.initClients(clients);
		assertEquals(client.getClients(),clients);
	}
	
	@Test
	public void testAjoutClient() throws RemoteException {
		
		clients.add("toto");
		clients.add("titi");
		
		clientManager.addClients("tata");
		assertEquals(client.getClients().get(client.getClients().size()-1),"tata");
	}
	
	@Test
	public void testSuppressionClient() throws RemoteException {
		
		clients.add("toto");
		clients.add("titi");
		
		clientManager.remClient("titi");
		
		assertFalse(client.getClients().contains("titi"));
	}

}
