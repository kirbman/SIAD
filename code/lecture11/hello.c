/*hello.c*/

#include <stdio.h>

int main(int argc, char *argv[]) {
	char *data = getenv("QUERY_STRING");
	printf("Content-Type: text/plain; charset=utf-8\n\n");
	printf("Prestige WorldWide brought to you by %s\n", data);
	return 0;
}
