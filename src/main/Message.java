package main;
import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String from;
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getMessage() {
		return message;
	}
	
	String to;
	String message;
	public Message(String from, String to, String message) {
		super();
		this.from = from;
		this.to = to;
		this.message = message;
	}
	
	

}
