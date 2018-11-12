package main;

import java.io.Serializable;

public class Message implements Serializable {
	String from;
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getContenu() {
		return contenu;
	}
	
	String to;
	String contenu;
	public Message(String from, String to, String message) {
		super();
		this.from = from;
		this.to = to;
		this.contenu = message;
	}
	
	

}
