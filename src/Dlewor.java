import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Dlewor {
    // constants to allow colored text and backgrounds
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";


    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static int[] matchDlewor(String attemptedWord, String correctWord, int[] Match) {
        int i;
        int j;

        for (i = 0; i < correctWord.length(); i++) {
            for (j = 0; j < correctWord.length(); j++) {
                if (attemptedWord.charAt(j) == correctWord.charAt(j)) {
                    Match[j] = 2;
                } else if (attemptedWord.charAt(j) == correctWord.charAt(i)) {
                    Match[j] = 1;
                }

            }

        }

        return Match;

    }

    public static void printDelwor(String guessedWord, int[] Match) {

            for (int i = 0; i <= 4; i++) {
                if (Match[i] == 2) {
                    System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + guessedWord.charAt(i));
                } else if (Match[i] == 1) {
                    System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + guessedWord.charAt(i));

                } else {
                    System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + guessedWord.charAt(i));

                }

            }
            System.out.println(ANSI_RESET + "");

    }

    public static int binarySearch(ArrayList<String> wordlist, int arrayBeginning, int arrayEnd, String attemptedWord) {
        if (arrayEnd >= arrayBeginning) {
            int mid = arrayBeginning + (arrayEnd - arrayBeginning) / 2;

            // If the element is present at the
            // middle itself
            if (wordlist.get(mid).compareTo(attemptedWord) == 0) {
                return mid;
            }

            // If element is smaller than mid, then
            // it can only be present in left subarray
            if (wordlist.get(mid).compareTo(attemptedWord) > 1) {
                return binarySearch(wordlist, 1, mid - 1, attemptedWord);
            }

            // Else the element can only be present
            // in right subarray
            return binarySearch(wordlist, mid + 1, arrayEnd, attemptedWord);
        }

        // We reach here when element is not present
        // in array
        return -1;
    }

    public static int linearSearch(ArrayList<String> wordlist, int arrayBeginning, int arrayEnd, String attemptedWord) {
        if (arrayEnd < arrayBeginning) {
            return -1;
        }
        if (wordlist.get(arrayBeginning).equals(attemptedWord)){
            return arrayEnd;
        }
        if (wordlist.get(arrayEnd).equals(attemptedWord)){
            return arrayEnd;
        }
        return linearSearch(wordlist, arrayBeginning + 1, arrayEnd - 1, attemptedWord);
    }

    public static boolean foundMatch(int[] Match, int iterations, String correctWord) {
        if ((Match[0] == 2) && (Match[1] == 2) && (Match[2] == 2) && (Match[3] == 2) && (Match[4] == 2)) {
            System.out.println("Yesss ! You got the word");
            return true;
        }  if (iterations == 5){
            System.out.println("Sorry the correct word is " + ANSI_GREEN_BACKGROUND + ANSI_BLACK + correctWord + ANSI_RESET);
            return false;
        }
        return false;
    }


    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Type Yes if your file is sorted, No if it is not sorted ");
        String answer;
        answer = scnr.nextLine();

        Random rand = new Random();
        String filename;
        FileInputStream myFile = null;
        System.out.println("Opening file " + args[0] + ".");

        // use first command line argument to open dictionary file,
        // gracefully exits if no file
        if (args.length == 1) {
            filename = args[0];
            try {
                myFile = new FileInputStream("src/" + filename);
            } catch (FileNotFoundException e) {
                System.err.println("Could not open input file.");
            }
        } else {
            System.err.println("Wordle [ filename ]");
            System.exit(-1);
        }

        // read in a dictionary file with all words that contain exactly 5 characters
        ArrayList<String> wordlist = new ArrayList<String>();
        Scanner fileReader = new Scanner(myFile); //set the scanner to read from a file
        while (fileReader.hasNextLine()) {
            String word = fileReader.nextLine();
            if (word.length() == 5) {
                wordlist.add(word);
            }
        }

        // print welcome message
        System.out.println("Welcome to Dlewor(TM)");

        // Hello World for colored text and background
        System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + "Hello ");
        System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + "World");
        System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "!");
        System.out.println(ANSI_RESET);


        //Getting a random word
        int IndexOfRandWord = rand.nextInt(wordlist.size());
        String correctWord = wordlist.get(IndexOfRandWord);
        int[] Match = new int[5];
        Scanner input = new Scanner(System.in);

        if(answer.equals("yes")){
            // enter the guessed word
            for (int i = 0; i < 6; i++) {
                System.out.print("Enter your guessed word (" + (i + 1) + "): ");
                String guess = input.nextLine();
                if (binarySearch(wordlist, 0, wordlist.size(), guess) != -1){
                    matchDlewor(guess, correctWord, Match);
                    printDelwor(guess, Match);
                    if (foundMatch(Match,i, correctWord)){
                        break;
                    }
                }else{
                    System.out.println("The word \"" + guess + "\" is not a possible choice. Please try again.");
                    i--;
                }
                Match = new int[5];
            }
        }else if(answer.equals("no")){
            // enter the guessed word
            for (int i = 0; i < 6; i++) {
                System.out.print("Enter your guessed word (" + (i + 1) + "): ");
                String guess = input.nextLine();
                if (linearSearch(wordlist, 0, wordlist.size() - 1, guess) != -1){
                    matchDlewor(guess, correctWord, Match);
                    printDelwor(guess, Match);
                    if (foundMatch(Match,i, correctWord)){
                        break;
                    }
                }else{
                    System.out.println("The word \"" + guess + "\" is not a possible choice. Please try again.");
                    i--;
                }
                Match = new int[5];
            }

        }


    }
}
