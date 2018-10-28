
import java.net.MalformedURLException;
import java.rmi.Naming;
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
			
			Connexion connexionComponent=new ConnectionImpl(clients,messages);
			
			Naming.rebind("Hello", myComponent);//rebinds the specified name to a new remote object
			Naming.rebind("Connexion", connexionComponent);
			System.out.println("Serveur actif");
			
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
