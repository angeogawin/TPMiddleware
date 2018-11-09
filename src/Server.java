
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	
	ArrayList<String> clients;
	ArrayList<Message> messages;
	HashMap<String,ClientConnection> listeClientConnection;//associe a chaque nickname un "clientconnection"
	HashMap<String,ClientManager> listeClientManager;//associe a chaque nickname un "clientmanager"
	
	public Server() {
		clients=new ArrayList<>();
		messages=new ArrayList<>();
		listeClientConnection=new HashMap<>();
		listeClientManager=new HashMap<>();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 
		try {
			//LocateRegistry.createRegistry(8055);
			Server server=new Server();
			ServerConnection serverConnection=new ServerConnectionImpl(server);
			
			Boolean serverAlive=true;
			int lastSizeMessages=0;
			int lastSizeClients=0;
			
			Naming.rebind("ServerConnection", serverConnection);
			
			
			
			
			System.out.println("Serveur actif");
			
			/*	
			while(serverAlive) {
			
				if(messages.size()>lastSizeMessages) {
					//cela signifie qu'il y a un nouveau message sur le serveur à délivrer
					lastSizeMessages=messages.size();
					Message m=messages.get(lastSizeMessages-1);
					try {
						Receiver receiver=(Receiver) Naming.lookup("Receiver"+m.to);
						receiver.receive(m.from, m.message);
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(clients.size()>lastSizeClients) {
					//cela signifie qu'un nouveau client est connecté
					lastSizeClients=clients.size();
					
					for(String c:clients) {
						if(!c.equals(clients.get(lastSizeClients-1))) {
							//on informe tous les autres clients qu'un nouveau arrive
							try {
								Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
								receiver.addClient(clients.get((lastSizeClients)-1));
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							//on informe le nouveau de la liste des personnes sur le chat
							try {
								Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
								receiver.initClients(clients);
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				else if(clients.size()<lastSizeClients) {
					//cela signifie qu'un client s'est déconnecté
					lastSizeClients=clients.size();
					for(String c:clients) {
						if(!c.equals(clients.get(lastSizeClients-1))) {
							//on informe tous les autres clients qu'un client est parti
							try {
								Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
								receiver.addClient(clients.get((lastSizeClients)-1));
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							//on informe le nouveau de la liste des personnes sur le chat
							try {
								Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
								receiver.initClients(clients);
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				
				
			}*/
			
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
