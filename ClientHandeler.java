import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;

public class ClientHandeler implements Runnable{
    private Socket client;
    private Map<String, String> qAndA = Map.of("2+3=?", "5",
                                               "Wie heißt diese Schule?", "TGM",
                                               "10-3=?", "7");

    public ClientHandeler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try(PrintWriter out = new PrintWriter(this.client.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));) {
            int counter = 1;
            for(Entry<String, String> questionAndAnswer : this.qAndA.entrySet()) {
                out.println("Frage " + counter + ": " + questionAndAnswer.getKey());
                String possibleAnswer = in.readLine();
                if(possibleAnswer.equals(questionAndAnswer.getValue())) {
                    out.println("Korrekt!");
                } else {
                    out.println("Falsch! Richtig wäre \"" + questionAndAnswer.getValue() + "\"");
                }
                counter++;
            }
        } catch(IOException ioe) {
            System.err.println(ioe);
        } finally {
            try {
                this.client.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}