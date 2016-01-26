package yahtzee;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
/**
 * Some utility classes
 * 
 * @author Taylor
 * @version 11/7/13
 */
public class Utility
{
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int STAY = 4;
	//pauses the program for time milliseconds
	public static void wait(int millisecs)
	{
		try 
		{
			Thread.sleep(millisecs);
		}
		catch (InterruptedException e)
		{            
		}
	}

	public static void clearConsole()
	{
		System.out.println('\u000C');
	}

	/**
	 * generates a random integer between the min and max
	 */
	public static int randomInteger(int min, int max)
	{
		int range = max - min;
		return (int)((Math.random() * range) + min);
	}

	/**
	 * generates a random integer with smallest number and a range
	 */
	public static int randomIntegerRange(int min, int range)
	{
		return (int)((Math.random() * range) + min);
	}

	/**
	 * generates a random decimal between the min and max
	 */
	public static double randomDouble(double min, double max)
	{
		double range = max - min;
		return Math.random() * range + min;
	}

	/**
	 * asks the user yes or no
	 */
	public static boolean yesOrNo()
	{
		boolean validInput;
		String input;
		Scanner s = new Scanner(System.in);
		do
		{
			input = s.next();
			if (input.equals("Yes") == true || input.equals("yes") == true || input.equals("y") == true 
					|| input.equals("Y") == true || input.equals("Yeah") == true || input.equals("yeah") == true
					|| input.equals("Yes") == true || input.equals("YES") == true) 
			{
				s.close();
				return true;
			}
			else if (input.equals("No") == true || input.equals("no") == true || input.equals("n") == true 
					|| input.equals("N") == true || input.equals("NO") == true)
			{   
				s.close();
				return false;
			}
			else  //user didn't input yes or no
			{
				System.out.println("Y or N please.");
				validInput = false;
			}
		}
		while (!validInput);
		s.close();
		return false;
	}
	//it's a secret... shhhh
	public static void secret()
	{
		wait(60000);
		System.out.println("That's it. You can go now.");
		wait(60000);
		System.out.println("You've spent two minutes staring at like 5 lines." + 
				"\nThere is nothing else to see here");
		wait(180000);
		System.out.println("Ok now it's been 5 minutes. Do you really want a secret that badly?");
		wait(30000);
		System.out.println("Fine. If you wait for another hour you'll get a secret.");
		for (int i = 0; i < 62; i++)
			wait(60000);
		System.out.println("The secret is that it was actally 62 minutes." +
				"\nI bet you're disapointed.");        
	}  

	/**
	 * Prints the text to the selected file
	 * @param fileName 
	 * @param textToBeWritten
	 */
	public static void printToFile(String fileName, String textToBeWritten) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), "utf-8"));
			writer.write("textToBeWritten");
		} catch (IOException ex) {
			// report
		} finally {
			try {writer.close();} catch (Exception ex) {}
		}
	}
}
