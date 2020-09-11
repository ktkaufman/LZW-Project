
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

<<<<<<< HEAD
	//make table that has the list of string and their value
	ArrayList<String> table = new ArrayList<String>();


=======
	//make a list of strings and their values
	ArrayList<String> table = new ArrayList<String>();
>>>>>>> e8caa5df6167763f86789695557ad87d06e0e199

	//makes arrayList that stores LZW code
	ArrayList<Integer> code = new ArrayList<Integer>();

	String text;
	
	public Encoder () {
		
=======

	public Encoder (String fileName) {
	public Encoder () {
>>>>>>> e8caa5df6167763f86789695557ad87d06e0e199
	}

	public void encodeFile (String inputFileName)
	{
		//make file reader
		BufferedReader br = new BufferedReader(new FileReader(inputFileName));

		for (int i=0; i<94; i++)
		{
			table.add(""+(char)(i+33)); //inputs values into table
		}

		String read = ""; // String that you take in


		while (br.ready()) //read in file to code and add to input
		{

			read = read + (char) br.read(); //reads in one char

			if (!table.contains(read)) //if read is not in table, it adds it to the table
			{
				table.add(read);
<<<<<<< HEAD
				code.add (table.indexOf(read.substring(0,read.length()-2))); //adds value of everything but last letter to code
=======
				code.add (table.getKey(read.substring(0,read.length()-2))); //adds value of everything but last letter to code
>>>>>>> e8caa5df6167763f86789695557ad87d06e0e199
				read = ""+ read.substring(read.length()-1); //resets with only last char of former sequence

			}

		}

		//save
		br.close();

	}


<<<<<<< HEAD
	StringBuffer sb = new StringBuffer ("");
	
	public void output () 
	{
		
=======
	public void generateText(int chainorder, String outputFileName, int numChars) throws IOException
	{
		//makes a stringbuffer to loop through table
		StringBuffer sb = new StringBuffer ("");
>>>>>>> e8caa5df6167763f86789695557ad87d06e0e199
		for (int key = 0; key < table.size(); key++) {
			sb.append(table.indexOf(key));
		}
		PrintWriter writer = new PrintWriter(new FileWriter(outputFileName));
		writer.print(sb);

		writer.close();
	}


}
