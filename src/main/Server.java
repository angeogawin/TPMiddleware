package main;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import composants.ClientConnection;
import composants.ClientManager;
import composants.ServerConnection;
import composants.ServerConnectionImpl;

public class Server implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> clients;
	public ArrayList<String> getClients() {
		return clients;
	}


	public void addClient(String client) {
		clients.add(client);
	}
	public void removeClient(String client) {
		clients.remove(client);
	}


	public ArrayList<Message> getMessages() {
		return messages;
	}


	public void addMessage(Message message) {
		messages.add(message);
	}


	private ArrayList<Message> messages;
	public HashMap<String,ClientConnection> listeClientConnection;//associe a chaque nickname un "clientconnection"
	public HashMap<String,ClientManager> listeClientManager;//associe a chaque nickname un "clientmanager"
	
	public Server() {
		clients=new ArrayList<>();
		messages=new ArrayList<>();
		listeClientConnection=new HashMap<>();
		listeClientManager=new HashMap<>();
	}
	

	public static void main(String[] args) {
		
		 Logger logger = Logger.getLogger("logger");
		 
		try {
		
			Server server=new Server();
			ServerConnection serverConnection=new ServerConnectionImpl(server);
		
			
			Naming.rebind("ServerConnection", serverConnection);
			
			
			
			
			System.out.println("Serveur actif");
			
		
			
		} catch (RemoteException | MalformedURLException e) {
			
			logger.log(Level.INFO, e.getMessage());
		}
	}

}
