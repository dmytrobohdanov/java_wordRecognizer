import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
    private static String WORDS_FILE = "words.txt";
//    private static String TESTCASES_FILE = "words.txt";
    private static String TESTCASES_FILE = "tests.txt";

    public static void main(String[] args) throws FileNotFoundException {
        File tests = new File(TESTCASES_FILE);
        Scanner in = new Scanner(tests);

        //learning
        Learner learner = new Learner(WORDS_FILE);

        System.out.println("I've finished my learning");

//        System.out.println(learner.lettersProbability);
        System.out.println(learner.lettersProbability.size());

        //creating word recognizer
        WordsRecognizer wordsRecognizer = new WordsRecognizer(learner.lettersProbability);
        System.out.println("I'm ready to work!");

        int passCounter = 0;
        int passTrue = 0;
        int passFalse = 0;
        int totalTestsAmount = 0;

        while (in.hasNext()) {
            String string = in.nextLine();
            totalTestsAmount++;

            boolean answer = getCorrectAnswer(string);
//            boolean answer = true;
            boolean response = wordsRecognizer.isThisWord(getLettersSequence(string));
//            boolean response = wordsRecognizer.isThisWord(string);
            if (answer == response) {
                passCounter++;
                if(answer == true){
                    passTrue++;
                }
                else {
                    passFalse++;
                }
            }

//            //displaying false positive results
//            if(answer == false && response == true){
//                System.out.println(string);
//            }
//            if (totalTestsAmount % 10000 == 0) {
//                System.out.println(passCounter + " / " + totalTestsAmount);
//            }
        }

        System.out.format("You passed %d test cases out of %d", passCounter, totalTestsAmount);
        System.out.println();

        System.out.format("You pass %d of true test cases and %d false tests out of %d", passTrue, passFalse, totalTestsAmount/2);
        System.out.println();

        System.out.format("It is %f percents of all tests", (double) passCounter / totalTestsAmount);
        System.out.println();
    }

    public static String getLettersSequence(String string) {
        return string.substring(2);
    }

    private static boolean getCorrectAnswer(String string) {
        return string.charAt(0) == '1';
    }

}