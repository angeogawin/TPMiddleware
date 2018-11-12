package src;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import composants.ConnectionImpl;
import composants.Connexion;
import composants.Receiver;

public class Server  implements Serializable{
	public ArrayList<String> clients;
	public ArrayList<Message> messages;
	int lastSizeClients;
	int lastSizeMessages;
	String listeClients;
	public ArrayList<String> clientsRecemmentPartis;
	Logger logger = Logger.getLogger("logger");
	public Server() {
		
		clients=new ArrayList<>();
		
		messages=new ArrayList<>();
		clientsRecemmentPartis=new ArrayList<>();
		lastSizeClients=0;
		lastSizeMessages=0;
		listeClients="";
		
		
	}
	
	public void distribuerNouveauxMessages() throws MalformedURLException, RemoteException {
		if(messages.size()>lastSizeMessages) {
			//cela signifie qu'il y a un nouveau message sur le serveur à délivrer
			System.out.println("Nouveau message sur le serveur");
			
			for(int i=lastSizeMessages;i<messages.size();i++) {
				Message m=messages.get(i);
				try {
					Receiver receiver=(Receiver) Naming.lookup("Receiver"+m.to);
					receiver.receive(m.from, m.contenu);
				} catch (NotBoundException e) {
					logger.log(Level.INFO, e.getMessage());
				}
				
				lastSizeMessages=messages.size();
			}
			
		}
	}
	
	public void gererArriveeDepartClient() throws MalformedURLException, RemoteException {
		if(clients.size()>lastSizeClients) {
			//cela signifie qu'un nouveau client est connecté
			System.out.println("Nouveau client");
			
			for(int j=0;j<lastSizeClients;j++) {
				String c=clients.get(j);
				//on informe tous les autres clients qu'un nouveau arrive
				try {
					Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
					for(int k=lastSizeClients;k<clients.size();k++) {
						receiver.addClient(clients.get(k));
					}
					
				} catch (NotBoundException e) {
					logger.log(Level.INFO, e.getMessage());
				}
			}
			for(int k=lastSizeClients;k<clients.size();k++) {
				    String nouveau=clients.get(k);
					//on informe les nouveaux de la liste des personnes sur le chat
					try {
						Receiver receiver=(Receiver) Naming.lookup("Receiver"+nouveau);
						receiver.initClients(clients);
					} catch (NotBoundException e) {
						logger.log(Level.INFO, e.getMessage());
					}
				
				
			}
			lastSizeClients=clients.size();
		}
		else if(clients.size()<lastSizeClients) {
			//cela signifie qu'un client s'est déconnecté
			
			System.out.println("Un client est parti");
			
			for(int j=0;j<clients.size();j++) {
				String c=clients.get(j);
				//on informe tous les autres clients qu'un client est parti
				try {
					Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
					for(String clientParti:clientsRecemmentPartis) {
						receiver.remClient(clientParti);
					}
					//on vide laliste
					clientsRecemmentPartis.clear();
					
				} catch (NotBoundException e) {
					logger.log(Level.INFO, e.getMessage());
				}
			}
			lastSizeClients=clients.size();
		}
	}
	
	public static void main(String[] args) {
	
		Server server=new Server();
		
	
		try {
			
			
			
			Boolean serverAlive=true;
			
			
			Connexion connexionComponent=new ConnectionImpl(server);
			
			
		
			Naming.rebind("Connexion", connexionComponent);
			
			System.out.println("Serveur actif");
			
		
			while(serverAlive) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					server.logger.log(Level.INFO, e1.getMessage());
					Thread.currentThread().interrupt();
				}
		
				server.distribuerNouveauxMessages();
				server.gererArriveeDepartClient();
				
				
			}
			
		} catch (RemoteException | MalformedURLException e) {
			server.logger.log(Level.INFO, e.getMessage());
		}
	}

}
