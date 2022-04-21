//Cameron Bernard
//SneakyQueens
//COP3503 Fall 2020
//This program finds if a list of queens on a given chess board are safe from attacking each other or not.

import java.util.*;

public class SneakyQueens
{
    // Variables for use later in the program.
    public static final double difficulty = 2.7;
    public static final double hours = 4;

    public static void main(String [] args) throws Exception
  	{
      ArrayList<String> list = new ArrayList<String>();

      list.clear();
      list.add("lcz2157");//
      /*list.add("cwd2592");
      list.add("dme2150");
      list.add("hut693");
      list.add("bul4331");
      list.add("ahh1602");
      list.add("amf6674");
      list.add("izk1442");
      list.add("cwo553");
      list.add("nha2174");
      list.add("ebd2934");*/
      System.out.println(SneakyQueens.allTheQueensAreSafe(list, 10000));
    }

    // allTheQueensAreSafe
    // Takes ArrayList of coordStrings representing locations of queens and int of boardSize representing the size of the board
    // @param boardSize the size of one side of the square board. Between 0 and 60,000
    // @param coordStrings contains the coordinates of each queen. Will be non-null
    // @return boolean returns true if all queens are not attacking each other, returns false otherwise.
    public static boolean allTheQueensAreSafe(ArrayList<String> coordStrings, int boardSize)
    {
      int length = coordStrings.size();

      // Create arrays representing each column, row, and both diagonals.
      int[] col = new int[boardSize];
      int[] row = new int[boardSize];
      int[] diagPos = new int[boardSize * 2];
      int[] diagNeg = new int[boardSize * 2];

      for(int i = 0; i < length; i++)
      {
        // Takes one queen at a time to work with.
        char[] coord = coordStrings.get(i).toCharArray();

        // Setting up and resetting variables for later use
        int coordCol = 0;
        int coordRow = 0;
        int coordPos = 0;
        int coordNeg = 0;
        ArrayList<Character> tempPos = new ArrayList<Character>();

        // Loops through the character string to find its coordinates
        for(int j = 0; j < coord.length; j++)
        {
          try
          {
            if(Character.isLetter(coord[j]))
            {
              // Transfers all the letters to an ArrayList to be worked on later.
              tempPos.add(coord[j]);
            }
            else
            {
              // Parses the row with the rest of the string as they will always be all numbers. Also, zero indexes it
              coordRow = Integer.parseInt(coordStrings.get(i).substring(j)) - 1;
              break;
            }

          }
          catch(Exception e)
          {
            // Catches any errors, there shouldnt be, but this is by far the most likely place for them to occur
          }
        }

        // Converts the base 26 lettering system to base 10 numbers to be able to work with them. Also 0 indexes it.
        for(int j = 0; j < tempPos.size(); j++){
          coordCol += (tempPos.get(j) - 'a' + 1) * Math.pow(26, tempPos.size()-j-1);
        }
        coordCol--;

        // The diagonals are graphs in y = mx + b where m = 1 or -1
        // and b = some y-intercept. We can find this intercept by subtracting
        // x from y or adding y to x. Given by the formulas y - x = b and y + x = break;
        // by adding boardSize to the former, there will never be a case where the position is negative.
        coordPos = coordRow - coordCol + boardSize;
        coordNeg = coordRow + coordCol;

        // Checks to make sure the queen is safe
        if(col[coordCol] == 1)
        {
          //System.out.println("Col");
          return false;
        }
        if(row[coordRow] == 1)
        {
          //System.out.println("Row");
          return false;
        }
        if(diagPos[coordPos] == 1)
        {
          //System.out.println("Pos");
          return false;
        }
        if(diagNeg[coordNeg] == 1)
        {
          //System.out.println("Neg");
          return false;
        }
        // Places the queen in that spot
        col[coordCol] = 1;
        row[coordRow] = 1;
        diagPos[coordPos] = 1;
        diagNeg[coordNeg] = 1;
      }
      // If the program has run through all queens without returning false, then they all must be safe
      return true;
    }

    // Returns difficulty of the assignment from 1.0 to 5.0
    public static double difficultyRating()
    {
      return difficulty;
    }

    // Returns the hours spent on the assignment
    public static double hoursSpent()
    {
      return hours;
    }
}
