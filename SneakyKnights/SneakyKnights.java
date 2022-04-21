// Cameron Bernard
// ca757863
// SneakyKnights
// COP3503 Fall 2020
// This program finds if a list of knights on a given chess board are safe from attacking each other or not.

import java.awt.*;
import java.util.*;

public class SneakyKnights{

  // Variables for use later
  public static final double difficulty = 3.0;
  public static final double hours = 5;

  public static void main(String [] args)
  {

  }

  public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {
    // Initialize variables needed to find and record points of attack and overlaps
    Point center;
    Point northWest;
    Point northEast;
    Point eastNorth;
    Point eastSouth;
    Point southEast;
    Point southWest;
    Point westSouth;
    Point westNorth;

    int col, row;

    HashSet<Point> knightPoints = new HashSet<>();
    HashSet<Point> attackPoints = new HashSet<>();

    for (int i = 0; i < coordinateStrings.size(); i++)
    {
      center = parseCoords(coordinateStrings.get(i));

      // If there is a duplicate knight found or if the knight is going
      // to be in a place being attacked, then not all knights are safe
      if ((!knightPoints.add(center)) || (attackPoints.contains(center)))
      {
        return false;
      }

      // These will be called a lot, so I call them here to avoid
      // calling them over and over again in the future and can
      // instead just use these variables
      col = (int)center.getX();
      row = (int)center.getY();

      // These check to see if the attack point is on the board
      // and if any knights are already placed there, if not we
      // add the points to the list of points being attacked
      // northWest
      if (((col + 2) < boardSize) && ((row - 1) > 0))
      {
        northWest = new Point(col + 2, row - 1);
        if (!knightPoints.contains(northWest))
        {
          attackPoints.add(northWest);
        }
        else
        {
          return false;
        }
      }

      // northEast
      if (((col + 2) < boardSize) && ((row + 1) < boardSize))
      {
        northEast = new Point(col + 2, row + 1);
        if (!knightPoints.contains(northEast))
        {
          attackPoints.add(northEast);
        }
        else
        {
          return false;
        }
      }

      // eastNorth
      if (((col + 1) < boardSize) && ((row + 2) < boardSize))
      {
        eastNorth = new Point(col + 1, row + 2);
        if (!knightPoints.contains(eastNorth))
        {
          attackPoints.add(eastNorth);
        }
        else
        {
          return false;
        }
      }

      // eastSouth
      if (((col - 1) > 0) && ((row + 2) < boardSize))
      {
        eastSouth = new Point(col - 1, row + 2);
        if (!knightPoints.contains(eastSouth))
        {
          attackPoints.add(eastSouth);
        }
        else
        {
          return false;
        }
      }

      // southEast
      if (((col - 2) > 0) && ((row + 1) < boardSize))
      {
        southEast = new Point(col - 2, row + 1);
        if (!knightPoints.contains(southEast))
        {
          attackPoints.add(southEast);
        }
        else
        {
          return false;
        }
      }

      // southWest
      if (((col - 2) > 0) && ((row - 1) > 0))
      {
        southWest = new Point(col - 2, row - 1);
        if (!knightPoints.contains(southWest))
        {
          attackPoints.add(southWest);
        }
        else
        {
          return false;
        }
      }

      // westSouth
      if (((col - 1) > 0) && ((row - 2) > 0))
      {
        westSouth = new Point(col - 1, row - 2);
        if (!knightPoints.contains(westSouth))
        {
          attackPoints.add(westSouth);
        }
        else
        {
          return false;
        }
      }

      // westNorth
      if (((col + 1) < boardSize) && ((row - 2) > 0))
      {
        westNorth = new Point(col + 1, row - 2);
        if (!knightPoints.contains(westNorth))
        {
          attackPoints.add(westNorth);
        }
        else
        {
          return false;
        }
      }
    }
    // If we get through all of that without any issues,
    // then we can say that we have a safe board.
    return true;
  }

  // Portion of code from my SneakyQueens program changed into a function for ease of use.
  public static Point parseCoords(String coords)
  {
    ArrayList<Character> tempPos = new ArrayList<Character>();
    int coordRow = 0;
    int coordCol = 0;

    for (int i = 0; i < coords.length(); i++)
    {
      try
      {
        if (Character.isLetter(coords.charAt(i)))
        {
          // Transfers all the letters to an ArrayList to be worked on later.
          tempPos.add(coords.charAt(i));
        }
        else
        {
          // Parses the row with the rest of the string as they will always be all numbers. Also, zero indexes it
          coordRow = Integer.parseInt(coords.substring(i)) - 1;
          break;
        }

      }
      catch(Exception e)
      {
        System.out.println(e);
        // Catches any errors, there shouldnt be, but this is by far the most likely place for them to occur
      }
    }

    // Converts the base 26 lettering system to base 10 numbers to be able to work with them. Also 0 indexes it.
    for (int i = 0; i < tempPos.size(); i++){
      coordCol += (tempPos.get(i) - 'a' + 1) * Math.pow(26, tempPos.size()-i-1);
    }
    coordCol--;

    //returns the new point that was created.
    return new Point(coordCol, coordRow);
  }

  public static double difficultyRating()
  {
    return difficulty;
  }

  public static double hoursSpent()
  {
    return hours;
  }

}
