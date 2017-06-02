/*mytcpclient*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>
int main(int argc, char *argv[]) {
	int port = atoi(argv[1]);

	printf("Server Program is running. (forest gump voice)\n");	

	if(argc != 2){
		printf("Usage: %d <port>\n", port);
		exit(0);
	}

	//create socket
	int serversockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (serversockfd < 0){
		perror("ERROR opening socket\n");
		close(serversockfd);
	}
	
	struct sockaddr_in serveraddr;
	bzero((char*) &serveraddr, sizeof(serveraddr));

	//internet access
	serveraddr.sin_family = AF_INET;

	//Figure out IP address
	serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);	

	//bcopy((char*)server_he->h_addr, (char*)&serveraddr.sin_addr.s_addr, server_he->h_length);
	serveraddr.sin_port = htons(port);

	printf("Server running on port %d\n", port);

	//bind socket to the address
	int optval = 1;
	setsockopt(serversockfd, SOL_SOCKET, SO_REUSEADDR, &optval, sizeof(int));
	
	if(bind(serversockfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr)) < 0) {
		perror("ERROR on binding\n");
		exit(3);
	}
	
	//listen
	if(listen(serversockfd, 5) < 0) {
		perror("ERROR on listen");
		exit(1);
	}
	printf("Server is listening on port %d\n", port);

	//accept connection
	while(1) {
		struct sockaddr_in clientaddr;
		int clientlen = sizeof(clientaddr);
		int childfd = accept(serversockfd, (struct sockaddr *) &clientaddr, (socklen_t *) &clientlen);
		if(childfd < 0){
			perror("ERROR on accept\n");
			exit(2);
		}
		printf("Connection established with %s\n", inet_ntoa(clientaddr.sin_addr));
	
	
		//receive data
		char buffer[1024];
		int byte_received;
		bzero(buffer, 1024);
		byte_received = recv(childfd, buffer, 1024, 0);
		if(byte_received < 0){
			perror("ERROR reading from socket\n");
			exit(1);
		}
		printf("Message received: %s\n", buffer);
		
		//send data
		char *msg = "Well Hi there garsh!\n";
		int bytes_sent;
		bytes_sent = send(childfd, msg, strlen(msg), 0);
	}	
	return 0;
}
