
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
	ArrayList<String> table = new ArrayList<String>();

	//makes arrayList that stores LZW code
	ArrayList<Integer> code = new ArrayList<Integer>();

	String text;

	public Encoder () {

	}

	@SuppressWarnings("unlikely-arg-type")
	public void encode (String fileName) throws IOException
	{
		try {

			//make buffered reader and file reader
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			//make printwriter so we can print as we encode
			PrintWriter writer = new PrintWriter(new FileWriter(fileName  + "encoded"));


			for (int i=0; i<94; i++)
			{
				table.add(""+(char)(i+33)); //inputs values into table
			}

			String read = ""; // String that you take in


			while (br.ready()) //read in file to code and add to input
			{

				char c = (char) br.read(); //reads in one char
				read = read + c; //adds the char onto what has been read so far


				if (table.indexOf(read) >= 0 || read.length()==1) {
					//		read = 
				}
				if ((int)read.charAt(0) <= 255) { //checks if it is already in the ascii table
					int temp = (int)read.charAt(0);
					code.add(temp); //adds the index to the list
					writer.print((int)read.charAt(0));
				}

				else//if the pattern is not in table, it adds it to the table
				{
					table.add(read);
					code.add (table.indexOf(read.substring(0,read.length()-2))); //adds value of everything but last letter to code
					writer.print(256+table.indexOf(c));
					read = ""+ read.substring(read.length()-1); //resets with only last char of former sequence

				}

			}

			//save
			br.close();
			writer.close();

		}
		catch (IOException e) {
			System.out.println("cannot read");
		}

	}
}

