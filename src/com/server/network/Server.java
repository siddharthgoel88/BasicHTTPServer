package com.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.requests.RequestHandler;

public class Server {
	private int serverPort;
	private int numConnections;
	private ServerSocket serverSocket = null;
	private static Server server = new Server();
	
	private Server() {
		setNumConnections(0);
		setServerPort(80); //default port
	}
	
	public static Server getInstance() {
		return server;
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("Error in creating server socket:" + e);
		}
		System.out.println("Server started at port:" + serverPort);
		
		Socket clientSocket;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				incrementConnections();
				RequestHandler reqHan = new RequestHandler(clientSocket, getNumConnections());
				new Thread(reqHan).start();
			} catch (IOException e) {
				System.out.println("Some error in client connection:"+e);
			}
		}	
	}
	
	public static void main(String[] args) {
		Server srv = Server.getInstance();
		if (args.length > 1) {
			System.out.println("Usage: java Server <port_number>");
			System.exit(1);
		} else if (args.length == 1) {
			srv.setServerPort(Integer.parseInt(args[0]));
		}
		srv.init();
	}

	private void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	private int getNumConnections() {
		return numConnections;
	}

	private void setNumConnections(int numConnections) {
		this.numConnections = numConnections;
	}
	
	public void incrementConnections() {
		setNumConnections(getNumConnections() + 1);
	}
	
	public void decrementConnections() {
		setNumConnections(getNumConnections() - 1);
	}

}
