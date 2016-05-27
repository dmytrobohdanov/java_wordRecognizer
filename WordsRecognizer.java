import java.util.HashMap;
import java.util.Map;

public class WordsRecognizer {
    private double MIN_PERCENT = 1.04;
    public static int MAX_LETTERS_NUMBER = 13;
    public static int MIN_LETTERS_NUMBER = 5;
    private HashMap<String, HashMap<Character, Integer>> trueLettersProbability;


    public WordsRecognizer(HashMap<String, HashMap<Character, Integer>> trueLettersProbability) {
        this.trueLettersProbability = trueLettersProbability;
    }

    public boolean isThisWord(String lettersSequence) {
        double probabilityThaItIsWord = 1;

        lettersSequence = lettersSequence.toLowerCase();

        //too long
        if(lettersSequence.length() > MAX_LETTERS_NUMBER){
            return false;
        }

        //too short
        if(lettersSequence.length() <= MIN_LETTERS_NUMBER ){
            return false;
        }

        // to much apostrophes
        if (hasMoreThenOneApostr(lettersSequence)) {
            return false;
        }

        String key = Learner.getKeyWord(lettersSequence);

        //there are no such keys
        if(! trueLettersProbability.containsKey(key)){
            return false;
        }

        //get letters amount from word
        HashMap<Character, Integer> lettersInWord = Learner.getLettersAmount(lettersSequence);

        //count possibility that it is the word
        for (Map.Entry<Character, Integer> entry : lettersInWord.entrySet()) {
            Character letter = entry.getKey();
            Integer letterAmount = entry.getValue();
            if(!trueLettersProbability.get(key).containsKey(letter)){
                return false;
            }
//            probabilityThaItIsWord = probabilityThaItIsWord + letterAmount + ((double) trueLettersProbability.get(key).get(letter) /10 );
            probabilityThaItIsWord = probabilityThaItIsWord * letterAmount * ((double) trueLettersProbability.get(key).get(letter) /10 );
        }

        return probabilityThaItIsWord > MIN_PERCENT;
    }


    private boolean hasMoreThenOneApostr(String lettersSequence) {
        int length = lettersSequence.length();
        int apCounter = 0;
        for (int i = 0; i < length; i++) {
            if (lettersSequence.charAt(i) == '\'') {
                apCounter++;
                if (apCounter >= 2) {
                    return true;
                }
            }
        }
        return false;
    }
}

