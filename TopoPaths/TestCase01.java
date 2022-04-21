// Sean Szumlanski
// COP 3503, Fall 2020

// ==========================
// TopoPaths: TestCase01.java
// ==========================
// A brief test case to help ensure you've implemented the difficultyRating() method correctly.


import java.io.*;
import java.util.*;

public class TestCase01
{
	private static void failwhale()
	{
		System.out.println("Test Case #1: fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args)
	{
		double difficulty = TopoPaths.difficultyRating();
		if (difficulty < 1.0 || difficulty > 5.0) failwhale();

		System.out.println("Test Case #1: PASS");
	}
}
