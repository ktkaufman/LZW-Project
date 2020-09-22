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
	HashMap<String, Character> table = new HashMap<String, Character>();
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
		try 
		{

			//make buffered reader and file reader
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			//make printwriter so we can print as we encode
			PrintWriter writer = new PrintWriter(new FileWriter(fileName  + "encoded"));


			/*
				EDIT 3
				previously started adding at ascii character #32,' ', however it's totally possible to get a character prior to that, ex: #10,'\n'
				edited to hold the entire unextended ascii table initially
				also, hashmap update
			*/
			for(int a=0; a<128; a++)
				table.put((char)a+"", (char)a);
			/*
				EDIT 4
				arraylist has a built in # that can be used for LZW encoding (element position), but we must put down a corresponding # for the hashmap
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
					EDIT 5
					hashmap update
				*/
				if (table.containsKey(pattern) || pattern.length()==1) 
				{ //checks if the table alrady contains this pattern/if it's already part of the ascii table
					prefix = pattern; //sets prefix to the pattern; now when pattern = prefix + readchar loops it'll include what's alrady been read
					//code.add((int)readchar); //adds the char's index to the table
				}

				else {
					if (prefix.length()==1) { //checks if it is in ascii table as a single letter
						//code.add((int)pattern.charAt(0)); //adds the index to the list of codes
						/*
							EDIT 6
							changed the hashmap to hold Characters instead of Integers
							no need for commas anymore bc each encoding block is 1 char
						*/
						writer.print(pattern.charAt(0));
						//writer.print((int)pattern.charAt(0) + ","); //prints the code of this pattern
					}

					else//if the pattern is not in table, it adds it to the table. also print this pattern
					{
						
						/*
							EDIT 7
							hashmap update
							no need for commas anymore bc each encoding block is 1 char
							removed code
						*/
						//code.add (33+table.indexOf(prefix)); //adds value of everything but last letter to code
						writer.print(table.get(prefix));
						//writer.print(table.get(prefix) +","); //prints the code of this pattern
					}
					
					prefix = "" + readchar; // resets with only the last char of the sequence
					/*
						EDIT 8
						hashmap update
						changed the hashmap to hold Characters instead of Integers, so I capped it at 256 - extended ascii size
						update: changed it to 55296
						max should be 65536 bc in utf - 8 1 char is maxxed at 65536, but bc of some weird utf rule, we're capped at 55296
					*/
					if(place<55296)
						table.put(pattern, (char)place); //adds this pattern to the table
					/*
						bug fix from a prev push
						forgot to add to place - don't want to link 2 seperate keys to the same value 
					*/
					place++;
				}
				
				
			}
			
			//when you reach the end - print what you have in your prefix
			if (prefix.length() == 1)
			{
				//prints the code for anything in the ascii table from index 0-127
				/*
					EDIT 9
					converted hashmap value from Integer to Character
				*/
				writer.print(prefix.charAt(0));
				//writer.print((int)prefix.charAt(0));
			}
			else 
			{
				//prints the code for any pattern in the table that's been added; codes past 127
				/*
					EDIT 10
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