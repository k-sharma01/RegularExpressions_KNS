import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.regex.*;

/**
 * This program parses a UNIX log file and is able to print the data collected
 * @author Kirin Sharma
 * @version 1.0
 * 
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */

public class LogFileProcessor 
{
    public static int lineCount = 0; // counter for lines parsed
    public static HashMap<String, Integer> IPHashmap = new HashMap<String, Integer>(); // hashmap of ip addresses and their counts
    public static HashMap<String, Integer> userHashMap = new HashMap<String, Integer>(); // hashmap of usernames and their counts

    public static void main(String[] args) throws IOException
    {
        String fileName = args[0]; // log file name passed in command line args
        int flag = Integer.parseInt(args[1]); // flag passed in command line args

        parseLogFile(fileName); // Parses log file and updates all hashmaps and necessary data

        // Decide which output to print depending on flag
        if(flag == 1)
            printIPHashmap();
        else if(flag == 2)
            printUsernameHashmap();
        else        
            printDefaultOutput();

    } // end main


    /**
     * Prints basic info about how many lines were parsed, count of unique users and IP addresses in log file
     */
    public static void printDefaultOutput()
    {
        System.out.println();
        System.out.println(lineCount + " lines in the log file were parsed.");
        System.out.println("There are " + IPHashmap.size() + " unique IP addresses in the log file.");
        System.out.println("There are " + userHashMap.size() + " unique users in the log file.\n");
    } // end printDefaultOutput


    /**
     * Method to print the unique IP addresses and the count of how many times they appear in log file
     */
    public static void printIPHashmap()
    {
        for(String IP : IPHashmap.keySet())
        {
            System.out.println(IP + ": " + IPHashmap.get(IP));
        }
        printDefaultOutput(); // Prints additional default info
    } // end printIPHashmap


    /**
     * Method to print the unique usernames and the number of times they appear in log file
     */
    public static void printUsernameHashmap()
    {
        for(String user : userHashMap.keySet())
        {
            System.out.println(user + ": " + userHashMap.get(user));
        }
        printDefaultOutput(); // Prints additional default info
    } // end printUsernameHashmap


    /**
     * Scans log file line by line searching for regex patterns of users and IP addresses and updates their counts in their respective hashmaps
     * @param fileName the name of the log file to process
     * @throws IOException
     */
    public static void parseLogFile(String fileName) throws IOException
    {
        Scanner logReader = new Scanner(new File(fileName)); // Scanner to read the log file
        Pattern userPattern = Pattern.compile("^user$"); // regular expression used to denote a user
        Pattern IPPattern = Pattern.compile("(((25[0-5])|(2[0-4][0-9])|([1]?[0-9][0-9]?))(\\.)){3}((25[0-5])|(2[0-4][0-9])|(1?[0-9]?[0-9]))"); // regex used to denote ip address

        // While loop reads file line by line
        while(logReader.hasNextLine())
        {
            // Reads the current line in and updates line count
            String currentLine = logReader.nextLine();
            lineCount++;

            Scanner lineScanner = new Scanner(currentLine); // Scanner to read current line word-by-word

            // Goes through line word-by-word looking for matches to the regular expressions
            while(lineScanner.hasNext())
            {
                String currentWord = lineScanner.next();
                Matcher userMatcher = userPattern.matcher(currentWord);
                Matcher IPMatcher = IPPattern.matcher(currentWord);

                // If the a match to 'user' is found, this means that the next word is a username
                if(userMatcher.find())
                {
                    String userName = lineScanner.next();
                    if(userHashMap.containsKey(userName))
                        userHashMap.put(userName, userHashMap.get(userName) + 1);
                    else
                        userHashMap.put(userName, 1);
                } 
                else if(IPMatcher.find()) // check for matches for a valid IPV4 address
                {
                    String match = IPMatcher.group();
                    if(IPHashmap.containsKey(match))
                        IPHashmap.put(match, IPHashmap.get(match) + 1);
                    else
                        IPHashmap.put(match, 1);
                }
            } // end while

           lineScanner.close();

        } // end while

        logReader.close();

    } // end parseLogFile

} // end class
