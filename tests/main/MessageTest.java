package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

	protected Message m;
	@Before
	public void setUp() throws Exception {
		m=new Message("toto","titi","salut");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBonneInstanciationExpediteur() {
		assertEquals("toto",m.getFrom());
	}
	@Test
	public void testBonneInstanciationDestinataire() {
		assertEquals("titi",m.getTo());
	}
	
	@Test
	public void testBonneInstanciationMessage() {
		assertEquals("salut",m.getMessage());
	}

}
