import java.util.regex.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * This program searches through a plain text file for specific regular expression patterns and
 * outputs a text file with a count of each pattern.
 * @author Kirin Sharma
 * @version 1.0
 * 
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */

 public class NovelProcessor
 {
    static Scanner scan = new Scanner(System.in); // static scanner to get user input

    public static void main(String[] args) throws IOException
    {
        // Get file names from user and initializes scanners to read respective novel txt file and txt file with regex patterns
        System.out.print("Enter the file name of the novel: ");
        String novelName = scan.nextLine();
        Scanner novelScanner = new Scanner(new File(novelName));
        System.out.print("Enter the file name of the regular expressions: ");
        Scanner regexScanner = new Scanner(new File(scan.nextLine()));

        // Create hashmap of patterns in regex file and initialize their counts to 0
        HashMap<Pattern, Integer> patternCounts = new HashMap<Pattern, Integer>();
        while(regexScanner.hasNext())
        {
            String currentPattern = regexScanner.next();
            patternCounts.put(Pattern.compile(currentPattern, Pattern.CASE_INSENSITIVE), 0);
        }
        regexScanner.close();

        // Scan novel word-by-word and compare to each pattern in the pattern hashmap
        while(novelScanner.hasNext())
        {
            String currentWord = novelScanner.next();

            // Check each pattern (in set of keys) and update its count if the pattern is found
            for(Pattern p : patternCounts.keySet())
            {
                if(p.matcher(currentWord).find())
                {
                    patternCounts.put(p, patternCounts.get(p) + 1);
                    break; // if one pattern found, no need to continue loop
                }
            } // end foreach
        } // end while
        novelScanner.close();

        // Write output file using created hashmap
        String outputFileName = novelName.replace(".txt", "_wc.txt"); // create name of output file using input filename and appending _wc
        FileWriter fw = new FileWriter(outputFileName);
        int i = 0; // Counter to not add empty line at end of file
        for(Pattern p : patternCounts.keySet())
        {
            fw.write(p.toString() + "|" + patternCounts.get(p));

            if(i++ < patternCounts.size() - 1)
                fw.write("\n"); // insert line break if not last iteration
        }
        fw.close();

    } // end main

 } // end class
