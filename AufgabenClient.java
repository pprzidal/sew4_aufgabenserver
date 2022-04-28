import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class AufgabenClient {
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(args));
        /*if(args.length < 2) {
            System.err.println("hey");
            System.exit(1);
        }*/
        int port = Integer.MIN_VALUE;
        String ip = null;
        try {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        } catch(NumberFormatException nfe) {
            System.err.println("Port war nicht parsable. Bitte eine ganze Zahl eingeben");
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Bitte mindestens 2 CLI Argumente (port und ip). Also so: java AufgabenClient 2000 127.0.0.1");
        }
        try (Socket kkSocket = new Socket(ip, port); PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream())); Scanner sc = new Scanner(System.in)) {
            String fromServer = null;
            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
                if(fromServer.contains("Fragen richtig beantwortet")) break;
                if(fromServer.contains("?")) {
                    String u = sc.nextLine();
                    out.println(u);
                }
            }
        } catch(IOException | UncheckedIOException ioe) {
            System.err.println(ioe);
        }
    }
}
