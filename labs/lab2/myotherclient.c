/*mytcpclient*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>
int main(int argc, char *argv[]) {
	char servername[256];
	printf("Client Program\n");
	if(argc!=3) {
		printf("Usage: %s <server> <port>\n", argv[0]);
		exit(0);
	}
	strcpy(servername, argv[1]);
	printf("Server:");
	printf("%s",servername);
	printf("\n");
/*	char *servername = argv[1];
	int port = atoi(argv[2]);

	printf("Client Program is running. (forest gump voice)\n");	

	if(argc != 3){
		printf("Usage: %s <server> <port>\n", argv[0]);
		exit(0);
	}

	printf("Servername = %s, port = %d\n", servername, port);

	//create socket
	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0){
		perror("ERROR opening socket\n");
		close(sockfd);
	}

	struct hostent *server_he;	
	if ((server_he = gethostbyname(servername)) == NULL) {
		perror("ERROR in gethostbyname\n");
		return 2;
	}	
	
	//connect to the server	
	struct sockaddr_in serveraddr;
	bzero((char*) &serveraddr, sizeof(serveraddr));
	serveraddr.sin_family = AF_INET;

	bcopy((char*)server_he->h_addr, (char*)&serveraddr.sin_addr.s_addr, server_he->h_length);
	serveraddr.sin_port = htons(port);
	
	if (connect(sockfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr)) < 0){
		perror("Cannot connect to the server\n");
		exit(0);
	} else {
		printf("Connected to server\n");
	}
	
	//send message	
	char *msg = "GET / HTTP/1.0\r\n\r\n";
	int bytes_sent;
	bytes_sent = send(sockfd, msg, strlen(msg), 0);
	
	//receive message
	char buffer[1024];
	int byte_received;
	bzero(buffer, 1024);
	byte_received = recv(sockfd, buffer, 1024, 0);
	if (byte_received < 0) {
		perror("ERROR reading from socket\n");
		exit(0);
	}

	printf("Message received: %s", buffer);
*/
	return 0;
}
