// Cameron Bernard
// ca757863
// RunLikeHell
// COP3503 Fall 2020

class RunLikeHell {

    public static int maxGain(int[] blocks)
    {
      int[] mostKnow = new int[3];

      if (blocks.length == 0 || blocks == null)
      {
        // If there are no blocks, it will hit nothing
        return 0;
      }
      else if (blocks.length == 1)
      {
        // If there is 1 block, it will hit it
        return blocks[0];
      }
      else if (blocks.length == 2)
      {
        // If there are 2 blocks, it can only hit one of them
        if (blocks[0] > blocks[1])
        {
          return blocks[0];
        }
        else
        {
          return blocks[1];
        }
      }
      else if (blocks.length == 3)
      {
        // If there are 3 blocks, it can only hit the first and last one or the middle
        if (blocks[1] > blocks[0] + blocks[2])
        {
          return blocks[1];
        }
        else
        {
          return blocks[0] + blocks[2];
        }
      }

      mostKnow[0] = blocks[0];
      mostKnow[1] = blocks[1];
      mostKnow[2] = blocks[0] + blocks[2];

      // The mod 3 is to index it to the 3 places in mostKnow, representing the 3 relevant blocks
      // Additionally, i = 3 to skip the first 3 which were already assigned
      // runs in O(n)
      for (int i = 3; i < blocks.length; i++)
      {

          if (mostKnow[(i - 2) % 3] + blocks[i] > mostKnow[(i - 1) % 3] || mostKnow[i % 3] + blocks[i] > mostKnow[(i - 1) % 3])
          {
            // Hits the current block
            if (mostKnow[(i - 2) % 3] > mostKnow[i % 3])
            {
              mostKnow[i % 3] = mostKnow[(i - 2) % 3] + blocks[i];
            }
            else
            {
              mostKnow[i % 3] = mostKnow[i % 3] + blocks[i];
            }
          }
          else
          {
            // Skips the current block
            mostKnow[i % 3] = mostKnow[(i - 1) % 3];
          }
      }

      // The last block will have the final number
      return mostKnow[(blocks.length - 1) % 3];
    }

    public static double difficultyRating()
    {
      return 2.5;
    }

    public static double hoursSpent()
    {
      return 5.0;
    }
}
