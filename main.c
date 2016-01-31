#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

void error(const char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{

    fprintf(stdout, "Welcome to socket programming!\n");

    char server_address[] = "127.0.0.1";
    int port_number = 10007;
    int socket_instance, n;


    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[256];

    // Open a socket
    socket_instance = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_instance < 0)
        error("ERROR opening socket");

    // Server address
    server = gethostbyname(server_address);
    if (server == NULL) {
        fprintf(stderr, "ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;


    bcopy(server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = htons(port_number);

    if (connect(socket_instance,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0)
        error("ERROR connecting");

    while(1) {

        printf("Input:");
        bzero(buffer,256);
        fgets(buffer,255,stdin);


        n = write(socket_instance, buffer, strlen(buffer));

        if (n < 0)
            error("ERROR writing to socket");

        bzero(buffer,256);
        n = read(socket_instance,buffer,255);

        if (n < 0)
            error("ERROR reading from socket");

        printf("%s\n",buffer);

        //close(socket_instance);


    }




}