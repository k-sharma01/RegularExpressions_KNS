import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * This program searches through output files created by the NovelProcessor and
 * determines the total amount of times each regex pattern appeared across all the novels
 * @author Kirin Sharma
 * @version 1.0
 * 
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */

public class WordCounter 
{
    public static void main(String[] args) throws IOException
    {
        // Array of output files to search through
        Scanner[] outputFiles = new Scanner[6];

        // Creates scanners of output files to search through, adds them to array
        Scanner draculaOutput = new Scanner(new File("dracula_wc.txt"));
        outputFiles[0] = draculaOutput;
        Scanner frankensteinOutput = new Scanner(new File("frankenstein_wc.txt"));
        outputFiles[1] = frankensteinOutput;
        Scanner jekyllHydeOutput = new Scanner(new File("jekyll_and_hyde_wc.txt"));
        outputFiles[2] = jekyllHydeOutput;
        Scanner dorianGrayOutput = new Scanner(new File("the_picture_of_dorian_gray_wc.txt"));
        outputFiles[3] = dorianGrayOutput;
        Scanner turnOfScrewOutput = new Scanner(new File("the_turn_of_the_screw_wc.txt"));
        outputFiles[4] = turnOfScrewOutput;
        Scanner castleOtrantoOutput = new Scanner(new File("the_castle_of_otranto_wc.txt"));
        outputFiles[5] = castleOtrantoOutput;

        HashMap<String, Integer> finalCounts = new HashMap<String, Integer>(); // Hashmap to hold final counts of each pattern

        // Loop through each output file line-by-line and update finalCounts hashmap
        for(Scanner s : outputFiles)
        {
            String currentLine;
            String pattern; // the pattern on the current line of the output file
            Integer count; // the count on the current line of the output file

            while(s.hasNextLine())
            {
                currentLine = s.nextLine();

                pattern = currentLine.substring(0, currentLine.lastIndexOf('|')); // gets pattern by returning substring from beginning to the last |
                count = Integer.parseInt(currentLine.substring(currentLine.lastIndexOf('|') + 1)); // returns substring starting after last | going to end

                if(finalCounts.containsKey(pattern))
                    finalCounts.put(pattern, finalCounts.get(pattern) + count);
                else
                    finalCounts.put(pattern, count);
            }
        } // end for

        // Print the finalCounts hashmap to the terminal
        System.out.println();
        for(String s : finalCounts.keySet())
            System.out.println(s + "|" + finalCounts.get(s) + "\n");

        // Close scanners
        draculaOutput.close();
        frankensteinOutput.close();
        castleOtrantoOutput.close();
        dorianGrayOutput.close();
        jekyllHydeOutput.close();
        turnOfScrewOutput.close();
        
    } // end main

} // end class
