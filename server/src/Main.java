import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port;

        if (args.length < 1) port = 1050;
        else port = Integer.parseInt(args[0]);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started! \n Port: " + port + "\nAwaiting players...");

        } catch (IOException e) {
            System.err.println("Cannot start a server");
            System.exit(-1);
        }

        while (true){
            try {
                new Client(serverSocket.accept()).start();
            } catch (IOException e) {
                System.err.println("accept() failed");
            }
        }
    }
}
