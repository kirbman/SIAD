/*chatserver in java*/

import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		MainServer comms = new MainServer();
		Thread t = new Thread(comms);
		t.start();		
	}
}

class MainServer implements Runnable {
	private static ArrayList<TCPServerThread> userList = new ArrayList<TCPServerThread>();
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
				TCPServerThread thread = new TCPServerThread(clientSocket);
				thread.start();
				//System.out.println("Adding new user...");
				//userList.add(thread);
				//System.out.println("User added");
				System.out.println("Connection established with IP: " + 
					clientSocket.getInetAddress().getHostAddress());		
			}
		} catch (IOException e) { }
	}

	/***chat methods***/
	adds user to list
	public void join(TCPServerThread thread) {
		System.out.println("Adding new user...");
		userList.add(thread);
		System.out.println("User added");
		
	}
	
	//sends chat message to all users on server
	public void sendToAll(String user) {
		System.out.println("Broadcasting message from " + user);
	}
	
	//gets a list of all the users currently on the server
	public void getUsers() {
		System.out.println("Getting user list");
	}
	
	//drops a user from the server and removes them from the list
	public void dropUser(String user) {
		System.out.println("Dropping " + user + " from the chat...");
	}
}

class TCPServerThread extends Thread {
	private Socket clientSocket = null;
	private MainServer server = new MainServer();
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
			//server.join();
			//receive data from client
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
			String welcome = "Welcome " + username + "!\n";
			clientSocket.getOutputStream().write(welcome.getBytes("UTF-8"));
			
			//get user chat messages/commands
			String inputLine; 
			while(true){
				inputLine  = in.readLine();
				if(!inputLine.isEmpty()){
					if(inputLine.equals("exit")) {
						System.out.println("User wants to exit");
						server.dropUser(username);
						clientSocket.close();
					} else if (inputLine.equals("list")) {
						System.out.println("User wants the list of users");
						server.getUsers();
					} else {
						System.out.println("Received Data from Client: " + inputLine);
						server.sendToAll(username);
					}
				}
		
				//send data back to client
				String response = inputLine + "\n";		
				clientSocket.getOutputStream().write(response.getBytes("UTF-8"));
				//clientSocket.close();
			}
			
		} catch(IOException e) { } catch(NullPointerException n) { }
	}
}
