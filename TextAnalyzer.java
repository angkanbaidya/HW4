//Angkan Baidya
// 112309655
// R01


import java.io.File;
import java.util.*;

/**
 * This will iterate through the directory through the contents and determine the relations between the pasage
 * object and prints a table of each percentages.
 * It will also determine which files are similar (60% or more) to each other.
 */
public class TextAnalyzer {

    private FrequencyTable frequencyTable;

    /**
     * Sets the frequency table
     * @param frq
     */
    public void setFrequencyTable(FrequencyTable frq) {
        this.frequencyTable = frq;
    }

    /**
     * Calculates which passages are similar to each other.
     * @param passages
     */
    public void calculateSimilarities(ArrayList<Passage> passages) {
        int x = 0;
        for(Passage current: passages) {
            HashMap<String, Double> similarTitles = new HashMap<>();
            int y = 0;
            for (Passage second: passages) {
                if(x != y) {
                    similarTitles.put(second.getTitle(), Passage.cosineSimilarity(current, second));
                }
                y++;
            }
            current.setSimilarTitles(similarTitles);
            x++;
        }

    }

    /**
     * Finds texts which are 60% or more close to each other and prints out a message stating they are suspected.
     * @param passages
     * @return
     */
    public ArrayList<String> findSuspectedAuthors(ArrayList<Passage> passages) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> availability = new ArrayList<>();
        for (Passage p: passages) {
            HashMap<String, Double> similarTitles = p.getSimilarTitles();
            for(Map.Entry<String, Double> e: similarTitles.entrySet()) {
                if(e.getValue() >= 0.6) {
                    if(!availability.contains(p.getTitle()+e.getKey()) && !availability.contains(e.getKey()+p.getTitle())) {
                        result.add("'"+ p.getTitle()+"' and '"+e.getKey()+"' may have the same author (" + Math.round(e.getValue()*100) +"% similar).");
                        availability.add(p.getTitle()+e.getKey());
                    }
                }
            }
        }
        return result;

    }

    /**
     * Main method that prompts the user to enter a directory for the program to search for texts and determine
     * the occurences of each word.
     * @param args
     */
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try {
            System.out.println("Enter the directory of a folder of text files: ");
            String directoryPath = scn.nextLine();
            ArrayList<Passage> passageList = new ArrayList<>();

            System.out.println("\nReading texts...\n");

            File[] directoryOfFiles = new File(directoryPath).listFiles();
            for (File i : directoryOfFiles) {
                String fileName = i.getName().replaceFirst("[.][^.]+$", "");

                //ignore files without txt extension
                if (i.getName().toUpperCase().endsWith(".TXT")) {

                    if (!fileName.equals("StopWords")) {
                        passageList.add(new Passage(directoryPath, fileName, i));
                    }
                }
            }


            TextAnalyzer textAnalyzer = new TextAnalyzer();
            textAnalyzer.setFrequencyTable(FrequencyTable.buildTable(passageList));
            textAnalyzer.calculateSimilarities(passageList);
            final Object[][] table = new String[passageList.size()][];
            int pos = 0;
            for (Passage p : passageList) {
                table[pos++] = new String[]{p.getTitle(), " | ", p.toString()};
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("%-30s%-5s%-50s\n", "Text (title)", " | ", "Similarities (%)");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

            for (final Object[] row : table) {
                System.out.format("%-30s%-5s%-50s\n", "", " | ", "");
                System.out.format("%-30s%-5s%-50s\n", row);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

            }

            System.out.println("\nSuspected Texts With Same Authors");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            ArrayList<String> suspectedResult = textAnalyzer.findSuspectedAuthors(passageList);
            if (suspectedResult.size() > 0) {
                for (String str : suspectedResult) {
                    System.out.println(str);
                }
            } else {
                System.out.println("None");
            }

            System.out.println("\nProgram terminating...\n");
        } catch (NullPointerException a) {
            System.out.println("There is no such file");
        }
    }}