import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClientHandeler implements Runnable{
    private Socket client;
    private Aufgabengenerierbar questionSource;
    private int nQuestions;

    public ClientHandeler(Socket client, Aufgabengenerierbar questionSource, int nQuestions) {
        this.client = client;
        this.nQuestions = nQuestions;
        this.questionSource = questionSource;
    }

    @Override
    public void run() {
        // TODO abhängig von der Java Version kann auch der Socket im try with resources verwendet werden. mir schreibt er das er aber final sein soll :(
        try(PrintWriter out = new PrintWriter(this.client.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));) {
            int correct = 0;
            for(int i = 0; i < nQuestions; i++) {
                Entry<String, String> questionAndAnswer = questionSource.pickQuestion();
                out.println("Frage " + (i + 1) + ": " + questionAndAnswer.getKey());
                String possibleAnswer = in.readLine();
                if(possibleAnswer == null) {
                    break;
                } else if(possibleAnswer.equalsIgnoreCase(questionAndAnswer.getValue())) {
                    out.println("Korrekt!");
                    correct++;
                } else {
                    out.println("Falsch! Richtig wäre \"" + questionAndAnswer.getValue() + "\"");
                }
            }
            out.println(correct + "/" + nQuestions + " Fragen richtig beantwortet");
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