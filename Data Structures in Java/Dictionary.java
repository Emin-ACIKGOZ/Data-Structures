// Author: Emin Salih Açıkgöz ID:22050111032
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Dictionary {
    private final String filePath;
    private final HashMap<String, Integer> wordFreqMap;
    private final HashMap<String, Integer> wordOrderMap;
    private int order;

    public Dictionary(String filePath) {
        this.filePath = filePath;
        this.wordFreqMap = new HashMap<>();
        this.wordOrderMap = new HashMap<>();
        this.order = 0;
    }

    public Dictionary() {
        this("sentences.txt");
    }

    /**
     * Processes a line of text,
     * extracts words,
     * updates their frequency counts in a map,
     * and maintains their order in another map.
     * @param line The line of text to process.
     */
    private void processLine(String line) {
        line = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().trim();
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                wordFreqMap.put(word, wordFreqMap.getOrDefault(word, 0) + 1);
                if (!wordOrderMap.containsKey(word)) {
                    wordOrderMap.put(word, order);
                    order++;
                }
            }
        }
    }
    /**
     * Loads data from a text file located at the provided file path.
     */
    private void loadTxt() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Error: txt file does not exist");
                if (file.createNewFile()) {
                    System.out.println("Creating empty file...");
                } else {
                    System.err.println("Error: Failed to create the file");
                    return;
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                processLine(line);
                line = reader.readLine();
            }

            System.out.println("Successfully read from " + this.filePath);

            reader.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the frequency count of a given word from the word frequency map.
     * @param word The word to retrieve the frequency count for.
     * @return The frequency count of the word, or 0 if the word is not found.
     */
    private int getCount(String word) {
        return this.wordFreqMap.getOrDefault(word, 0);
    }

    /**
     * Formats an array of keys into a string.
     * @param keys The array of keys to format.
     * @param k The maximum number of keys to include in the formatted result.
     * @return A string representing the formatted array of keys.
     */
    private String formatResult(String[] keys, int k) {
        StringBuilder result = new StringBuilder("[");
        int length = Math.min(k, keys.length);
        for (int i = 0; i < length; i++) {
            result.append(keys[i]);
            if (i < length - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        if (k > keys.length) {
            System.out.println("Warning: Requested " + k + " words, but only " + keys.length + " words are available.");
        }
        return result.toString();
    }

    /**
     * Retrieves the most frequent words up to a specified amount.
     * @param k The number of the most frequent words to retrieve.
     */
    private void getMostFrequentWords(int k) {
        String[] mostFreq = wordFreqMap.keySet().toArray(new String[0]);

        // Sort by frequency and then by order if the freq is the same
        Arrays.sort(mostFreq, (a, b) -> {
            int freqCompare = wordFreqMap.get(b).compareTo(wordFreqMap.get(a));
            if (freqCompare == 0) {
                return wordOrderMap.get(a).compareTo(wordOrderMap.get(b));
            }
            return freqCompare;
        });

        int n = Math.min(mostFreq.length, k);
        if (n == 1) {
            System.out.println("The " + n + " most frequent word is: " + formatResult(mostFreq, k));
        } else {
            System.out.println("The " + n + " most frequent words are: " + formatResult(mostFreq, k));
        }
    }

    /**
     * Retrieves the least frequent words up to a specified amount.
     * @param k The number of the least frequent words to retrieve.
     */
    private void getLeastFrequentWords(int k) {
        String[] leastFreq = wordFreqMap.keySet().toArray(new String[0]);

        // Sort by frequency and then by order if the freq is the same
        Arrays.sort(leastFreq, (a, b) -> {
            int freqCompare = wordFreqMap.get(a).compareTo(wordFreqMap.get(b));
            if (freqCompare == 0) {
                return wordOrderMap.get(a).compareTo(wordOrderMap.get(b));
            }
            return freqCompare;
        });

        int n = Math.min(leastFreq.length, k);
        if (n == 1) {
            System.out.println("The " + n + " least frequent word is: " + formatResult(leastFreq, k));
        } else {
            System.out.println("The " + n + " least frequent words are: " + formatResult(leastFreq, k));
        }
    }

    /**
     * Runs the program, allows the user to interact with an interface.
     */
    public void run() {
        loadTxt();
        int option = -1;
        Scanner scan = new Scanner(System.in);
        while (option != 4) {
            System.out.println("""
                    Select an Option:
                    1. Get the count of a specific word
                    2. Get the most frequent words
                    3. Get the least frequent words
                    4. Exit the program
                    """);
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                option = -1;
                scan.nextLine();
            }
            switch (option) {
                case 1:
                    System.out.println("Enter a word: ");
                    try {
                        String word = scan.nextLine();
                        if (word.isEmpty()) {
                            throw new RuntimeException();
                        }
                        System.out.println("The word '" + word + "' appears " + getCount(word) + " time(s)");
                    } catch (Exception e) {
                        System.err.println("Error: input is invalid");
                    }
                    break;
                case 2:
                    System.out.println("Enter the number of words: ");
                    try {
                        int k = scan.nextInt();
                        scan.nextLine();
                        if (k > 0) {
                            getMostFrequentWords(k);
                        } else {
                            System.err.println("Error: input must be a positive integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: input is invalid");
                        scan.nextLine(); //Clears invalid input
                    }
                    break;
                case 3:
                    // code block
                    System.out.println("Enter the number of words: ");
                    try {
                        int k = scan.nextInt();
                        scan.nextLine();
                        if (k > 0) {
                            getLeastFrequentWords(k);
                        } else {
                            System.err.println("Error: input must be a positive integer");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: input is invalid");
                        scan.nextLine(); //Clears invalid input
                    }
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.err.println("Error: please choose an option between 1 to 4");
            }
        }
        scan.close();
    }

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        dict.run();
    }
}