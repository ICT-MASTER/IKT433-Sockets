import java.io.*;
import java.net.*;

public class Client {

    static String serverIp = "127.0.0.1";
    static int port = 10007;

    public static void main(String[] args) throws IOException {
        
        System.out.println ("Connecting to " + serverIp + " on port 10007.");

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        // Open Connection
         try {
            socket = new Socket(serverIp, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
         } catch (UnknownHostException e) {
            System.err.println("Unspesified error " + serverIp);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO Exception");
            System.exit(1);
        }

        BufferedReader incomming = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.print ("Input : ");
        while ((userInput = incomming.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server reply: " + in.readLine());
            System.out.print ("Input : ");
        }

        out.close();
        in.close();
        incomming.close();
        socket.close();
    }
}

