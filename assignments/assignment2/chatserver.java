/*chatserver in java*/

import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class chatserver {
	private List<TCPServerThread> userList = new ArrayList<TCPServerThread>();
	public static void main(String[] args) throws IOException {
		
		//create server socket
		int portNum = 2552;
		ServerSocket serverSocket = new ServerSocket(portNum);
		//confirm port is running on the correct port
		System.out.println("Server is running on port " + serverSocket.getLocalPort());
		
		//accept client
		while(true){
			Socket clientSocket = serverSocket.accept();
			new TCPServerThread(clientSocket).start();
			System.out.println("Connection established with IP: " + clientSocket.getInetAddress().getHostAddress());		
		}
	}
}

class TCPServerThread extends Thread {
	private Socket clientSocket = null;
	
	TCPServerThread(Socket clientSocket) {
		super("TCPServerThread");
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		try{	
		//receive data from client
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine; 
			//String receivedData = inputLine;
			while(true){
				inputLine  = in.readLine();
				if(!inputLine.isEmpty()){
					if(inputLine.equals("exit")) {
						System.out.println("User wants to exit");
						
					} else if (inputLine.equals("list")) {
						System.out.println("User wants the list of users");
						clientSocket.getOutputStream().write(userList.getBytes("UTF-8"));
					} else {
						System.out.println("Received Data from Client: " + inputLine);
					}
				}
		
				//send data back to client
				String response = inputLine + "\n";		
				clientSocket.getOutputStream().write(response.getBytes("UTF-8"));
				//clientSocket.close();
			}
			
		} catch(IOException e) { }
	}
}
