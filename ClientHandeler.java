import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClientHandeler implements Runnable{
    private Socket client;
    private static Map<String, String> qAndA = new HashMap<>();

    public static void readFromFile(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = null;
        
            while ((line = br.readLine()) != null) {
                String[] bla = line.split(";");
                qAndA.put(bla[0], bla[1]);
            }
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    public ClientHandeler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        // TODO abhängig von der Java Version kann auch der Socket im try with resources verwendet werden. mir schreibt er das er aber final sein soll :(
        try(PrintWriter out = new PrintWriter(this.client.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));) {
            int counter = 0;
            int correct = 0;
            System.out.println(qAndA.size());
            System.out.println("here masdasdasdasd");
            for(Entry<String, String> questionAndAnswer : qAndA.entrySet()) {
                counter++;
                out.println("Frage " + counter + ": " + questionAndAnswer.getKey());
                String possibleAnswer = in.readLine();
                if(possibleAnswer.equalsIgnoreCase(questionAndAnswer.getValue())) {
                    out.println("Korrekt!");
                    correct++;
                } else {
                    out.println("Falsch! Richtig wäre \"" + questionAndAnswer.getValue() + "\"");
                }
            }
            out.println(correct + "/" + counter + " Fragen richtig beantwortet");
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