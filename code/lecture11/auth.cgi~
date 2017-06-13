#include <stdio.h>
int main(int argc,char *argv[]) {
	char *data = getenv("QUERY_STRING");
	char username[100];
	char password[100];
	sscanf(data, "name=%[^&]&password=%s", username, password);
	if (username != "mike" || password != "hunt") {
		printf("username or password is incorrect\n");
	}
}
