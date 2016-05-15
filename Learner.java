import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Learner {
    //needs to collect info
    //todo convert to local
    public HashMap<String, StatKeeper> dataKeeper = new HashMap<>();

    //public dictionary with link keyWord-LettersProbability
    public HashMap<String, HashMap<Character, Integer>> lettersProbability;

    Learner(String fileName) {
        File words = new File(fileName);
        try {
            processFile(words);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //todo rewrite with return-type

    /**
     * sets dependency keyWord-letter-probability
     * according to words in file
     *
     * @param words file we are working with
     * @throws FileNotFoundException if there is no such file
     */
    private void processFile(File words) throws FileNotFoundException {
        Scanner in = new Scanner(words);

        while (in.hasNext()) {
            //get word we will process and make it lower case
            String currentWord = in.next();

            //remove all 4 and less letters words
            if(currentWord.length() <= WordsRecognizer.MIN_LETTERS_NUMBER ) continue;
            if(currentWord.length() >= WordsRecognizer.MAX_LETTERS_NUMBER ) continue;

            currentWord = currentWord.toLowerCase();

            //get key (for now: 1st and 2nd letters of word)
            String keyWord = getKeyWord(currentWord);

            //get amount of each letter in current word
            //todo rename var
            HashMap<Character, Integer> lettersAmount = getLettersAmount(currentWord);


            //updating statistic for specified key
            updateStatistic(dataKeeper, keyWord, lettersAmount, currentWord.length());
        }

        //get full statistic and probabilities
        lettersProbability = getStatistic(dataKeeper);
    }

    private HashMap<String, HashMap<Character, Integer>> getStatistic(HashMap<String, StatKeeper> dataKeeper) {
        HashMap<String, HashMap<Character, Integer>> lettersProb = new HashMap<>();

        for (Map.Entry<String, StatKeeper> entry : dataKeeper.entrySet()) {
            String key = entry.getKey();
            StatKeeper StatKeeper = entry.getValue();

            StatKeeper.getProbabilities();
            lettersProb.put(key, StatKeeper.statistic);
        }

        return lettersProb;
    }

    /**
     * update statistic with new data
     *
     * @param dataKeeper    pointer to dataKeeper
     * @param keyWord       key
     * @param lettersAmount amount of each letter in word
     * @param length        of word
     */
    private void updateStatistic(HashMap<String, StatKeeper> dataKeeper, String keyWord,
                                 HashMap<Character, Integer> lettersAmount, int length) {
        if (dataKeeper.containsKey(keyWord)) {
            dataKeeper.get(keyWord).renewStatistic(lettersAmount, length);
        } else {
            dataKeeper.put(keyWord, new StatKeeper(lettersAmount, length));
        }
    }

    /**
     * Counts all letter in specified word
     *
     * @param word we are working with
     * @return HashMap with all letters in word.
     * If there is no letter in Map - there is no letter in word
     */
    public static HashMap<Character, Integer> getLettersAmount(String word) {
        HashMap<Character, Integer> lettersAmount = new HashMap<>();

        //remember word length for less calculations
        int tempLength = word.length();

        //count amount of each of letters in word
        for (int i = 0; i < tempLength; i++) {
            char currentLetter = word.charAt(i);

            if (lettersAmount.containsKey(currentLetter)) {
                Integer amount = lettersAmount.get(currentLetter);
                amount++;
                lettersAmount.put(currentLetter, amount);
            } else {
                lettersAmount.put(currentLetter, 1);
            }
        }
        return lettersAmount;
    }


    /**
     * Gets keyWord from specified word
     *
     * @param currentWord processing word
     * @return keyWord - first 2 symbols of word
     */
    public static String getKeyWord(String currentWord) {
        return (currentWord.length() > 1) ? currentWord.substring(0, 2) : currentWord;
    }
}
