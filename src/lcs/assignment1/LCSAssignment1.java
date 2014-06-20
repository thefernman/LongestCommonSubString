/* File: LCSAssignment1.java
 I affirm that this program is entirely my own work and
 none of it is the work of any other person.

 ____________________________
 (your signature)


 @author Fernando Campo 1299228 COP 3530 Data Structures MWF 10:45 Summer 2014
 */
package lcs.assignment1;

//Imports.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Inner class used to implement our own String (MyString) class. Used to create
 * substrings and various other methods. MyString class implements Comparable
 * interface to override the compareTo() to give the order of MyStrings.
 *
 * @author Fernando Campo 1299228
 */
class MyString implements Comparable<MyString>
{

    private String source;
    private int startIndex;
    private int length;

    /**
     * MyString constructor. Used to create a MyString object from a String and
     * a starting index.
     *
     * @param original String to create a MyString object from.
     * @param SI Starting index from which substring start from original string.
     */
    public MyString(String original, int SI)
    {
        source = original;
        startIndex = SI;
        length = source.length() - startIndex;
    }

    /**
     * Substring method used to create substring from a starting index to an
     * ending index. Returns a string as the substring.
     *
     * @param SI Starting index to create the substring from the original
     * string.
     * @param SE Ending index to create the substring from the original string.
     * @return Return a string of a substring of given starting index and ending
     * index.
     */
    public String substring(int SI, int SE)
    {
        return source.substring(startIndex + SI, startIndex + SE);
    }

    /**
     * Overridden compareTo method from the Comparable Interface. Compares this
     * MyString with the specified MyString for order. Returns a negative
     * integer, zero, or a positive integer as this substring comes before,
     * equal to, or comes after than the specified substring by ascending order.
     *
     * @param other MyString object to be compare to.
     * @return Returns a negative integer, zero, or a positive integer.
     */
    @Override
    public int compareTo(MyString other)
    {
        int firstStart = startIndex;
        int secondStart = other.startIndex;

        String firstStr = source;
        String secondStr = other.source;

        int firstEnd = startIndex + length;
        int secondEnd = other.startIndex + other.length;

        //While characters match and substring belong to different text files,
        //move to the next character.
        while (firstStart < firstEnd
                && secondStart < secondEnd
                && firstStr.charAt(firstStart)
                == secondStr.charAt(secondStart))
        {
            ++firstStart;
            ++secondStart;
        }

        //If first substring reach its end.
        if (firstStart == firstEnd)
        {
            //if second substring reach its end.
            if (secondStart == secondEnd)
            {
                return 0; //both MyString are equal.
            }
            else
            {
                return -1; //first substring first.
            }
        }
        if (secondStart == secondEnd)
        {
            return 1; //second substring second
        }
        //Otherwise, compute the numeric value of the characters, if negative it
        //comes before, if zero its equal, and if positive its comes after.
        return firstStr.charAt(firstStart) - secondStr.charAt(secondStart);
    }

    /**
     * Accessor method to return the character at a given index.
     *
     * @param idx Index of character to return.
     * @return Return the character at that index.
     */
    public char charAt(int idx)
    {
        return source.charAt(startIndex + idx);
    }

    /**
     * Accessor method to return a MyString's length.
     *
     * @return Return an int of a MyString's length.
     */
    public int length()
    {
        return length;
    }

    /**
     * Accessor method to return a MyString's start index.
     *
     * @return Return an int of a MyString's start index.
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * Overridden toString method to return a String of the MyString object.
     *
     * @return Return a string version of the MyString object.
     */
    @Override
    public String toString()
    {
        return source.substring(startIndex);
    }
}//end of MyString class

/**
 * Java program that accepts two files and outputs the longest common substring
 * of the two files. Uses StringBuilder to creates a single string that contains
 * both text files, creates an array of substring (MyString) objects, sorts, and
 * finds the longest common substring from both files by looking at each
 * adjacent pair of elements. Also computes the time taken to find the longest
 * common substring.
 *
 * @author Fernando Campo 1299228 COP 3530 Data Structures MWF 10:45 Summer 2014
 */
public class LCSAssignment1
{

    //static varible.
    private static int special = 0;

    /**
     * Finds how many characters are common between two MyString objects.
     *
     * @param lhs First MyString object.
     * @param rhs Second MyString object.
     * @param half Index where the special character is located between the two
     * files.
     * @return Return an int of how many characters are in common between two
     * MyString objects.
     */
    public static int longestPrefix(MyString lhs, MyString rhs, int half)
    {
        int len = 0;
        //if statement check if both MyString objects are from different files 
        //or on each side of my half way point.
        if (lhs.getStartIndex() < half && rhs.getStartIndex() > half
                || lhs.getStartIndex() > half && rhs.getStartIndex() < half)
        {
            //while len hasn't reach the end of the substrings and the 
            //characters match, increment the index position to check or len.
            while (len < lhs.length() && len < rhs.length()
                    && lhs.charAt(len) == rhs.charAt(len))
            {

                ++len;
            }
        }
        return len;
    }

    /**
     * Accepts files to be read from, constructs a StringBuilder object with a
     * special character between the two, and returns the string version of the
     * combined files.
     *
     * @param files String [] of files names or args[] from main.
     * @return Returns a string of the combined files.
     * @throws FileNotFoundException Throws exception if no file are found.
     */
    public static String readFile(String[] files) throws FileNotFoundException
    {
        StringBuilder sb = new StringBuilder();
        Scanner fileScan = new Scanner(new File(files[0]));
        //while not end of file.
        while (fileScan.hasNext())
        {
            sb.append(fileScan.next());
            //append space if file has next, but doesn't add space at EOF.
            if (fileScan.hasNext())
            {
                sb.append(" ");
            }
        }
        sb.append("☺");
        special = sb.length() - 1;
        fileScan = new Scanner(new File(files[1]));
        while (fileScan.hasNext())
        {
            sb.append(fileScan.next());
            if (fileScan.hasNext())
            {
                sb.append(" ");
            }
        }
        String str = new String(sb);
        return str;

    }

    /**
     * Fills the suffix array with substrings from original string (MyString
     * objects).
     *
     * @param str Original string of combined files.
     * @param suffixes Suffix array to be filled.
     */
    public static void fillSuffixes(String str, MyString[] suffixes)
    {
        for (int i = 0; i < suffixes.length; i++)
        {
            suffixes[i] = new MyString(str, i);
        }
    }

    /**
     * Finds the maximum longest common substring length from the LCP array.
     *
     * @param LCP Longest common prefix array.
     * @return Return the int of max longest common prefix.
     */
    public static int findMaxLCPIndex(int[] LCP)
    {
        int maxLCPIndex = 0;
        for (int i = 1; i < LCP.length; i++)
        {
            if (LCP[ i] > LCP[ maxLCPIndex])
            {
                maxLCPIndex = i;
            }
        }
        return maxLCPIndex;
    }

    /**
     * Finds the longest common substring from both files by looking at each
     * adjacent pair of elements in the suffixes array.
     *
     * @param suffixes Suffixes array containing all possible suffixes from both
     * files.
     * @return Return an int array that contains the longest common prefix
     * length of compared suffixes.
     */
    public static int[] findLCP(MyString[] suffixes)
    {
        int[] LCP = new int[suffixes.length];
        for (int i = 1; i < LCP.length; i++)
        {
            //calls the longestPrefix method for adjacent elements.
            LCP[ i] = longestPrefix(suffixes[ i], suffixes[ i - 1], special);
        }
        return LCP;
    }

    /**
     * Displays the longest common substring between the files for a given LCP
     * and suffixes array. Truncate the longest common substring if the length
     * is greater than 30 characters.
     *
     * @param LCP LCP array that contains the longest length of each substring
     * compared.
     * @param suffixes Suffix array that contains all the possible suffixes.
     */
    public static void displayResults(int[] LCP, MyString[] suffixes)
    {
        //finds the largest LCP number and assigns it.
        int maxLCPIndex = findMaxLCPIndex(LCP);
        //check if the length of the substring is less than 30 characters.
        //Then prints out the substring.
        if (LCP[ maxLCPIndex] < 30)
        {
            System.out.println("The longest common substring is "
                    + LCP[ maxLCPIndex]
                    + " characters '"
                    + suffixes[ maxLCPIndex].substring(0, LCP[ maxLCPIndex])
                    + "'\n");
        }
        //length of substring is greater than 30 characters, it truncates it.
        else
        {
            System.out.println("The longest common substring is "
                    + LCP[ maxLCPIndex]
                    + " characters '"
                    + suffixes[ maxLCPIndex].substring(0, 30)
                    + "...'\n");
        }
    }

    /**
     * main method that calls the other methods and solves for the longest
     * common substring between two files.
     *
     * @param args the command line arguments or files names used
     */
    public static void main(String[] args) throws FileNotFoundException
    {

        if (args.length == 2)
        {
            System.out.println("Files used: " + args[0] + " and " + args[1]);
            //Read from file and create a String
            String str = readFile(args);

            //Starting the clock.
            long start = System.currentTimeMillis();

            //creating array for suffixes.
            MyString[] suffixes = new MyString[str.length()];
            //computes and fills the suffix array.
            fillSuffixes(str, suffixes);

            //Sort suffix array.
            System.out.println("\nSorting suffix array...");
            Arrays.sort(suffixes);

            System.out.println("\nFinding longest common prefix..");
            //computes and fill the LCP array with longest common substring 
            //length
            int[] LCP = findLCP(suffixes);

            //Stopping the clock.
            long end = System.currentTimeMillis();

            System.out.println("\nDisplay results:\n");
            //Displays results.
            displayResults(LCP, suffixes);

            //Displaying time taken to compute answer
            long elapsed = end - start;
            System.out.println("Time taken was " + elapsed + "ms.");
        }
    }//end of main
}//end of LCSAssignment1 class
