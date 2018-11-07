

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
	ArrayList<String> clients;
	ArrayList<Message> messages;
	public Server() {
		clients=new ArrayList<>();
		messages=new ArrayList<>();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
		try {
			//LocateRegistry.createRegistry(8055);
			Hello myComponent = new HelloImpl();
			
			
			Connexion connexionComponent=new ConnectionImpl(server);
			
			Naming.rebind("Hello", myComponent);//rebinds the specified name to a new remote object
			Naming.rebind("Connexion", connexionComponent);
			System.out.println("Serveur actif");
			
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
