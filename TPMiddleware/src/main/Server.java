package main;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import composants.Dialogue;
import composants.DialogueImpl;
import composants.HelloImpl;

public class Server implements Serializable{
	public ArrayList<String> clients;
	public ArrayList<Message> messages;
	
	public Server() {
		clients=new ArrayList<>();
		messages=new ArrayList<>();
	}
	

	public static void main(String[] args) {
		
	    Server server=new Server();
		 
		try {
			
			
			
			Dialogue dialogue =new DialogueImpl(server);
			
		
			Naming.rebind("Dialogue", dialogue);
			System.out.println("Serveur actif");
			
		} catch (RemoteException | MalformedURLException e) {
			
			e.printStackTrace();
		}
	}

}
