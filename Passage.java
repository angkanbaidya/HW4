//Angkan Baidya
// 112309655
// R01



import java.io.*;
import java.util.*;


/**
 *  Class Passage is a hash table which maps strings to an integer value of a single text file.
 *  Passage will then construct a parsed text file and count each occurrence of a word.
 */
public class Passage {
    private String title;
    private String directory;
    private int wordCount;
    private HashMap<String, Double> similarTitles = new HashMap<>();
    private HashMap<String, Integer> occurrences = new HashMap<>();

    /** Constructor which sets the title of the Passage and calls the parseFile() method
     *
     * @param directory
     * @param title
     * @param file
     */
    public Passage(String directory, String title, File file) {
        this.setTitle(title);
        this.directory = directory;
        this.parseFile(file);
    }

    /** Calculates the similarity between two Passage objects using the formula above
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double cosineSimilarity(Passage p1, Passage p2) {

        Set<String> set01 =	new HashSet<>(p2.getWords());
        Set<String> set02 = new HashSet<>(p1.getWords());

        for(String str: set02) {
            if(!set01.contains(str))
                set01.add(str);
        }

        double word1Freq;
        double word2Freq;
        double numerator = 0;
        double denominatorPart1 = 0;
        double denominatorPart2= 0;
        double cosineValue;
        for (String word: set01) {
            word1Freq = p1.getWordFrequency(word);
            word2Freq = p2.getWordFrequency(word);
            numerator += word1Freq * word2Freq;
            denominatorPart1 += Math.pow(word1Freq, 2);
            denominatorPart2 += Math.pow(word2Freq, 2);
        }
        cosineValue = numerator/(Math.sqrt(denominatorPart1)*Math.sqrt(denominatorPart2));
        return cosineValue;
    }

    /** Reads the given text file and counts each word’s occurrence,
    * excluding stop words, and inserts them into the table
     **/
    public void parseFile(File file) {

        StopWords s = new StopWords(directory);
        ArrayList<String> stopWords = s.getStopWords();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String [] words = this.processWord(st).split(" ");
                for(String word: words) {
                    if (!word.isEmpty() && !stopWords.contains(word)) {
                        this.setWordCount(this.getWordCount()+1);
                        this.addWordToOccurrences(word);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + file.getName());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
            System.exit(-1);
        }
    }

    /** returns the relative frequency of the given word in this Passage (occurrences/wordCount)
     *
     * @param word
     * @return
     */
    public double getWordFrequency(String word) {
        return (double)this.getOccurrences().getOrDefault(word, 0)/this.getWordCount();
    }

    /**	returns a Set containing all of the words in this Passage
     *
     * @return
     */
    public Set<String> getWords() {
        return this.getOccurrences().keySet();
    }

    /**
     * Gets the title of the passage
     * @return
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the passage.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Gets the word count of the passage
     *
     * @return
     */
    public int getWordCount() {
        return this.wordCount;
    }

    /** Sets the word count of the package
     *
     * @param wordCount
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * gets similiar titles that may be the same as the original text
     * @return
     */
    public HashMap<String, Double> getSimilarTitles() {
        return this.similarTitles;
    }

    /**
     * Sets similiar titles
     * @param similarTitles
     */
    public void setSimilarTitles(HashMap<String, Double> similarTitles) {
        this.similarTitles = similarTitles;
    }

    /** To string method that returns similar titles and the percentages
     *
     * @return
     */
    public String toString() {
        String temp = "";
        int pos = 0;
        for(Map.Entry<String, Double> entry :this.getSimilarTitles().entrySet()) {
            temp += entry.getKey() + "("+ Math.round(entry.getValue()*100) +"%)";
            if(pos != this.getSimilarTitles().entrySet().size()-1) {
                temp += ", ";
            }
            pos++;
        }

        return temp;
    }

    /**
     * Gets the occurences
     * @return
     */
    private HashMap<String, Integer> getOccurrences() {
        return this.occurrences;
    }

    /**
     * Processes each word and removes punctuation
     * @param word
     * @return
     */
    private String processWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
    }

    /**
     * Adding words to each occurence and increasing count and adds word to list
     * @param word
     */
    private void addWordToOccurrences(String word) {
        if(this.getOccurrences().containsKey(word)) {
            // the word is already exists. Increase the count by one
            this.getOccurrences().put(word, this.getOccurrences().get(word) + 1);
        } else {
            // add the word to the list
            this.getOccurrences().put(word, 1);
        }
    }

}