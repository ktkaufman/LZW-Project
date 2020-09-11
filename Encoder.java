
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

	private String s = ""; 
	private String pattern = "";
	private char c = 0; //this will represent the last char when checking a substring

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

				c = (char) br.read(); //reads in one char
				pattern = s + c; //adds the char onto what has been read so far


				//if (table.indexOf(pattern) >= 0 || pattern.length()==1) {
					//s = pattern;
					//code.add((int)c);
				}

				else {
					if ((int)pattern.charAt(0) <= 127) { //checks if it is already in the ascii table
						code.add((int)pattern.charAt(0)); //adds the index to the list of codes
						writer.print((int)pattern.charAt(0)); //prints the code of this pattern
					}

					else//if the pattern is not in table, it adds it to the table. also print this pattern
					{
						code.add (table.indexOf(s)); //adds value of everything but last letter to code
						writer.print(128+table.indexOf(s)); //prints the code of this pattern
					}
					table.add(pattern);
					s = "" + c; // resets with only the last char of the sequence
				}
				
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

