import java.util.*;
import java.io.*;


/*
 	1. read the text until we find. pattern that is not in our table
	2. put that pattern into the table, output the code for everything BUT the last letter of that new pattern
	3. reset to only include what we have not output a code for
 */


public class Encoder

{

	//make table that has the list of string and their value
	/*
		EDIT 1
		ArrayLists - look up time - O(n)
		HashMap - look up time - O(1)
		ArrayLists - add time - O(1)
		HashMap - add time - O(1)
		look up + add are essential to this program, so minimize their time complexity
	*/
	HashMap<String, Integer> table = new HashMap<String, Integer>();
	//private ArrayList<String> table = new ArrayList<String>();

	//makes arrayList that stores LZW code
	/*
		EDIT 2
		code didn't actually do anything?
		eliminated it altogether
	*/
	//private ArrayList<Integer> code = new ArrayList<Integer>();

	private String prefix = ""; 
	private String pattern = ""; // represents everything but the newest read char
	private char readchar = 0; //this will represent the last char when checking a substring

	//empty constructor
	public Encoder () 
	{
	}

	public void encode (String fileName) throws IOException
	{
		try {

			//make buffered reader and file reader
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			//make printwriter so we can print as we encode
			PrintWriter writer = new PrintWriter(new FileWriter(fileName  + "encoded"));


			/*
				EDIT 3
				previously began at ascii character #32,' ', however it's totally possible to get a character prior to that, ex: #10,'\n'
				edited to hold the entire unextended ascii table
				also, hashmap update
			*/
			for(int a=0; a<128; a++)
				table.put((char)a+"", a);
			/*
				EDIT 4
				hashmap needs a corresponding # (the LZW value)
			*/
			int place = 128;
			/*for (int i=0; i<95; i++)
			{
				table.add(""+(char)(i+32)); //inputs values into table that are already in the ascii table; began at 33rd character to avoid weird chars
			}*/


			while (br.ready()) //read in file to code and add to input
			{

				readchar = (char) br.read(); //reads in one char from file
				pattern = prefix + readchar; //adds the char onto what has been read so far

				/*
					hashmap update
				*/
				if (table.containsKey(pattern) || pattern.length()==1) { //checks if the table alrady contains this pattern/if it's already part of the ascii table
					prefix = pattern; //sets prefix to the pattern; now when pattern = prefix + readchar loops it'll include what's alrady been read
					//code.add((int)readchar); //adds the char's index to the table
				}

				else {
					if (prefix.length()==1) { //checks if it is in ascii table as a single letter
						//code.add((int)pattern.charAt(0)); //adds the index to the list of codes
						writer.print((int)pattern.charAt(0) + ","); //prints the code of this pattern
					}

					else//if the pattern is not in table, it adds it to the table. also print this pattern
					{
						//code.add (33+table.indexOf(prefix)); //adds value of everything but last letter to code
						/*
							hashmap update
						*/
						writer.print(table.get(prefix) +","); //prints the code of this pattern
					}
					
					prefix = "" + readchar; // resets with only the last char of the sequence
					/*
						hashmap update
					*/
					table.put(pattern, place); //adds this pattern to the table
				}
				
				
			}
			
			//when you reach the end - print what you have in your prefix
			if (prefix.length() == 1)
			{
				//prints the code for anything in the ascii table from index 0-127
				writer.print((int)prefix.charAt(0));
			}
			else 
			{
				//prints the code for any pattern in the table that's been added; codes past 127
				/*
					hashmap update
				*/
				writer.print(table.get(prefix));
			}
			//save and close
			br.close();
			writer.close();

		}
		catch (IOException e) 
		{
			System.out.println("cannot read");
		}

	}
}