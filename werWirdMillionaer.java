/**
 * @Author: Buntschu Leonard
 * @Date:   21-01-2025 08:17:51
 * @Last Modified by:   Buntschu Leonard
 * @Last Modified time: 22-01-2025 10:25:21
 * @Licence: All rights Reserved
 */
package main.java;
import java.util.Scanner;
import java.util.Random;

public class werWirdMillionaer {
    // Array of questions for the game
    static String[] questions = {
        "Frage 1: Welches chemische Element wurde nach einem Land bennant?",
        "Frage 2: Welcher Planet hat den grössten Vulkan im Sonnensystem?",
        "Frage 3: Welches Land hat die meisten offiziellen Amtssprachen?",
        "Frage 4: Wie viele Tasten hat ein modernes Klavier?",
        "Frage 5: Wer gewann den Nobelpreis für Physik 1921?",
        "Frage 6: Welcher See ist der tiefste der Welt?",
        "Frage 7: In welchem Jahr wurde die Schweizerische Eidgenossenschaft gegründet?",
        "Frage 8: Welche Organisation hat 1945 ihren Sitz in Genf gegründet",
        "Frage 9: Was ist die Währung von Norwegen?",
        "Frage 10: Wie viele Knochen hat der menschliche Körper im Erwachsenenalter?",
        "Frage 11: Welcher Ber ist der höchste der Welt, gemessen ab dem MeeresGRUND?",
        "Frage 12: Wie heisst das längste Flussystem der Welt?",
        "Frage 13: Welches Tier ist das Symbol für die Börse bei steigenden Kursen?",
        "Frage 14: Welches Land exportiert die meisten Bananen weltweit?",
        "Frage 15: Wie viele Ringe sind auf der Olympischen Flagge?"

    };
    // Array of possible answers for each question
    static String[][] answers = {
        {"1) Francium", "2) Polonium", "3) Germanium", "4) Americium"},
        {"1) Venus", "2) Jupiter", "3) Mars", "4) Saturn"},
        {"1) Indien", "2) Südafrika", "3) Schweiz", "4) Papua-Neuguinea"},
        {"1) 85", "2) 88", "3) 90", "4) 92"},
        {"1) Marie Curie", "2) Niels Bohr", "3) Max Planck", "4) Albert Einstein"},
        {"1) Baikalsee", "2) Tanganjikasee", "3) Kaspisches Meer", "4) Viktoriasee"},
        {"1) 1291", "2) 1315", "3) 1492", "4) 1648"},
        {"1) UNO", "2) Weltgesundheitsorganisation (WHO)", "3) Internationales Olympisches Komitee (IOC)", "4) Rotes Kreuz"},
        {"1) Euro", "2) Rubel", "3) Kronen", "4) Pfund"},
        {"1) 206", "2) 208", "3) 210", "4) 212"},
        {"1) Mount Everest", "2) K2", "3) Mauna Kea", "4) Kilimandscharo"},
        {"1) Nil", "2) Mississippi-Missouri", "3) Jangtse", "4) Amazonas"},
        {"1) Stier", "2) Gepard", "3) Adler", "4) Wolf"},
        {"1) Brasilien", "2) Kolumbien", "3) Ecuador", "4) Costa Rica"},
        {"1) 4", "2) 5", "3) 6", "4) 7"},
        // All possible Answers
    };

    static int[] correctAnswers = {3, 3, 2, 2, 4, 1, 1, 2, 3, 1, 3, 4, 1, 3, 2}; // Correct answers
    static int[] prizeLevels = {100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000}; // Prize levels
    static int[] safetyLevels = {4, 9}; // Saftey levels, which "array" he would drop back to
    static boolean[] jokers = {true, true, true, true}; // Joker availability
    static boolean retryJokerAvailable = true; // Retry Joker status
    static boolean retryJokerActive = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner to read input
        int currentLevel = 0; // Tracks the progress in questions
        boolean gameActive = true; // Game loop variable

        // Main game loop
        while (gameActive && currentLevel < questions.length) {
            System.out.println(questions[currentLevel]); // Display current question
            for (String answer : answers[currentLevel]) {
                System.out.println(answer); // Display current answers possibilities
            }

            System.out.println("Wähle deine Antwort (1-4), nutze einen Joker (5 für Joker) oder gehe nach Hause (6 für gehen):");

            while (!scanner.hasNextInt()) { // Check if input is an integer
                System.out.println("Ungültige Eingabe! Bitte gib eine Zahl ein:");
                scanner.next(); // Deletes the invalid input
            }

            int choice = scanner.nextInt(); // Takes user input

            if (choice == 5) { // Choosing Joker
                useJoker(scanner, currentLevel);
                continue; // Continue to next iteration to display question again
            }

            if (choice == 6) { // Player chooses to leave
                try { // Checks if the player has a prize level
                    System.out.println("Du gehst mit " + prizeLevels[currentLevel - 1] + " nach Hause!");
                }
                catch(Exception e) { // If the try returns an error due to no prize level, player leaves without anything
                    System.out.println("Du gehst ohne Gewinn nach Hause!");
                } finally { // Ends the game no matter what the player wins
                    gameActive = false;
                    System.exit(0);
                }
            }
            // Check if player choice is valid and not null from 50:50
            if (choice > 0 && choice <= 4 && answers[currentLevel][choice - 1] != null) {
                if (choice == correctAnswers[currentLevel]) { // Player answers correctly
                    System.out.println("Richtig!");
                    currentLevel++; // Goes up one level in winnings
                } else { // Player answered incorrectly
                    if (retryJokerActive) { // Check if retry joker is active
                        System.out.println("Falsch, du darfst aber nochmals!");
                        retryJokerActive = false; // When used it gets turned off to prevent infinite guessing
                        continue;
                    } else {
                        System.out.println("Falsch!");
                        int safePrize = getSafePrize(currentLevel); // Get price of last saftey level
                        System.out.println("Du verlierst! Dein Gewinn: " + safePrize);
                        gameActive = false; // Ends game
                    }
                }
            } else {
                System.out.println("Ungültige Eingabe! Versuche es erneut.");
            }
        }

        if (currentLevel == questions.length) { // Player answered all questions and won the game
            System.out.println("Herzlichen Glückwunsch! Du hast " + prizeLevels[prizeLevels.length - 1] + " gewonnen!");
        }

        scanner.close();
    }
    // Shows available jokers
    static void useJoker(Scanner scanner, int currentLevel) {
        System.out.println("Verfügbare Joker:");
        // Checks if the Joker is set to available in the Array on L55
        if (jokers[0]) System.out.println("1) 50:50 (Zwei falsche Antworten verschwinden.)");
        if (jokers[1]) System.out.println("2) Publikumsjoker (Das Publikum stimmt ab.)");
        if (jokers[2]) System.out.println("3) Telefonjoker (Du darfst einen bekannten anrufen)");
        if (jokers[3]) System.out.println("4) Zusatzjoker (Du darfst jemanden aus dem Publikum fragen.)");
        if (retryJokerAvailable) System.out.println("5) Zweite Chance (Du hast einen zweiten Versuch beim Antworten.)");
        System.out.println("6) Zurück zur Frage");

        while (!scanner.hasNextInt()) { // Check if input is an integer
            System.out.println("Ungültige Eingabe! Bitte gib eine Zahl ein:");
            scanner.next(); // Deletes the invalid input
        }

        int jokerChoice = scanner.nextInt();
        switch (jokerChoice) { // Execute Jokers
            case 1:
                if (jokers[0]) use5050(currentLevel);
                jokers[0] = false; // Marks as used
                break;
            case 2:
                if (jokers[1]) useAudience(currentLevel);
                jokers[1] = false; // Marks as used
                break;
            case 3:
                if (jokers[2]) usePhone(currentLevel);
                jokers[2] = false; // Marks as used
                break;
            case 4:
                if (jokers[3]) zusatzJoker(currentLevel);
                jokers[3] = false; // Marks as used
                break;
            case 5:
                if (retryJokerAvailable) retryJoker();
                retryJokerAvailable = false; // Marks as used
                break;
            case 6:
                System.out.println("Zurück zur Frage!"); // Back to the question
                break; // Beendet das Switch-Statement
            default:
                System.out.println("Ungültige Eingabe!");
        }
    }
    // 50:50 Joker logic
    static void use5050(int currentLevel) {
        System.out.println("50:50 Joker: Zwei falsche Antworten werden entfernt.");
        Random random = new Random();
        int correctAnswerIndex = correctAnswers[currentLevel] - 1; // Index of correct answer
        int removed = 0; // Counter for removing wrong answers

        // Removing 2 wrong answers
        while (removed < 2) {
            int randomIndex = random.nextInt(4); // Pick random answer
            if (randomIndex != correctAnswerIndex && answers[currentLevel][randomIndex] != null) { // Makes sure answer picked is not right or already deleted
                answers[currentLevel][randomIndex] = null; // Removes wrong answer
                removed++;
            }
        }
        // Display remaining Answers
        for (int i = 0; i < 4; i++) {
            if (answers[currentLevel][i] != null) {
                System.out.println(answers[currentLevel][i]);
            }
        }
    }
    // Audience joker logic
    static void useAudience(int currentLevel) {
        System.out.println("Publikumsjoker: Das Publikum hat abgestimmt!");
        Random random = new Random();
        int correctAnswerIndex = correctAnswers[currentLevel] - 1;
        int[] percentages = new int[4]; // Stores vote percentages

        // Assigns a base percentage to the right answer so it is actually helpful
        percentages[correctAnswerIndex] = 30 + random.nextInt(21);
        int remainingPercentage = 100 - percentages[correctAnswerIndex];

        // Distribute the remaining percentages to the wrong answers
        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerIndex) {
                int randomShare = random.nextInt(remainingPercentage + 1);
                percentages[i] = randomShare;
                remainingPercentage -= randomShare;
            }
        }
        // Displays percentage for each question
        for (int i = 0; i < 4; i++) {
            System.out.println(answers[currentLevel][i] + " - " + percentages[i] + "%");
        }
    }
    // Phone joker logic
    static void usePhone(int currentLevel) {
        Random random = new Random();
        if (random.nextInt(100) < 75) { // Random number between 0 and 100
            System.out.println("Freund: Ich bin überzeugt, dass " + correctAnswers[currentLevel] + " die richtige Antwort ist."); // If below 75, friend gives the right answer (75%)
        } else {
            System.out.println("Freund: Ich bin überzeugt, dass " + ((correctAnswers[currentLevel] % 4) + 1) + " die richtige Antwort ist."); // If above 75, friend gives the wrong answer (25%)
        }
    }

    static void zusatzJoker(int currentLevel) {
        Random random = new Random();
        if (random.nextInt(100) < 75) { // Random number between 0 and 100
            System.out.println("Seppli: Ich glaube, es ist Antwort " + correctAnswers[currentLevel] + "."); // If below 75, dude in audience gives the right answer (75%)
        } else {
            System.out.println("Seppli: Ich glaube, es ist Antwort " + ((correctAnswers[currentLevel] % 4) + 1) + "."); // If above 75, dude in audience gives the wrong answer (25%)
        }
    }

    static void retryJoker() {
        retryJokerActive = true; // Activates retry joker
        System.out.println("Sie haben 2 Versuche!"); // Informs the player
    }

    // Calculates saftey level based on current level
    static int getSafePrize(int currentLevel) {
        // Go through safety levels from highest to lowest
        for (int i = safetyLevels.length - 1; i >= 0; i--) {
            if (currentLevel >= safetyLevels[i]) {
                return prizeLevels[safetyLevels[i] - 1]; // Return the prize of the safety level
            }
        }
        return 0; // If no saftey level is reacher, returns 0, as if prize = 0 Money
    }
}
