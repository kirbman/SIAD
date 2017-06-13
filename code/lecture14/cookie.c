#include <stdio.h>
int main(void) {
	printf("Content-Type: text/html\n");
	printf("Set-Cookie: username=php\n\n");
	char *cookie = getenv("HTTP_COOKIE");
	printf("Howdy! Cookie from client = %s\n", cookie);
	return 0;	
}
