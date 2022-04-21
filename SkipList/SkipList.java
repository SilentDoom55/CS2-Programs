// Cameron Bernard
// ca757863
// SneakyKnights
// COP3503 Fall 2020
// Implementation of a Skip List in Java.

import java.util.*;
import java.lang.*;

class Node<T>
{
  // Declaring all variables needed for Nodes
  private T value;
  private int currentHeight;
  public List<Node<T>> neighborNodes = new ArrayList<>();

  // Constructs a Node with a specific height
  public Node(int height)
  {
      value = null;
      currentHeight = height;

        for (int i = 0; i < height + 1; i++)
        {
          neighborNodes.add(null);
        }
  }

  // Constructs a Node with a specific height and initilizes value
  public Node(T data, int height)
  {
      value = data;
      currentHeight = height;

      for (int i = 0; i < height + 1; i++)
      {
        neighborNodes.add(null);
      }
  }

  // Returns the value stored at this node
  public T value()
  {
    return this.value;
  }

  // Returns the height of this node
  public int height()
  {
    return this.currentHeight;
  }

  // Returns a reference to the next node at this particular level
  public Node<T> next(int level)
  {
    if (level < 0 || level > neighborNodes.size() - 1)
    {
      return null;
    }
    return this.neighborNodes.get(level);
  }

  // Sets the next reference at "level" within this node to "node"
  public void setNext(int level, Node<T> node)
  {
    this.neighborNodes.set(level, node);
  }

  // Grows this node by exactly one level
  public void grow()
  {
    currentHeight++;
    this.neighborNodes.add(null);
  }

  // Grow this node 1 time 50% of the time
  public boolean maybeGrow()
  {
    if (Math.random() < 0.5)
    {
      grow();
      return true;
    }
    return false;
  }

  // Removes references from the top of this node's tower of next until this node's
  // height value has been reduced to "height"
  public void trim(int height)
  {
    for (int h = height; h < currentHeight; h++)
    {
      neighborNodes.set(h, null);
    }

    currentHeight = height;
  }
}

public class SkipList<T extends Comparable<T>>
{

  private Node<T> head;
  private int currentHeight;
  private int numData;
  private int maxLevel;

  SkipList()
  {
    numData = 0;
    currentHeight = 1;
    maxLevel = 1;
    head = new Node<T>(0);
    head.neighborNodes.add(null);
  }

  SkipList(int height)
  {
    numData = 0;
    currentHeight = height;
    maxLevel = height;
    head = new Node<T>(height);
    for (int i = 0; i < height; i++)
    {
      head.neighborNodes.add(null);
    }
  }

  // Returns the number of nodes in the skip list, except the head node
  public int size()
  {
    return numData;
  }

  // Returns the current height of the skip list
  public int height()
  {
    return maxLevel;
  }

  // Returns the head of the skip list
  public Node<T> head()
  {
    return head;
  }

  // Inserts data into the skip list at a random height
  public void insert(T data)
  {
	  int level = 0;
  	while (Math.random() < 0.5)
    {
      level++;
      if (level >= maxLevel && level >= getMaxHeight(numData))
      {
        break;
      }
    }
    insert(data, level);
  }

  // Inserts data into the skip list at a set height
  public void insert(T data, int height)
  {
    numData++;

    // Grows the skiplist and height appropriately
    if ((height > maxLevel) || (getMaxHeight(numData) > maxLevel))
    {
      growSkipList();
    }
    if (height > currentHeight)
    {
      currentHeight = height;
    }

    // finds the level the node needs to be at
	  int level = height - 1;
    if (level < 0)
    {
      level = 0;
    }

    // Inserts the node
    Node<T> newNode = new Node<>(data, height);
    Node<T> currNode = this.head();

    for (int i = maxLevel; i >= 0; i--)
    {
      while (currNode.next(i) != null)
      {
        if (greaterThan(currNode.next(i).value(), data))
        {
          break;
        }
        currNode = currNode.next(i);
      }
      if (i <= level)
      {
        newNode.setNext(i, currNode.next(i));
        currNode.setNext(i, newNode);
      }
    }
  }

  // Deletes a single occurence to data from the skip list
  public void delete(T data)
  {
    int i = maxLevel, iSnap;
    Node<T> curr = head;
    Node<T> term;
    while (curr != null){
      if (curr.next(i) == null)
      {
        if (i == 0)
        {
          return;
        }
        i--;
      }
      else if (equalTo(curr.next(i).value(), data))
      {
        term = curr.next(i);
        for (;i >= 0; i--)
        {
          curr.setNext(i, term.next(i));
          term.setNext(i, null);
        }
        numData--;
        if (getMaxHeight(numData) < maxLevel)
        {
          trimSkipList();
          maxLevel--;
        }
        return;
      }
      else if (greaterThan(data, curr.next(i).value()))
      {
        curr = curr.next(i);
      }
      else
      {
        if (i == 0)
        {
          return;
        }
        i--;
      }
    }
  }

  // Returns true if the skip list contains "data", false otherwise
  public boolean contains(T data)
  {
    Node<T> curr = head;
    int i = maxLevel;
    while (curr != null){
      if (curr.next(i) == null)
      {
        if (i == 0)
        {
          return false;
        }
        i--;
      }
      else if (equalTo(curr.next(i).value(), data))
      {
        return true;
      }
      else if (greaterThan(curr.next(i).value(), data))
      {
        curr = curr.next(i);
      }
      else
      {
        if (i == 0)
        {
          return false;
        }
        i--;
      }
    }
    return false;
  }

  // Returns a reference to a node in the skiplist that contains "data"
  public Node<T> get(T data)
  {
    Node<T> curr = head;
    int i = maxLevel;
    while (curr.next(i) != null){
      if (equalTo(curr.next(i).value(), data))
      {
        return curr.next(i);
      }
      else if (greaterThan(curr.next(i).value(), data))
      {
        curr = curr.next(i);
      }
      else
      {
        if (i == 0)
        {
          return null;
        }
        i--;
      }
    }
    return null;
  }

  // Returns the max height of a skip list with n nodes
  private static int getMaxHeight(int n)
  {
    return (int)Math.ceil((Math.log(n) / Math.log(2)));
  }

  // Returns 1 with 50% probability, 2 with 25% and so on
  private static int generateRandomHeight(int maxHeight)
  {
    int height = 0;
    while (height < maxHeight)
    {
      if (Math.random() < 0.5)
      {
        height++;
      }
      else
      {
        return height;
      }
    }
    return height;
  }

  // Grows the skip list
  private void growSkipList()
  {
    this.head().grow();
    Node<T> past = head;
    Node<T> curr = head.next(maxLevel);
    while (curr != null)
    {
      if (curr.maybeGrow())
      {
        past.setNext(maxLevel + 1, curr);
      }
      past = curr;
    }
    maxLevel++;
  }

  // Trims the skip list
  private void trimSkipList()
  {
    trimSkipList(head);
  }
  // Trims the skip list
  private void trimSkipList(Node<T> node)
  {
    if (node == null)
    {
      return;
    }
    trimSkipList(node.next(maxLevel));
    node.trim(maxLevel - 1);

  }

  // Custom methods made to compare generics
  private boolean lessThan(T a, T b)
  {
    return a.compareTo(b) < 0;
  }

  private boolean greaterThan(T a, T b)
  {
    return a.compareTo(b) > 0;
  }

  private boolean equalTo(T a, T b)
  {
    return a.compareTo(b) == 0;
  }

  // Method created for testing purposes, prints the list by level
  public void printList(){
    Node<T> curr = head;
    for (int i = maxLevel; i >= 0; i--)
    {
      curr = head;
      // System.out.println("level = " + i + "\n");
      while (curr != null)
      {
        // System.out.println("Height: " + curr.height());
        // System.out.println("Value:  " + curr.value());
        curr = curr.next(i);
      }
      // System.out.println(" ");
    }
  }


  // Rating of difficulty of the assignment from 0.0-5.0
  public static double difficultyRating()
  {
    return 3.7;
  }

  // Number of hours spent on the assignment.
  public static double hoursSpent()
  {
    return 12.0;
  }
}
