package com.server.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.server.network.Server;
import com.server.response.ResponseGenerator;

public class RequestHandler implements Runnable {
	private Socket client;
	private int id;
	private BufferedReader inputStream;
	private PrintStream outputStream;
	private String clientInput;
	private String finalResponse;
	
	public RequestHandler(Socket client, int id) {
		this.client = client;
		this.id = id;
		clientInput = "";
		finalResponse = "";
		try {
			inputStream = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			outputStream = new PrintStream(this.client.getOutputStream());
		} catch (IOException e) {
			System.out.println("Issue in getting input/output stream for client with id=" + id + " :" + e);
		}
	}
	
	private void readInput() {
		String input = "";
		try {
			while ((input = inputStream.readLine()) != null  && input.length() > 0)
				clientInput += input + "\n";
		} catch (IOException e) {
			System.out.println("Error in reading input from client with id=" + id + " :" + e);
		}
	}
	
	private void processInput() {
		ResponseGenerator resp = new ResponseGenerator(clientInput);
		finalResponse = resp.generateResponse();
	}
	
	private void sendResponse() {
		/*String temp= "HTTP/1.0 200 OK\n" +
					"Date: Fri, 31 Dec 1999 23:59:59 GMT\n" +
					"Content-Type: text/html\n" +
					"Content-Length: 43\n\n" +
					"<html><body><h1>pavan :)</h1></body></html>"; 
		outputStream.println(temp); 
		System.out.println("The message from client id=" + this.id + 
				" and ip=" + client.getInetAddress() + " is:\n"
				+ finalResponse);*/
		
		outputStream.println(finalResponse);
		
		try {
			Server.getInstance().decrementConnections();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readInput();
		processInput();
		sendResponse();
	}

}
