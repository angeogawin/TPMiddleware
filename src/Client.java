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
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
	String pseudo;
	ArrayList<String> clients ;
	ArrayList<Message> messages;
	ClientManager clientMgrComponent;
	ClientConnection clientCnxComponent;
	ServerConnection serverConnectionComponent;
	HashMap<String,MessageBox> listeBox;//contient la liste des boites associée à chaque client connecté auquel ce client
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
			
			
			
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			boolean sessionStarted = false;
			while (!endSession) {
				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("Bienvenue dans Chat 2000. Le serveur comprend les requêtes suivante:\r\n" + 
							" connect pseudo |disconnect | getClients | getMessages  | sendMessage;to;message | quit "
							);
				}

				String theLine="";
				try {
					theLine = userIn.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (theLine.equals("disconnect")) {
					System.out.println("le serveur va terminer cette session");
					
					client.serverConnectionComponent.disconnect(client.pseudo);
					System.out.println(client.pseudo+ " est déconnecté");
					client.pseudo="";
					client.clients.clear();
					for(String c:client.listeBox.keySet()) {
						//on supprime tous les composants messages box créés pour faire communiquer nickname et d'autres clients
				    	String nomComposant1="MessageBox"+client.pseudo+c;
				    	String nomComposant2="MessageBox"+c+client.pseudo;
				    	Naming.unbind(nomComposant1);
				    	Naming.unbind(nomComposant2);
					}
				}
				else if (theLine.equals("quit")) {
					System.out.println("le serveur va fermer");
					endSession=true;
					client.serverConnectionComponent.disconnect(client.pseudo);
					
				}
				else if(theLine.contains("connect")) {
					//s'enregistrer aupres du serveur
					if(client.pseudo==null || client.pseudo.equals("")) {
						client.pseudo=theLine.split(" ")[1];
						//on ajoute nos composants dans le registre
						client.clientMgrComponent=new ClientManagerImpl(client);
						client.clientCnxComponent=new ClientConnectionImpl(client);
						Naming.rebind("ClientManager"+client.pseudo, client.clientMgrComponent);
						Naming.rebind("ClientConnection"+client.pseudo, client.clientCnxComponent);
					   
						client.serverConnectionComponent.connect(client.pseudo, client.clientCnxComponent, client.clientMgrComponent);
						System.out.println(client.pseudo+ " est connecté");
					
					    //on affiche les clients deja presents sur le reseau
						if(client.clients.size()!=client.lastSizeClients) {
							client.lastSizeClients=client.clients.size();
							client.listeClients="";
							for (String c:client.clients) {
								client.listeClients+=c+",";
								
						
							}
							if(client.listeClients.equals("")) {
								System.out.println("Présents sur le réseau: Personne");
							}
							else {
								System.out.println("Présents sur le réseau: "+client.listeClients);
							}
						
						}
					}
					else {
						System.out.println("Veuillez vous déconnecter de "+client.pseudo+ "afin de changer de compte");
					}
					
					
				}
				else if(theLine.contains("sendMessage")) {
					if(client.pseudo!=null && !"".equals(client.pseudo)) {
						String to=theLine.split(";")[1];
						String contenu=theLine.split(";")[2];
						Message m=new Message(client.pseudo,to,contenu);
						if(client.listeBox.containsKey(to)) {
							//la connection entre les 2 clients existe deja
							client.listeBox.get(to).receive(m);
						}
						
						else {
							//1ere connection entre les 2 clients
							MessageBox msgboxReceiver=new MessageBoxImpl(client);
							Naming.rebind("MessageBox"+client.pseudo+to, msgboxReceiver);//boite permettant à "to" decrire à "client.pseudo"
							client.listeBox.put(to, client.serverConnectionComponent.getClient(to).connect(client.pseudo, msgboxReceiver));
							client.listeBox.get(to).receive(m);
						}
						
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
