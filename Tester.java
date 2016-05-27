import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Tester {
    private static String WORDS_FILE = "words.txt";
//    private static String TESTCASES_FILE = "words.txt";
    private static String TESTCASES_FILE = "tests.txt";


    public static void main(String[] args) throws FileNotFoundException {
        File tests = new File(TESTCASES_FILE);
//        File tests = new File("nie_slova.txt");
//        File tests = new File("words5.txt");
//        File tests = new File("failed_unwords_bigSet.txt");
//        File tests = new File("failed_words_bigSet.txt");
//        File tests = new File("smallSet_2705.txt");
//        File tests = new File("failed_words.txt");
        Scanner in = new Scanner(tests);
        int counterSymbols = 0;

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

        HashSet<String> falseNeg = new HashSet<>();
        HashSet<String> falsePos = new HashSet<>();

        while (in.hasNext()) {
            String string = in.nextLine();
            totalTestsAmount++;

//                if(string.length() < 6)
                if(string.length() < 13 && string.length() > 5)
                    counterSymbols++;

            boolean answer = getCorrectAnswer(string);
            boolean response = wordsRecognizer.isThisWord(getLettersSequence(string));
//            boolean answer = false;
//            boolean response = wordsRecognizer.isThisWord(getLettersSequence(string));
            if (answer == response) {
                passCounter++;
                if(answer == true){
                    passTrue++;
                }
                else {
                    passFalse++;
                }
            }



            //displaying false positive results
            if(answer == false && response == true){
//                System.out.println(string);
                falsePos.add(getLettersSequence(string));
            }

            if(answer == true && response == false){
//                System.out.println(string);
                falseNeg.add(getLettersSequence(string));
            }

            if (totalTestsAmount % 10000 == 0) {
                System.out.println(passCounter + " / " + totalTestsAmount);
            }
        }

//        File fN = new File("failTrueWords.txt");
//        Scanner inn = new Scanner(fN);
//
//        HashSet<String> sashas = new HashSet<>();
//        while (inn.hasNext()){
//            sashas.add(inn.nextLine());
//        }
//
//        int counter = 0;
//
//        for (String str: falseNeg){
//            if(!sashas.contains(str)){
//                counter++;
//            }
//        }
//        System.out.println("in Sasha's array there is no " + counter + "of my false negative");

//        System.out.println("Sasha's set has words " + counterSymbols + " larger then 10");

//        //count same fails
//        int counter = 0;
//        int allFailsCounter = 0;
//        while (inn.hasNext()){
//            allFailsCounter++;
//            String first = inn.nextLine();
//            if(falseNeg.contains(first)){
//               counter++;
//            }
//        }

//        System.out.println("we have same fails " + counter + " from " + allFailsCounter);
//        System.out.println("it is " + (double)counter / allFailsCounter * 100 + "%");





        System.out.format("You passed %d test cases out of %d", passCounter, totalTestsAmount);
        System.out.println();

        System.out.format("You pass %d of true test cases and %d false tests out of %d", passTrue, passFalse, totalTestsAmount/2);
        System.out.println();

        System.out.format("It is %f percents of all tests", (double) passCounter / totalTestsAmount);
        System.out.println();

//        System.out.println(learner.lettersProbability);
    }

    public static String getLettersSequence(String string) {
        return string.substring(2);
    }

    private static boolean getCorrectAnswer(String string) {
        return string.charAt(0) == '1';
    }

}