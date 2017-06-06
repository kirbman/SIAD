/*mytcpclient in java*/


import java.net.Socket;
import java.io.*;

public class mytcpclient {
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
		System.out.println("Connection Established\nHost: " + hostName + " Port: " + portNum + "\n");

		//output stream to send data
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);			
		new TCPClientThread(sock).start();
		
		//read from user input
		BufferedReader stdln = new BufferedReader(new InputStreamReader(System.in));

		String userInput;
		while ((userInput = stdln.readLine()) != null) {
			if (userInput.equals("Exit")) {
				System.out.println("Closing socket...\nClosing program...\n");
				System.exit(1);
				sock.close();
			}
			out.println(userInput);
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
					System.out.println("Received from server: " + inputLine);
					inputLine = in.readLine();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
