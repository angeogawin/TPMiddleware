package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import composants.Connexion;
import composants.Dialogue;


public class Client {
	private  String pseudo;
	private static Connexion connexionComponent;
	private static Dialogue dialogue;
	private  boolean endSession;
	private static final String AVERTISSEMENT_CONNEXION="Veuillez vous connecter afin d'acceder au chat";
	
	Logger logger = Logger.getLogger("logger");
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
    
    public void repondreCommande(String theLine) throws RemoteException {
    	
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
				dialogue=connexionComponent.connect(pseudo);
				System.out.println(pseudo+ " est connecté");
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
				dialogue.sendMessage(m);
				System.out.println("Envoyé!");
				
			}
			else {
				System.out.println(AVERTISSEMENT_CONNEXION);
			}
			
			
		}
		else if(theLine.contains("getMessages")) {
			if(pseudo!=null) {
				List<Message> messagesRecus=dialogue.getMessages();
				for(Message m:messagesRecus) {
					System.out.println("From: "+m.getFrom());
					System.out.println("Contenu: "+ m.getContenu());
				}
				
				if(messagesRecus.isEmpty()) {
					System.out.println("Aucun message recu");
				}
				
				
			}
			else {
				System.out.println(AVERTISSEMENT_CONNEXION);
			}
			
		}
		
		else if(theLine.equals("getClients")) {
			if(pseudo!=null) {
				for (String a:dialogue.getClients()){
				
						System.out.println(a);
					
				
				
				}
				if(dialogue.getClients().isEmpty()) {
					System.out.println("Aucun autre client connecté :(");
				}
			
			}
			else {
				System.out.println(AVERTISSEMENT_CONNEXION);
			}
		}
		
    	
    }
	public static void main(String[] args) {
		
	
		Client client=new Client();
		
		try {
		
			connexionComponent = (Connexion) Naming.lookup("Connexion");
			
			boolean sessionStarted = false;
			while (!client.endSession) {
				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("Bienvenue dans Chat 2000. Le serveur comprend les requêtes suivante:\r\n" + 
							" connect pseudo |disconnect | getClients | getMessages  | sendMessage;to;message | quit \n"
						
							);
				}

				String theLine="";
				theLine=client.lireCommande();
				client.repondreCommande(theLine);
		
		
			}
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
	
			client.logger.log(Level.INFO, e.getMessage());
		}
		
		
	}
	
}
