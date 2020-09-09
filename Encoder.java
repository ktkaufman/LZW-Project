import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 *	1. read the text until we find. pattern that is not in our table
	2. put that pattern into the table, output the code for everything BUT the last letter of that new pattern
	3. reset to only include what we have not output a code for

 */


public class Encoder

{

	//make table that has the list of string and their value
	Hashtable<String, Integer> table = new Hashtable<String, Integer>();

	
	//makes arrayList that stores LZW code
	ArrayList<Integer> code = new ArrayList<Integer>();


	public Encoder (String inputFileName)
	{
		//make file reader
		BufferedReader br = new BufferedReader(new FileReader(inputFileName));
	
		for (int i=0; i<94; i++)
		{
			table.put(""+ (char)(i+33), i); //inputs values into table
		}

		String read = ""; // String that you take in

		
		while (br.ready()) //read in file to code and add to input
		{

				read = read + (char) br.read(); //reads in one char
				
				if (!table.containsKey(read)) //if read is not in table, it adds it to the table
				{
					table.put(read, table.size());
					code.add (table.keys(read.substring(0,read.length()-2))); //adds value of everything but last letter to code
					read = ""+ read.substring(read.length()-1); //resets with only last char of former sequence
					
				}
			
		}
		
		//save
		br.close();

	}
	
	public String output () {
		StringBuffer sb = new StringBuffer ("");
		for (int key = 0; key < table.size(); key++) {
			sb.append(table.get(key));
		}
		return sb.toString();

}
}
