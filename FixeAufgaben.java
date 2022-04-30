import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public class FixeAufgaben implements Aufgabengenerierbar {
    private static Map<String, String> qAndA = new HashMap<>();
    private Random r = new Random();

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

    @Override
    public Entry<String, String> pickQuestion() {
        List<String> keys = new ArrayList<String>(qAndA.keySet());
        String key = keys.get(r.nextInt(keys.size()));
        return Map.entry(key, qAndA.get(key));
    }
    
}
