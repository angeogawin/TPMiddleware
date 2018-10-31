
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ArrayList<String> clients;
		 ArrayList<Message> messages;
		 
		try {
			//LocateRegistry.createRegistry(8055);
			Hello myComponent = new HelloImpl();
			clients=new ArrayList<>();
			messages=new ArrayList<>();
			Boolean serverAlive=true;
			int lastSizeMessages=0;
			int lastSizeClients=0;
			
			Connexion connexionComponent=new ConnectionImpl(clients,messages);
			
			
			Naming.rebind("Hello", myComponent);//rebinds the specified name to a new remote object
			Naming.rebind("Connexion", connexionComponent);
			
			System.out.println("Serveur actif");
			
			
			while(serverAlive) {
				
				if(messages.size()>lastSizeMessages) {
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
					lastSizeClients=clients.size();
					
					for(String c:clients) {
						try {
							Receiver receiver=(Receiver) Naming.lookup("Receiver"+c);
							receiver.addClient(clients.get((lastSizeClients)-1));
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				
			}
			
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
