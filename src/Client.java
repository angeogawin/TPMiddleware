package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import composants.Connexion;
import composants.Emitter;
import composants.Receiver;
import composants.ReceiverImpl;



public class Client implements Serializable{
	public String pseudo;
	private Connexion connexionComponent;
	Emitter emitterComponent;
	public ArrayList<String> clients;
	public ArrayList<Message> messages;
	String listeClients;
	 int lastSizeClients;
	int lastSizeMessage;

	boolean endSession;
	Logger logger = Logger.getLogger("logger");
	public Client() {
		
		clients=new ArrayList<>();
		
		messages=new ArrayList<>();
		lastSizeClients=0;
		lastSizeMessage=0;
		listeClients="";
		
		
	}
	public String lireCommande() {
		String theLine="";
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			theLine = userIn.readLine();
			
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		
		return theLine;
	}
	
	public void repondreCommande(String theLine) throws RemoteException, MalformedURLException {
		
		if (theLine.equals("disconnect")) {
			System.out.println("le serveur va terminer cette session");
			
			connexionComponent.disconnect(pseudo);
			System.out.println(pseudo+ " est déconnecté");
			pseudo="";
		}
		else if (theLine.equals("quit")) {
			System.out.println("le serveur va fermer");
			endSession=true;
			connexionComponent.disconnect(pseudo);
			
		}
		else if(theLine.contains("connect")) {
			if(pseudo==null || pseudo.equals("")) {
				
				pseudo=theLine.split(" ")[1];
				
				Receiver receiverComponent=new ReceiverImpl(this);
				Naming.rebind("Receiver"+pseudo, receiverComponent);
				emitterComponent=connexionComponent.connect(pseudo,receiverComponent);
				System.out.println(pseudo+ " est connecté");
				if(clients!=null) {
					
					lastSizeClients=clients.size();
				
				//on affiche la liste des clients connectés
				for (String c:clients) {
					listeClients+=c+",";
					System.out.println("Présents sur le réseau: "+listeClients);
			
				}
				}
				//on affiche les messages recus
				if(messages!=null) {
					lastSizeMessage=messages.size();
				
				
				for (Message m:messages) {
					
					System.out.println("De: "+m.from+" - "+m.contenu);
			
				}
				}
			}
			else {
				System.out.println("Veuillez vous déconnecter de "+pseudo+ "afin de changer de compte");
			}
			
			
		}
		else if(theLine.contains("sendMessage")) {
			if(pseudo!=null) {
				String to=theLine.split(";")[1];
				String contenu=theLine.split(";")[2];
				Message m=new Message(pseudo,to,contenu);
				emitterComponent.sendMessage(m);
				System.out.println("Envoyé!");
				
			}
			else {
				System.out.println("Veuillez vous connecter afin d'acceder au chat");
			}
			
			
		}
		
	}
	
	public void actualiserConsole() {
		//cette fonction permet de rafraichir la liste de messages et clients
		
		if(this.pseudo!=null) {
			if(this.clients!=null && this.clients.size()!=this.lastSizeClients) {
		
				this.lastSizeClients=this.clients.size();
				this.listeClients="";
				for (String c:this.clients) {
					this.listeClients+=c+",";
					
			
				}
				if(this.listeClients.equals("")) {
					System.out.println("Présents sur le réseau: Personne");
				}
				else {
					System.out.println("Présents sur le réseau: "+this.listeClients);
				}
			
			
			}
			if(this.messages!=null && this.messages.size()>this.lastSizeMessage) {
			
				for(int i=this.lastSizeMessage;i<this.messages.size();i++) {
					System.out.println("De: "+this.messages.get(i).from+" - "+this.messages.get(i).contenu);
				}
				
				this.lastSizeMessage=this.messages.size();
						
			
			}
		}
	}

	public static void main(String[] args) {
		
	
		Client client=new Client();
		
		
		new java.util.Timer().scheduleAtFixedRate( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                // on rafraichit la liste des clients et messages regulierement
		            	client.actualiserConsole();
		            }
		        }, 
		        1000 ,1000
		);
		try {
			
			
			client.connexionComponent = (Connexion) Naming.lookup("Connexion");
			
			

			
			
			
			
			boolean sessionStarted = false;
			while (!client.endSession) {

				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("Bienvenue dans Chat 2000, entrez une requête au clavier " );
					System.out.println( "Liste des requêtes:\n "
							+ "connect pseudo "
							+ "disconnect pseudo "
							+ "sendMessage;destinataire;Contenu");
							
							
				}
				
				
				client.actualiserConsole();
				String theLine="";
				theLine=client.lireCommande();
				client.repondreCommande(theLine);
				
			
				
		
			}
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			client.logger.log(Level.INFO, e.getMessage());
		}
		
		
		
		
	}
	


	
}
