/*ChatServer in java*/

import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TestServer {
	public static void main(String[] args) throws IOException {
		MainServer comms = new MainServer();
		Thread t = new Thread(comms);
		t.start();		
	}
}

class MainServer implements Runnable {
	private static ArrayList<TCPServerThread> threadList = new ArrayList<TCPServerThread>();
	private static ArrayList<String> userList = new ArrayList<String>();
	public void run() {
		try {
			//create server socket
			int portNum = 2552;
			ServerSocket serverSocket = new ServerSocket(portNum);

			//confirm port is running on the correct port
			System.out.println("Server is running on port " + serverSocket.getLocalPort());
		
			//accept client
			while(true){
				Socket clientSocket = serverSocket.accept();
				TCPServerThread thread = new TCPServerThread(this,clientSocket);
				System.out.println("Adding new user...");
				threadList.add(thread);
				System.out.println("User added");
				thread.start();
				System.out.println("Connection established with IP: " + 
					clientSocket.getInetAddress().getHostAddress());		
			}
		} catch (IOException e) { }
	}

	/***chat methods***/

	//adds user to list
	public void newUser(String user) {
		System.out.println("Adding " + user + " to chat list...");
		userList.add(user);
		System.out.println(user + " added");
	}
	
	//sends chat message to all users on server
	public void sendToAll(String user, String msg, int i) throws IOException {
		System.out.println("Broadcasting message from " + user + "...");
		Iterator<TCPServerThread> iter = threadList.iterator();
		while(iter.hasNext()){
			iter.next().send(user, msg, i);
		}
	}
	
	//gets a list of all the users currently on the server
	public String getUsers() {
		System.out.println("Getting user list");
		String list = Arrays.toString(userList.toArray());
		return list;
	}
	
	//drops a user from the server and removes them from the list
	public void dropUser(String user, TCPServerThread thread) {
		System.out.println("Dropping " + user + " from the chat...");
		threadList.remove(thread);
		userList.remove(user);
		System.out.println(user + " successfully dropped from chat.");
	}
}

class TCPServerThread extends Thread {
	private Socket clientSocket = null;
	private MainServer server = null;
	TCPServerThread(Socket clientSocket) {
		super("TCPServerThread");
		this.clientSocket = clientSocket;
	}
	
	TCPServerThread(MainServer server, Socket clientSocket) {
		super("TCPServerThread");
		this.server = server;
		this.clientSocket = clientSocket;
	}

	public void run() {
		try{	
			/***receive data from client***/
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//get username
			System.out.println("Getting username...");
			String username;
			String prompt1 = "What is your username?\n";
			clientSocket.getOutputStream().write(prompt1.getBytes("UTF-8"));
			username = in.readLine();
			if(username == null || username.isEmpty()) {
				String prompt2 = "Invalid username. Enter another username\n";
				clientSocket.getOutputStream().write(prompt2.getBytes("UTF-8"));
				username = in.readLine();
			}
			server.newUser(username); //add user to chat list
			server.sendToAll(username, " has joined the chat", 1);
			String welcome = "Welcome " + username + "! Enter your message below\n";
			clientSocket.getOutputStream().write(welcome.getBytes("UTF-8"));
			
			//get user chat messages/commands
			String inputLine; 
			while(true){
				inputLine  = in.readLine();
				if(!inputLine.isEmpty()){
					if(inputLine.equals("exit")) {
						System.out.println("User wants to exit");
						server.dropUser(username, this);
						server.sendToAll(username, " has left the chat", -1);
						//clientSocket.close();
					} else if (inputLine.equals("list")) {
						System.out.println("User wants the list of users");
						String list = server.getUsers() + "\n";
						clientSocket.getOutputStream().write(list.getBytes("UTF-8"));
					} else {
						System.out.println("Message from " + username + ": " + inputLine);
						server.sendToAll(username, inputLine, 0);
					}
				}
			}
			
		} catch(IOException e) { } catch(NullPointerException n) { }
	}
	
	//sends message to user
	public void send(String username, String msg, int i) throws IOException {
		String finalmsg;
		//make standard chat message		
		if(i == 0) {
			finalmsg = username + ": " + msg + "\n";
		}
		//user joined message		
		else if (i > 0) {
			finalmsg = username + msg + "\n";
		} 
		//user dropped from chat message		
		else {
			finalmsg = username + msg + "\n";
		}
		clientSocket.getOutputStream().write(finalmsg.getBytes("UTF-8"));
	}
}
