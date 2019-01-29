package composants;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Client;
import main.Message;

public class MessageBoxImplTest {
	
	private MessageBoxImpl mImpl;
	private Client client;
	private Message m;

	@Before
	public void setUp() throws Exception {
		client=new Client();
		mImpl=new MessageBoxImpl(client);
		m=new Message("toto","titi","salut");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReceptionMessage() throws RemoteException {
		mImpl.receive(m);
		assertEquals(m,client.getMessages().get(client.getMessages().size()-1));
		
		}
}
