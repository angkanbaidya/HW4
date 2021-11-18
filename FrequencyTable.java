//Angkan Baidya
// 112309655
// R01


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class extends a data structure which will contain a collection of FrequencyLists
 * and builds each list from Passage objects.
 */
public class FrequencyTable {

    private HashMap<String, FrequencyList> frequencyListArrayList = new HashMap<>();
    private static FrequencyTable obj = null;
    private ArrayList<Passage> passageList;

    /**
     * Iterates through passages and constructs FrequencyLists with each passages appropriate
     * word frequencies
    */
    public static FrequencyTable buildTable(ArrayList<Passage> passages) {
        if(obj == null) {
            obj = new FrequencyTable(passages);
        }
        return obj;
    }

    /**
     * Adds passages onto the Frequency Table
     * @param passages
     */
    public FrequencyTable(ArrayList<Passage> passages) {

        this.passageList = passages;

        for(Passage p: passages) {
            this.addPassage(p);
        }
    }
    /**
     * Adds a Passage into the FrequencyTable and updates the FrequencyLists accordingly
     * Throws an illegal argument exception if passage is null or empty.
    */
    public void addPassage(Passage p) throws IllegalArgumentException {
        if(p != null && p.getWordCount()>0) {
            Set<String> keySet = p.getWords();
            for(String word: keySet) {
                if(!this.getFrequencyListArrayList().containsKey(word)) {
                    this.getFrequencyListArrayList().put(word, new FrequencyList(word, this.getPassageList()));
                }
            }
        } else {
            throw new IllegalArgumentException("Passage cannot be null or empty");
        }

    }

    /**
     * returns the frequency of the given word in the given Passage.
     * @param word
     * @param p
     * @return
     * @throws IllegalArgumentException
     */
    public int getFrequency(String word, Passage p) throws IllegalArgumentException {
        if(p != null && p.getWordCount()>0) {
            return (int)p.getWordFrequency(word);
        } else {
            throw new IllegalArgumentException("Passage cannot be null or empty");
        }
    }

    /**
     * gets the Frequency ArrayList
     * @return
     */
    public HashMap<String, FrequencyList> getFrequencyListArrayList() {
        return this.frequencyListArrayList;
    }

    /**
     * gets the passageList
     * @return
     */
    private ArrayList<Passage> getPassageList() {
        return this.passageList;
    }

}