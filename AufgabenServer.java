import java.io.IOException;
import java.net.*;

public class AufgabenServer {
    public static void main(String[] args) {
        try(ServerSocket ss = new ServerSocket(NetworkConfig.PORT)) {
            Socket client = ss.accept();
            Thread s = new Thread(new ClientHandeler(client));
            s.start();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }
}