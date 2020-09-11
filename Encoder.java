
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 *	1. read the text until we find. pattern that is not in our table
	2. put that pattern into the table, output the code for everything BUT the last letter of that new pattern
	3. reset to only include what we have not output a code for

 */


public class Encoder

{

	//make table that has the list of string and their value
	private ArrayList<String> table = new ArrayList<String>();

	//makes arrayList that stores LZW code
	private ArrayList<Integer> code = new ArrayList<Integer>();

	private String prefix = ""; 
	private String pattern = "";
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


			for (int i=0; i<95; i++)
			{
				table.add(""+(char)(i+32)); //inputs values into table
			}


			while (br.ready()) //read in file to code and add to input
			{

				readchar = (char) br.read(); //reads in one char
				pattern = prefix + readchar; //adds the char onto what has been read so far


				if (table.contains(pattern) || pattern.length()==1) {
					prefix = pattern;
					code.add((int)readchar);
				}

				else {
					if (prefix.length()==1) { //checks if it is in ascii table as a single letter
						code.add((int)pattern.charAt(0)); //adds the index to the list of codes
						writer.print((int)pattern.charAt(0) + ","); //prints the code of this pattern
					}

					else//if the pattern is not in table, it adds it to the table. also print this pattern
					{
						code.add (33+table.indexOf(prefix)); //adds value of everything but last letter to code
						writer.print(33+table.indexOf(prefix) +","); //prints the code of this pattern
					}
					
					prefix = "" + readchar; // resets with only the last char of the sequence
					table.add(pattern);
				}
				
				
			}
			//when you reach the end
			if (prefix.length() == 1)
			{
				writer.print((int)prefix.charAt(0) );
			}
			else
			{
				writer.print(128+table.indexOf(prefix));
			}
			//save
			br.close();
			writer.close();

		}
		catch (IOException e) 
		{
			System.out.println("cannot read");
		}

	}
}
