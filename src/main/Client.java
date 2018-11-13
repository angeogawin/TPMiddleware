package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.Serializable;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import composants.ClientConnection;
import composants.ClientConnectionImpl;
import composants.ClientManager;
import composants.ClientManagerImpl;
import composants.MessageBox;
import composants.MessageBoxImpl;
import composants.ServerConnection;

public class Client implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger("logger");
	 private static final String MESSAGEBOX="MessageBox";
	private String pseudo;
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<String> getClients() {
		return clients;
	}
	public void addClient(String client) {
		clients.add(client);
	}
	public void removeClient(String client) {
		clients.remove(client);
	}

	public void setClients(List<String> clients) {
		this.clients =  clients;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	private List<String> clients ;
	private List<Message> messages;
	ClientManager clientMgrComponent;
	ClientConnection clientCnxComponent;
	ServerConnection serverConnectionComponent;
	private Map<String,MessageBox> listeBox;//contient la liste des boites associée à chaque client connecté auquel ce client
	public Map<String, MessageBox> getListeBox() {
		return listeBox;
	}

	public void setListeBox(Map<String, MessageBox> listeBox) {
		this.listeBox = listeBox;
	}
	public void addBox(String a,MessageBox b) {
		listeBox.put(a, b);
	}

	int lastSizeClients;
	int lastSizeMessage;
	String listeClients;
	//pourra envoyer des messages
	
	private static boolean endSession;
	
	public Client() {
		clients = new ArrayList<>();
		messages=new ArrayList<>();
		listeBox=new HashMap<>();
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
	
	public void repondreCommande(String theLine) throws RemoteException, MalformedURLException, NotBoundException {
		if (theLine.equals("disconnect")) {
			System.out.println("le serveur va terminer cette session");
			
			serverConnectionComponent.disconnect(pseudo);
			System.out.println(pseudo+ " est déconnecté");
			pseudo="";
			clients.clear();
			for(String c:listeBox.keySet()) {
				//on supprime tous les composants messages box créés pour faire communiquer nickname et d'autres clients
		    	String nomComposant1=MESSAGEBOX+pseudo+c;
		    	String nomComposant2=MESSAGEBOX+c+pseudo;
		    	Naming.unbind(nomComposant1);
		    	Naming.unbind(nomComposant2);
			}
		}
		else if (theLine.equals("quit")) {
			System.out.println("le serveur va fermer");
			endSession=true;
			serverConnectionComponent.disconnect(pseudo);
			
		}
		else if(theLine.contains("connect")) {
			//s'enregistrer aupres du serveur
			if(pseudo==null || pseudo.equals("")) {
				pseudo=theLine.split(" ")[1];
				//on ajoute nos composants dans le registre
				clientMgrComponent=new ClientManagerImpl(this);
				clientCnxComponent=new ClientConnectionImpl(this);
				Naming.rebind("ClientManager"+pseudo, clientMgrComponent);
				Naming.rebind("ClientConnection"+pseudo, clientCnxComponent);
			   
				serverConnectionComponent.connect(pseudo, clientCnxComponent, clientMgrComponent);
				System.out.println(pseudo+ " est connecté");
			
			    //on affiche les clients deja presents sur le reseau
				if(clients.size()!=lastSizeClients) {
					lastSizeClients=clients.size();
					listeClients="";
					for (String c:clients) {
						listeClients+=c+",";
						
				
					}
					if(listeClients.equals("")) {
						System.out.println("Présents sur le réseau: Personne");
					}
					else {
						System.out.println("Présents sur le réseau: "+listeClients);
					}
				
				}
			}
			else {
				System.out.println("Veuillez vous déconnecter de "+pseudo+ "afin de changer de compte");
			}
			
			
		}
		else if(theLine.contains("sendMessage")) {
			if(pseudo!=null && !"".equals(pseudo)) {
				String to=theLine.split(";")[1];
				String contenu=theLine.split(";")[2];
				Message m=new Message(pseudo,to,contenu);
				if(listeBox.containsKey(to)) {
					//la connection entre les 2 clients existe deja
					listeBox.get(to).receive(m);
				}
				
				else {
					//1ere connection entre les 2 clients
					MessageBox msgboxReceiver=new MessageBoxImpl(this);
					Naming.rebind(MESSAGEBOX+pseudo+to, msgboxReceiver);//boite permettant à "to" decrire à "pseudo"
					listeBox.put(to, serverConnectionComponent.getClient(to).connect(pseudo, msgboxReceiver));
					listeBox.get(to).receive(m);
				}
				
				System.out.println("Envoyé!");
				
			}
			else {
				System.out.println("Veuillez vous connecter afin d'acceder au chat");
			}
			
			
		}
		
	}
	public void actualiserConsole() {
		//cette fonction permet de rafraichir la liste de messages et clients
		
		if(this.pseudo!=null && this.clients!=null) {
			
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
			if(this.messages!=null && this.messages.size()>this.lastSizeMessage) {
		
				for(int i=this.lastSizeMessage;i<this.messages.size();i++) {
					System.out.println("De: "+this.messages.get(i).from+" - "+this.messages.get(i).message);
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
			
			
			client.serverConnectionComponent = (ServerConnection) Naming.lookup("ServerConnection");
			
		
			boolean sessionStarted = false;
			while (!endSession) {
				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("Bienvenue dans Chat 2000. Le serveur comprend les requêtes suivante:\r\n" + 
							" connect pseudo |disconnect | getClients | getMessages  | sendMessage;to;message | quit "
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
