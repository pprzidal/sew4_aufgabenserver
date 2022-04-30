# Java Networking, Multithreading: Aufgaben-Server

Um Schüler*innen die Möglichkeit zu geben, ihre Fähigkeiten (oder auch ein Programm) mit automatischer Rückmeldung testen zu können, soll ein Aufgaben-Server implementiert werden. Bei jeder Verbindung durch einen Client sollen vom Server drei Aufgaben gestellt werden, die dann vom Client beantwortet werden. Der Server meldet dem Client, ob die Antwort korrekt war, und am Ende, wie viele Fragen richtig beantwortet wurden.

Eine Beispiel-Session ist hier:

```
2+3=?                             # Server
5                                 # Client
Korrekt!                          # Server
Wie heißt diese Schule?           # Server
HTL                               # Client
Falsch! Richtig wäre "TGM"        # Server
10-3=?                            # Server
7                                 # Client
Korrekt!                          # Server
2/3 Fragen richtig beantwortet    # Server
```

(eine Zeichen-für-Zeichen genau gleiche Ausgabe ist nicht notwendig)

# Aufgabenstellung:

Implementiere den Server und einen Client, die eine Kommunikation nach dem obigen Prinzip implementieren. Die Aufgaben (Frage und dazugehörige Antwort) werden dabei am Server generiert. Für EKV ist natürlich nur *eine* Variante umzusetzen.

## Anforderungen GK überwiegend

Server:

* Der Server kann über die Kommandozeile gestartet werden (der Port ist vordefiniert)
* Implementiere eine der folgenden Aufgabengenerator-Strategien
    - erstelle händisch eine Liste von Fragen und Antworten (mindestens 10 Paare) und wähle immer zufällig eine Frage aus, oder
    - generiere zufällig zwei Zahlen und eine Rechenoperation, und mach daraus eine Rechenaufgabe und ihre Lösung
* Der Server wartet auf eingehende Socket-Verbindungen
* Verbindet sich ein Client, bekommt dieser nacheinander drei Aufgaben generiert und gestellt und am Ende sein Ergebnis zurück
* Aufgabengenerator- und Netzwerk-Logik sind voneinander getrennt

Client:

* Der Client verbindet sich mit dem Server (IP-Adresse und der Port sind vordefiniert)
* Der Client gibt Nachrichten vom Server in der Konsole aus und schickt alle Benutzereingaben an den Server weiter
* Der Client erkennt, wann keine Frage mehr gestellt wird und beendet sich dann
    - im Beispiel hat z.B. jede Frage mit `?` geendet, aber andere Ansätze sind denkbar - du hast die Wahl!

Allgemein:

* Alle Verbindungen werden sauber geschlossen
* JavaDoc-Kommentare sind vorhanden

## Zusätzliche Anforderungen GK vollständig

* Der Serverport wird beim Server als Kommandozeilenparameter beim Start übergeben (also z.B. Aufruf `java <Serverklasse> <port>`)
* solltest du eine fixe Aufgabenliste verwenden, lade diese Aufgaben aus einer Datei
* Verbindet sich ein Client, wird ein neuer Thread gestartet, sodass sofort der nächste Client akzeptiert werden kann (optional kann dafür ein Threadpool verwendet werden)
* IP-Adresse und Port werden beim Client als Kommandozeilenparameter beim Start übergeben (also z.B. Aufruf `java <Clientklasse> 127.0.0.1 <port>`)

## Zusätzliche Anforderungen EK überwiegend

Um auch mit aufwendigen Fragenerstellungen umgehen zu können, sollen alle Aufgaben in Hintergrundthreads erstellt werden. Außerdem sollen nun Threadpools zum Einsatz kommen:

    Der Server hat einen Puffer für 20 Aufgaben
        es sollen auch andere Kapazitäten als 20 unterstützt werden
        Hinweis: Producer-Consumer-Schema!
        etwaige Synchronisationslogik für den Puffer soll in einer Klasse gekapselt sein
    Die Aufgaben-Generierung passiert nicht mehr beim Client-Handler, sondern in einem eigenen Thread
        implementiere einen weiteren Fragengenerator (z.B. den nicht als GKÜ umgesetzten von oben) und starte auch diesen.
    Alle Hintergrundthreads (Aufgabenerstellung, Client-Handling) werden über einen Threadpool verwaltet
        mach dir Gedanken über die Anzahl der Hintergrundthreads: mit fixed und cached thread pools besteht die Möglichkeit, eine maximale Anzahl oder beliebig viele Hintergrundthreads zu haben. Was ist bei dieser Aufgabe sinnvoll bzw. wie kann die Wahl das Verhalten des Servers beeinflussen?

Zusätzliche Anforderungen EK vollständig - Variante 1: Automatisierung/Schummler

Ändere den Client so, dass er manche Fragen automatisch beantwortet, z.B. Rechenaufgaben

    Kann die Frage automatisch beantwortet werden, wird nicht auf eine Benutzereingabe gewartet; stattdessen wird die Antwort sofort an die Benutzer*in ausgegeben und an den Server geschickt.
    (dazu den Fixe-Liste-an-Fragen Generator zu verwenden ist erlaubt, aber langweilig ;))

Zusätzliche Anforderungen EK vollständig - Variante 2: Maschinenlesbares Protokoll

Das vorgeschlagene Protokoll ist mit Texten wie Korrekt! oder 1/2 Fragen richtig beantwortet darauf ausgelegt, dass die Ausgaben direkt von Menschen gelesen werden. Verwende stattdessen maschinenlesbarere, "strukturierte" Nachrichten (z.B. false,TGM oder 0,TGM statt Falsch! Richtig wäre "TGM"; 1,2 statt 1/2 Fragen richtig beantwortet; "weniger implizit" angeben, ob noch eine Frage kommt - object streams sind auch möglich) und formuliere daraus clientseitig wieder menschenlesbare Nachrichten für die Ausgabe.

Dadurch kann der Client eine bessere Ausgabe bieten, obwohl der Server auch weiterhin keine zusätzlichen Daten schickt. Mach dir das zunutze, indem du

    nach jeder Frage eine Zwischenstatistik ausgibst
    bei den Zwischen- und Endstatistiken die richtigen Antworten auch in Prozent ausgibst.

Zusätzliche Anforderungen EK vollständig - Variante 3: GUI

Anspruchsvoll! Gib als "Sicherheitsnetz" jedenfalls auf GKV/EKÜ-Niveu ab, bevor du diese Erweiterung angehst.

Erstelle eine GUI (z.B. Swing oder Android-App - nicht JOptionPane!) als Ersatz für das command line interface (CLI). (bei Android darf die Serveradresse hard-coded sein, sonst müsste sie jedes mal in der App eingegeben werden)

Da Netzwerk-Kommunikation sonst die UI blockieren würde, müssen wir hier Multithreading einsetzen. Orientiere dich z.B. an diesen Quellen für Swing und Android. Das Grundprinzip wird etwa folgendes sein:

    beim Programmstart wird die GUI gestartet und eine Netzwerkverbindung hergestellt:
        bei Swing läuft die UI im Event-Dispatching-Thread, die Netzwerkverbindung kann also im Main-Thread laufen
        bei Android läuft die UI (oft) im Main-Thread, die Netzwerkverbindung muss also in einem Hintergrund-Thread gestartet werden
        der Netzwerk-Thread ist verantwortlich, wie üblich die Verbindung sauber zu beenden
    immer wenn vom Server eine Nachricht an den Client kommt, muss eine Aktion im UI-Thread gesetzt werden um z.B. eine Frage anzuzeigen: SwingUtilities.invokeLater(Runnable) / View.post(Runnable)
    wenn vom User eine Antwort erwartet wird, muss der Netzwerk-Thread blockieren, bis diese Antwort angekommen ist. Dafür kann die UI als Producer und der Netzwerk-Thread als Consumer angesehen werden. Sobald die Daten beim Netzwerk-Thread angekommen sind, schickt der Netzwerk-Thread die Daten an den Server.

Abgabe

    Upload des jar inkl. source code hier im Kurs.
    Demonstration der Lösung in einem Abgabegespräch, wobei die eigene Lösung erklärt werden kann.

