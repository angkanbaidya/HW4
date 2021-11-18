//Angkan Baidya
// 112309655
// R01


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a custom class that gets the stopwords from either the directory or user inputted file
 */
public class StopWords {
    private ArrayList<String> stopWords;


    /**
     * Takes in the directory and checks if the stop words are either in the directory or the folder
     * @param directory
     * Throws an exception if not found
     */
    public StopWords(String directory) {
        Scanner scn;

        try {
            try{
                scn = new Scanner(new File(directory+"StopWords.txt"));
            }catch(FileNotFoundException e) {
                scn = new Scanner(new File("StopWords.txt"));
            }

            stopWords = new ArrayList<String>();

            while (scn.hasNextLine()){
                stopWords.add(scn.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found for stopwords");
            System.exit(-1);
        }
    }

    /**
     * Gets the stop words
     * @return
     */
    public ArrayList<String> getStopWords() {
        return stopWords;
    }
}
