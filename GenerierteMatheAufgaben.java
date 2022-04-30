import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

interface Rechenbar {
    int rechnen(int a, int b);
}

public class GenerierteMatheAufgaben implements Aufgabengenerierbar {
    private static final List<Entry<String, Rechenbar>> RECHENZEICHEN = List.of(
        Map.entry("+", (int a, int b) -> a + b),
        Map.entry("-", (int a, int b) -> a - b),
        Map.entry("*", (int a, int b) -> a * b),
        Map.entry("/", (int a, int b) -> a / b)
    );
    private Random r = new Random();

    @Override
    public Entry<String, String> pickQuestion() {
        int first = r.nextInt(100) + 1;
        int second = r.nextInt(100) + 1;
        Entry<String, Rechenbar> rechenzeichen = RECHENZEICHEN.get(r.nextInt(RECHENZEICHEN.size()));
        return Map.entry(first + rechenzeichen.getKey() + second + "=?", "" + rechenzeichen.getValue().rechnen(first, second));
    }
    
}
