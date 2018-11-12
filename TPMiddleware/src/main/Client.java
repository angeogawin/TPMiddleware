package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import composants.Dialogue;


public class Client {
	private String pseudo;
	private boolean endSession;
	Dialogue dialogue;
	
	public static final String avertissementConnexion="Veuillez vous connecter";
	public Client() {
		
	}
	
	public String lireCommande() {
		String theLine="";
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			theLine = userIn.readLine();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return theLine;
	}
	
	public void repondreCommande(String commande) throws MalformedURLException, RemoteException, NotBoundException {
		dialogue = (Dialogue) Naming.lookup("Dialogue");
		if (commande.equals("disconnect")) {
			if(pseudo!=null) {
			System.out.println("le serveur va terminer cette session");
			
			dialogue.disconnect(pseudo);
			System.out.println("Deconnecté");
			}
			
			else {
				System.out.println("Vous n'étiez pas connecté");
			}
		}
		else if (commande.equals("quit")) {
			if(pseudo!=null) {
			System.out.println("le serveur va fermer");
			endSession=true;
			dialogue.disconnect(pseudo);
			}
			
			else {
				System.out.println("Vous n'étiez pas connecté");
			}
		}
		else if(commande.contains("connect")) {
			if(pseudo!=null) {
				System.out.println("Veuillez d'abord vous déconnecter de "+ pseudo);
			}
			else {
				pseudo=commande.split(" ")[1];
				dialogue.connect(pseudo);
				System.out.println(pseudo+" est connecté");
			}
			
		}
		else if(commande.contains("sendMessage")) {
			if(pseudo!=null) {
				String to=commande.split(";")[1];
				String contenu=commande.split(";")[2];
				Message m=new Message(pseudo,to,contenu);
				dialogue.sendMessage(m);
				System.out.println("Envoyé!");
				
			}
			else {
				System.out.println(avertissementConnexion);
			}
			
			
		}
		else if(commande.contains("getMessages")) {
			if(pseudo!=null) {
				ArrayList<Message> messagesRecus=(ArrayList<Message>) dialogue.getMessages(pseudo);
				if(messagesRecus.isEmpty()) {
					System.out.println("Aucun message");
				}
				for(Message m:messagesRecus) {
					System.out.println("De: "+ m.getFrom());
					System.out.println("Contenu: "+ m.getContenu());
				}
				
				
			}
			else {
				System.out.println(avertissementConnexion);
			}
			
		}
		
		else if(commande.equals("getClients")) {
			if(pseudo!=null) {
				StringBuilder bld=new StringBuilder();
				bld.append(" - ");
				for (String a:dialogue.getClients()){
					if(!a.equals(pseudo)) {
						bld.append(a+", ");
						
					}
					
					
				}
				System.out.println(bld.toString());
			}
			else {
				System.out.println(avertissementConnexion);
			}
		}
	
	}

	public static void main(String[] args) {

        Client client=new Client();
		
		try {
	
			boolean sessionStarted = false;
			while (!client.endSession) {
				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("Bienvenue dans Chat 2000. Le serveur comprend les requêtes suivante:\n"
							+ " connect pseudo |disconnect | getClients | getMessages  | sendMessage;to;message | quit \n\n"
							);
				}

				String theLine="";
				theLine=client.lireCommande();
				client.repondreCommande(theLine);
				
			}
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}
