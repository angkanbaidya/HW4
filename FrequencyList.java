//Angkan Baidya
// 112309655
// R01

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *This class will search through the passed arrayList of passage and find the occurences of each
 * of its given word in each passage object.
 */
public class FrequencyList {

    private String word;
    private ArrayList<Double> frequencies = new ArrayList<>();
    private Hashtable<String, Integer> passageIndices = new Hashtable<>();

    /**
     * Adds each passage from the arrayList
     * @param word
     * @param passages
     */
    public FrequencyList(String word, ArrayList<Passage> passages) {
        this.word = word;
        for(Passage p: passages) {
            this.addPassage(p);
        }
    }
    /**
    Adds a Passage to this FrequencyList and passageIndices contains p's title which maps
     to the next available index in the arrayList
    */
    public void addPassage(Passage p) {
        double freq = p.getWordFrequency(this.word);
        if(freq > 0) {
            this.frequencies.add(freq);
            this.passageIndices.put(p.getTitle(), this.frequencies.size()-1);
        }
    }

    /**
     *     returns the frequency of "word" in the given Passage,
     *     0 if the Passage is not contained in this FrequencyList
     */
    public double getFrequency(Passage p) {
        if(this.passageIndices.containsKey(p.getTitle())) {
            int index = this.passageIndices.get(p.getTitle());
            return this.frequencies.get(index);
        } else {
            return 0;
        }
    }
}