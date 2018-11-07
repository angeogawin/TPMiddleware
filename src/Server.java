package src;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
	ArrayList<String> clients;
	ArrayList<Message> messages;
	int lastSizeClients;
	int lastSizeMessages;
	String listeClients;
	ArrayList<String> clientsRecemmentPartis;
	
	public Server() {
		
		clients=new ArrayList<>();
		
		messages=new ArrayList<>();
		clientsRecemmentPartis=new ArrayList<>();
		lastSizeClients=0;
		lastSizeMessages=0;
		listeClients="";
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
		
	
		try {
			//LocateRegistry.createRegistry(8055);
			Hello myComponent = new HelloImpl();
			
			Boolean serverAlive=true;
			
			
			Connexion connexionComponent=new ConnectionImpl(server);
			
			
			Naming.rebind("Hello", myComponent);//rebinds the specified name to a new remote object
			Naming.rebind("Connexion", connexionComponent);
			
			System.out.println("Serveur actif");
			
		
			while(serverAlive) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			//	System.out.println(String.valueOf(clients.size()));
				if(server.messages.size()>server.lastSizeMessages) {
					//cela signifie qu'il y a un nouveau message sur le serveur à délivrer
					System.out.println("Nouveau message sur le serveur");
					
					for(int i=server.lastSizeMessages;i<server.messages.size();i++) {
						Message m=server.messages.get(i);
						try {
							Receiver receiver=(Receiver) Naming.lookup("Receiver"+m.to);
							receiver.receive(m.from, m.message);
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						server.lastSizeMessages=server.messages.size();
					}
					
				}
				if(server.clients.size()>server.lastSizeClients) {
					//cela signifie qu'un nouveau client est connecté
					System.out.println("Nouveau client");
					
					for(int j=0;j<server.lastSizeClients;j++) {
						String c=server.clients.get(j);
						//on informe tous les autres clients qu'un nouveau arrive
						try {
							Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
							for(int k=server.lastSizeClients;k<server.clients.size();k++) {
								receiver.addClient(server.clients.get(k));
							}
							
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					for(int k=server.lastSizeClients;k<server.clients.size();k++) {
						    String nouveau=server.clients.get(k);
							//on informe les nouveaux de la liste des personnes sur le chat
							try {
								Receiver receiver=(Receiver) Naming.lookup("Receiver"+nouveau);
								receiver.initClients(server.clients);
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						
					}
					server.lastSizeClients=server.clients.size();
				}
				else if(server.clients.size()<server.lastSizeClients) {
					//cela signifie qu'un client s'est déconnecté
					
					System.out.println("Un client est parti");
					
					for(int j=0;j<server.clients.size();j++) {
						String c=server.clients.get(j);
						//on informe tous les autres clients qu'un client est parti
						try {
							Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
							for(String clientParti:server.clientsRecemmentPartis) {
								receiver.remClient(clientParti);
							}
							//on vide laliste
							server.clientsRecemmentPartis.clear();
							
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					server.lastSizeClients=server.clients.size();
				}
				
				
			}
			
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
