import java.io.IOException;
import java.net.*;

public class AufgabenServer {
    public static void main(String[] args) {
        int port = Integer.MIN_VALUE;
        String filename = null;
        boolean fixeAufgaben = false;
        try {
            port = Integer.parseInt(args[0]);
            filename = args[1];
            fixeAufgaben = Boolean.parseBoolean(args[2]);
        } catch(NumberFormatException nfe) {
            System.err.println("Port war nicht parsable. Bitte eine ganze Zahl eingeben");
            System.exit(1);
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Bitte mindestens 3 CLI Argumente (port). Also so: java AufgabenClient 2000");
            System.exit(1);
        }
        FixeAufgaben.readFromFile(filename);
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Socket client;
            System.out.println("asdasd");
            while(true) {
                client = serverSocket.accept();
                System.out.println(client);
                (new Thread(new ClientHandeler(client, fixeAufgaben ? new FixeAufgaben() : new GenerierteMatheAufgaben(), 3))).start();;
            }
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }
}