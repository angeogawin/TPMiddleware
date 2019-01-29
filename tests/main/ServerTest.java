package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerTest {

	private Server server;
	
	@Before
	public void setUp() throws Exception {
		server=new Server();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAjoutClient() {
		server.addClient("toto");
		assertTrue(server.getClients().get(server.getClients().size()-1).equals("toto"));
	}
	
	@Test
	public void testAjoutMessage() {
		Message m=new Message("toto","titi","hey");
		server.addMessage(m);
		assertTrue(server.getMessages().get(server.getMessages().size()-1).equals(m));
	}
	
	@Test
	public void testSupprimerClient() {
		server.addClient("toto");
		server.removeClient("toto");
		assertTrue(!server.getClients().contains("toto"));
	}

}
