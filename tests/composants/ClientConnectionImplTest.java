package composants;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;

public class ClientConnectionImplTest {
	
	private ClientConnectionImpl ccImpl;
	private Client client;
	private Client client2;
	private MessageBox rcv;

	@Before
	public void setUp() throws Exception {
		client=new Client();
		client2=new Client();
		ccImpl=new ClientConnectionImpl(client);
		rcv=new MessageBoxImpl(client2);//cest dans cette boite que les messages pour clients2 arriveront
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnection() throws RemoteException {
		ccImpl.connect("titi", rcv);
		assertTrue(client.getListeBox().get("titi").equals(rcv));
		
	}

}
