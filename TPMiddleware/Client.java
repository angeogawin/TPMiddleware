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

public class Client {
	private static String pseudo;
	static private Dialogue dialogue;
	private static boolean endSession;
	
	public Client(String pseudo) {
		this.pseudo=pseudo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hello myComponent;
		
		try {
			myComponent = (Hello) Naming.lookup("Hello");
			dialogue = (Dialogue) Naming.lookup("Dialogue");
			System.out.println(myComponent.sayHello());
			
			
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			boolean sessionStarted = false;
			while (!endSession) {
				if (!sessionStarted) {
					sessionStarted = true;
					System.out.println("entrez une requête au clavier " + "(le serveur comprend \n   [connect pseudo];\n"
							+ "  [getMessages];[getClients];[quit]\n tapez [disconnect] pour stopper la session sur le serveur\n)"
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
					if(pseudo!=null) {
					System.out.println("le serveur va terminer cette session");
					
					dialogue.disconnect(pseudo);
					}
					
					else {
						System.out.println("Vous n'étiez pas connecté");
					}
				}
				else if (theLine.equals("quit")) {
					if(pseudo!=null) {
					System.out.println("le serveur va fermer");
					endSession=true;
					dialogue.disconnect(pseudo);
					}
					
					else {
						System.out.println("Vous n'étiez pas connecté");
					}
				}
				else if(theLine.contains("connect")) {
					pseudo=theLine.split(" ")[1];
					dialogue.connect(pseudo);
					
				}
				else if(theLine.contains("sendMessage")) {
					if(pseudo!=null) {
						String to=theLine.split(";")[1];
						String contenu=theLine.split(";")[2];
						Message m=new Message(pseudo,to,contenu);
						dialogue.sendMessage(m);
						
					}
					else {
						System.out.println("Veuillez vous connecter");
					}
					
					
				}
				else if(theLine.contains("getMessages")) {
					if(pseudo!=null) {
						ArrayList<Message> messagesRecus=dialogue.getMessages(pseudo);
						for(Message m:messagesRecus) {
							System.out.println("From: "+ String.valueOf(m.getFrom()));
							System.out.println("Contenu: "+ String.valueOf(m.getMessage()));
						}
						
						
					}
					else {
						System.out.println("Veuillez vous connecter");
					}
					
				}
				
				else if(theLine.equals("getClients")) {
					if(pseudo!=null) {
						for (String a:dialogue.getClients()){
							if(!a.equals(pseudo)) {
								System.out.println(a);
							}
							
							
						}
					}
					else {
						System.out.println("Veuillez vous connecter");
					}
				}
			
				
		
			}
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
