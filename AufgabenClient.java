import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class AufgabenClient {
    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket kkSocket = new Socket(NetworkConfig.HOSTNAME, NetworkConfig.PORT); PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        ) {

        } catch(IOException | UncheckedIOException ioe) {
            System.err.println(ioe);
        }
    }
}
