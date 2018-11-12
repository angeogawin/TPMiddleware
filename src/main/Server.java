package main;


import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import composants.ConnectionImpl;
import composants.Connexion;


public class Server implements Serializable {
	private List<String> clients;
	public List<String> getClients() {
		return clients;
	}


	public void addClient(String client) {
		clients.add(client);
	}
	public void removeClient(String client) {
		clients.remove(client);
	}


	public List<Message> getMessages() {
		return messages;
	}


	public void addMessage(Message message) {
		messages.add( message);
	}


	private List<Message> messages;
	public Server() {
		clients=new ArrayList<>();
		messages=new ArrayList<>();
	}
	

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("logger");
		Server server=new Server();
		try {			
			Connexion connexionComponent=new ConnectionImpl(server);
			
			
			Naming.rebind("Connexion", connexionComponent);
			System.out.println("Serveur actif");
			
		} catch (RemoteException | MalformedURLException e) {
		
			logger.log(Level.INFO, e.getMessage());
		}
	}

}
