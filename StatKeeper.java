import java.util.HashMap;
import java.util.Map;

class StatKeeper {
    public static final int ACCURACY = 300;
    public HashMap<Character, Integer> statistic;
    public Integer totalLettersNumber;

    StatKeeper(HashMap<Character, Integer> lettersAmount, int length) {
        totalLettersNumber = 0;
        statistic = new HashMap<>();
        renewStatistic(lettersAmount, length);

//        for (char i = 'a'; i <= 'z'; i++) {
//            statistic.put(i, 0);
//        }
//        statistic.put('\'', 0);
    }

    /**
     * updating statistic with new data from word
     *
     * @param newData needs to be added to statistic
     * @param length  of the word
     */
    public void renewStatistic(HashMap<Character, Integer> newData, int length) {

//        if(length >= WordsRecognizer.MAX_LETTERS_NUMBER) return;

        for (char i = 'a'; i <= 'z'; i++) {
            if (!(statistic.containsKey(i) || newData.containsKey(i))) {
                continue;
            } else if (statistic.containsKey(i) && !newData.containsKey(i)) {
                continue;
            } else if (!statistic.containsKey(i) && newData.containsKey(i)) {
                statistic.put(i, newData.get(i));
            } else {
                int val = statistic.get(i);
                val += newData.get(i);
                statistic.put(i, val);
            }
        }
        char ap = '\'';
        if (!(statistic.containsKey(ap) || newData.containsKey(ap))) {

        } else if (statistic.containsKey(ap) && !newData.containsKey(ap)) {

        } else if (!statistic.containsKey(ap) && newData.containsKey(ap)) {
            statistic.put(ap, newData.get(ap));
        } else {
            int val = statistic.get(ap);
            val += newData.get(ap);
            statistic.put(ap, val);
        }

        totalLettersNumber += length;
    }

    /**
     * instead of amount of letter appearance
     * writes probability of appearing each letter to Map statistic
     */
    public void getProbabilities() {
        for (Map.Entry<Character, Integer> entry : statistic.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();

            statistic.get(key);
            value *= ACCURACY;
            value = (int) value / totalLettersNumber;
            statistic.put(key, value);
        }
    }
}