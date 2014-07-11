package com.server.network;


/*
 * This class will be used to manage the ip address 
 * and port of the server and client.
 */


public class Address {
	String ip;
	int port;
	
	Address(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void setIP(String ip){
		this.ip = ip;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
}
