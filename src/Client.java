package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sun.security.jca.GetInstance;

public class Client {
	 String pseudo;
	static private Connexion connexionComponent;
	Emitter emitterComponent;
	 ArrayList<String> clients;
	ArrayList<Message> messages;
	String listeClients;
	 int lastSizeClients;
	int lastSizeMessage;

	boolean endSession;
	
	public Client() {
		
		clients=new ArrayList<>();
		
		messages=new ArrayList<>();
		lastSizeClients=0;
		lastSizeMessage=0;
		listeClients="";
		
		
	}
	
	public void actualiserConsole() {
		//cette fonction permet de rafraichir la liste de messages et clients
		
		if(this.pseudo!=null) {
			if(this.clients!=null) {
			if(this.clients.size()!=this.lastSizeClients) {
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
			}
			if(this.messages!=null) {
			if(this.messages.size()>this.lastSizeMessage) {
				for(int i=this.lastSizeMessage;i<this.messages.size();i++) {
					System.out.println("De: "+this.messages.get(i).from+" - "+this.messages.get(i).message);
				}
				
				this.lastSizeMessage=this.messages.size();
						
			}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hello myComponent;
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
			
			myComponent = (Hello) Naming.lookup("Hello");
			connexionComponent = (Connexion) Naming.lookup("Connexion");
			
			
			System.out.println(myComponent.sayHello());
			
			
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
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
				try {
					theLine = userIn.readLine();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				if (theLine.equals("disconnect")) {
					System.out.println("le serveur va terminer cette session");
					
					connexionComponent.disconnect(client.pseudo);
					System.out.println(client.pseudo+ " est déconnecté");
					client.pseudo="";
				}
				else if (theLine.equals("quit")) {
					System.out.println("le serveur va fermer");
					client.endSession=true;
					connexionComponent.disconnect(client.pseudo);
					
				}
				else if(theLine.contains("connect")) {
					if(client.pseudo==null || client.pseudo.equals("")) {
						
						client.pseudo=theLine.split(" ")[1];
						
						Receiver receiverComponent=new ReceiverImpl(client);
						Naming.rebind("Receiver"+client.pseudo, receiverComponent);
						client.emitterComponent=connexionComponent.connect(client.pseudo,receiverComponent);
						System.out.println(client.pseudo+ " est connecté");
						if(client.clients!=null) {
							
							client.lastSizeClients=client.clients.size();
						
						//on affiche la liste des clients connectés
						for (String c:client.clients) {
							client.listeClients+=c+",";
							System.out.println("Présents sur le réseau: "+client.listeClients);
					
						}
						}
						//on affiche les messages recus
						if(client.messages!=null) {
							client.lastSizeMessage=client.messages.size();
						
						
						for (Message m:client.messages) {
							
							System.out.println("De: "+m.from+" - "+m.message);
					
						}
						}
					}
					else {
						System.out.println("Veuillez vous déconnecter de "+client.pseudo+ "afin de changer de compte");
					}
					
					
				}
				else if(theLine.contains("sendMessage")) {
					if(client.pseudo!=null) {
						String to=theLine.split(";")[1];
						String contenu=theLine.split(";")[2];
						Message m=new Message(client.pseudo,to,contenu);
						client.emitterComponent.sendMessage(m);
						System.out.println("Envoyé!");
						
					}
					else {
						System.out.println("Veuillez vous connecter afin d'acceder au chat");
					}
					
					
				}
				
				
		
			}
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	


	
}
