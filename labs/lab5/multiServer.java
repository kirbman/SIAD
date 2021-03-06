/*multiServer in java*/

import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;

public class multiServer {
	public static void main(String[] args) throws IOException {
		//create server socket
		int portNum = 2552;
		ServerSocket serverSocket = new ServerSocket(portNum);
		//confirm port is running on the correct port
		System.out.println("MultiServer is running on port " + serverSocket.getLocalPort());
		
		//accept client
		while(true){
			Socket clientSocket = serverSocket.accept();
			new TCPServerThread(clientSocket).start();
			System.out.println("Connection established with IP: " + clientSocket.getInetAddress().getHostAddress());		
		}
		//receive data from client
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String inputLine = in.readLine();
		String receivedData = inputLine;
		while(!inputLine.isEmpty()){
			inputLine = in.readLine();
			receivedData += inputLine + "\n";
		}
		System.out.println("Received Data from Client:\n" + receivedData);

		//send data back to client
		String response = "I got yo data\nLooks sometin like dis\n" + receivedData;
		clientSocket.getOutputStream().write(response.getBytes("UTF-8"));
	}
}
