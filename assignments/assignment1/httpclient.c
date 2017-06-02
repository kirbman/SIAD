/*httpclient.c*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>

int main(int argc, char *argv[]) {
	char *url = argv[1];
	printf("Client Program is running...\n");	
	if(argc < 2){
		printf("Usage: %s http://<url>\n", argv[0]);
		exit(0);
	}
	
	char host[100], path[100];
	sscanf(url, "http://%[^/]/%s", host, path);
	printf("Host = %s\nPath = %s\n", host, path);

	//create socket
	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0){
		perror("ERROR opening socket\n");
		close(sockfd);
	}

	struct hostent *server_he;	
	if ((server_he = gethostbyname(host)) == NULL) {
		perror("ERROR in gethostbyname\n");
		return 2;
	}	
	
	//connect to the server	
	struct sockaddr_in serveraddr;
	bzero((char*) &serveraddr, sizeof(serveraddr));
	serveraddr.sin_family = AF_INET;

	bcopy((char*)server_he->h_addr, (char*)&serveraddr.sin_addr.s_addr, server_he->h_length);
	serveraddr.sin_port = htons(80);
	
	if (connect(sockfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr)) < 0){
		perror("Cannot connect to the server\n");
		exit(0);
	} else {
		printf("Connected to server\n");
	}
		
	//get filename
	char *filename;
	filename = strrchr(url, '/');
	if (filename[0] = '/') {
		filename++;
	}
	//if (filename == NULL || filename[0] == '\0') {
	//	strcpy(filename, "index.html");
	//}
	//printf("filename: %s\n", filename);
	
	//send message
	char request[100];	
	if (path == NULL) {
		sprintf(request,"GET / HTTP/1.0\r\nHost: %s\r\n\r\n", host);
	} else {
		sprintf(request,"GET /%s HTTP/1.0\r\nHost: %s\r\n\r\n", path, host);
	}
	int bytes_sent;
	bytes_sent = send(sockfd, request, strlen(request), 0);
	
	//receive message
	char buffer[10000];
	int byte_received;
	bzero(buffer, 10000);
	byte_received = recv(sockfd, buffer, 10000, 0);
	if (byte_received < 0) {
		perror("ERROR reading from socket\n");
		exit(0);
	}
	
	//check http response code, if not 200, dont write data to a file
	char respCode[4];
	sscanf(buffer, "HTTP/1.%*[01] %s ",respCode); 	
	if (strcmp(respCode, "200") != 0) {
		printf("HTTP response code does not allow data to be written\nDisplaying message below\n");
		printf("Message received: %s\n", buffer);
	} else {
		//write data to a separate file
		int bytesRem, headerLen, recvCount;
		char *end = strstr(buffer, "\r\n\r\n");
		headerLen = strlen(buffer) - strlen(end);
		bytesRem = byte_received - (headerLen+4);
		FILE *outfile = fopen(filename, "w+");
		printf("Writing data file: %s\n", filename);
		fwrite(end+4, bytesRem, 1, outfile);
		while((recvCount = recv(sockfd, buffer, 10000, 0)) !=0) {
			fwrite(buffer, recvCount, 1, outfile);
		}
		printf("Data file available in directory: /assignment1/\n");	
		fclose(outfile);
	}
}
