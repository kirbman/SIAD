/*chatclient in java*/


import java.net.Socket;
import java.io.*;

public class chatclient {
	public static void main(String[] args) throws IOException {
		//check arguments
		if(args.length != 2){
			System.err.println("Usage: java mytcpclient <host> <port>");
			System.exit(1);
		}
	
		//Get server IP ect
		String hostName = args[0];
		int portNum = Integer.parseInt(args[1]);
	 
		//create client socket
		Socket sock = new Socket(hostName, portNum);
		System.out.println("Connection Established\nHost: " + hostName + " Port: " + portNum);

		//output stream to send data
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);			
		new TCPClientThread(sock).start();
		
		//input stream
		BufferedReader stdln = new BufferedReader(new InputStreamReader(System.in));
		
		//get username
		System.out.println("Plz enter a username: ");
		String username = stdln.readLine();
		while (username == null) {
			System.out.println("Plz enter a valid username: ");
			username = stdln.readLine();
		}

		//send username to server
		
		//get & send user input
		String userInput;
		while ((userInput = stdln.readLine()) != null) {
			if (userInput.equals("Exit")) {
				System.out.println("Closing socket...\nClosing program...");
				System.exit(1);
				sock.close();
			} else if (userInput.equals("Contacts")) {
				System.out.println("Getting list of contacts...");
				//display users in chat server
			}
			out.println(username + ": " + userInput);
		}	
	
	}
}

class TCPClientThread extends Thread {
	private Socket clientSocket = null;

	TCPClientThread(Socket clientSocket) {
		super("TCPClientThread");
		this.clientSocket = clientSocket;
	}

	public void run() {
		try{
			//receive data from client
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while(true){
				String inputLine = in.readLine();
				while((inputLine != null) && !inputLine.isEmpty()) {
					System.out.println(inputLine);
					inputLine = in.readLine();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
