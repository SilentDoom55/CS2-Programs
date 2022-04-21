// Cameron Bernard
// ca757863
// TopoPaths
// COP 3503, Fall 2020

import java.io.*;
import java.util.*;

// Foreword: In any given graph there can only be a maximum of one
// valid topopath as every graph which has a topopath will simply
// be a straight line, thus countTopoPaths is basically a boolean
// function as it can only return 0 (false) or 1 (true).

public class TopoPaths
{
  public static final double difficulty = 2.0;
  public static final double hours = 3;

  public static int countTopoPaths(String filename)
  {
    try
    {
      // Initializing the scanner
      Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));

      // Declaring and Initializing variables needed for later
      int numVert = s.nextInt();
  		int tailIndex = -10;
      int edgeIndex, numEdges;

      // Declaring and Initializing the storage for all edges
  		ArrayList<ArrayList<Integer>> edgeList = new ArrayList<ArrayList<Integer>>(numVert);
  		for (int i = 0; i < numVert; i++)
      {
        edgeList.add(i, new ArrayList<Integer>());
      }

  		// Reading in the graph for processing.
      // This section has a few short-circuit situations that can return 0 if
      // it knows that the graph will not have a valid topograph.
  		for (int i = 0; i < numVert; i++)
  		{
  			numEdges = s.nextInt();
        // If this occurs, then there is a cycle as it must point to itself.
  			if (numEdges == numVert)
        {
          return 0;
        }
  			else if (numEdges > 0)
  			{
  				for (int j = 0; j < numEdges; j++)
  				{
            // Reads in the edge index and zero indexes it.
  					edgeIndex = s.nextInt() - 1;
            // If this occurs, then there is a cycle.
  					if (edgeIndex == i)
            {
              return 0;
            }
  					else
            {
              edgeList.get(edgeIndex).add(i);
            }
  				}
  			}
  			else
  			{
          // This only occurs when the vertex doesnt point to another vertex.
          // This means that it must be the tail of the graph.
  				tailIndex = i;
  			}
  		}
  		// If this occurs, then there is a cycle because the tailIndex has not
      // been updated, so there is no tail.
  		if (tailIndex == -10)
      {
        return 0;
      }
  		else
  		{
  			// Based on the foreward, if the maximum path length + 1 must equal the number of vertices
        // in order for this to be a valid topograph.
  			if (getPathLen(edgeList, tailIndex) + 1 == numVert)
        {
  				return 1;
        }
  			else
        {
          return 0;
        }
  		}
    }
    catch(IOException e)
    {
      return 0;
    }
  }

  // Finds the maximum length of a path from the tailIndex in O(n^2) time.
  private static int getPathLen(ArrayList<ArrayList<Integer>> edgeList, int currIndex)
  {
    int maxLength, currLength;
    // If this occurs, then there is a cycle (or only 1 vertex).
    if (edgeList.get(currIndex).size() == 0)
    {
      return 0;
    }
    else
    {
      maxLength = 0;
      // Goes through every possible path from each vertex
      for (int i = 0; i < edgeList.get(currIndex).size(); i++)
      {
        currLength = 1;
        // Recursively add all the nodes in the path.
        currLength += getPathLen(edgeList, edgeList.get(currIndex).get(i));
        if (currLength > maxLength)
        {
          maxLength = currLength;
        }
      }
      return maxLength;
    }
  }

  // Returns a difficultyRating of the assignment from 0.0 to 5.0.
  public static double difficultyRating()
  {
    return difficulty;
  }

  // Returns the approximate number of hours spent on this assignment.
  public static double hoursSpent()
  {
    return hours;
  }
}
