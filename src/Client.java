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
	//pourra envoyer des messages
	
	private static boolean endSession;
	
	public Client() {
		clients = new ArrayList<>();
		messages=new ArrayList<>();
		listeBox=new HashMap<>();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client=new Client();
		
		 
		
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
				}
				else if (theLine.equals("quit")) {
					System.out.println("le serveur va fermer");
					endSession=true;
					client.serverConnectionComponent.disconnect(client.pseudo);
					
				}
				else if(theLine.contains("cs")) {
					//s'enregistrer aupres du serveur
					if(client.pseudo==null || client.pseudo.equals("")) {
						client.pseudo=theLine.split(" ")[1];
						//on ajoute nos composants dans le registre
						client.clientMgrComponent=new ClientManagerImpl(client);
						client.clientCnxComponent=new ClientConnectionImpl();
						Naming.rebind("ClientManager"+client.pseudo, client.clientMgrComponent);
						Naming.rebind("ClientConnection"+client.pseudo, client.clientCnxComponent);
					   
						client.serverConnectionComponent.connect(client.pseudo, client.clientCnxComponent, client.clientMgrComponent);
						System.out.println(client.pseudo+ " est connecté");
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
