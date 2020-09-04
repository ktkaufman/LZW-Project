import java.util.*;
import java.io.BufferedReader;

/**
 *	1. read the text until we find. pattern that is not in our table
	2. put that pattern into the table, output the code for everything BUT the last letter of that new pattern
	3. reset to only include what we have not output a code for

 */
public class Encoder (String inputFileName){
	
//make table that has the list of string and their value
	Hashtable<String, int> table = new Hashtable<String, int>();
	
	
	//make file reader
	BufferedReader br = new BufferedReader(new FileReader(file));

	//make table and input a-z
	
	//make array of strings (input), each character has an index
	
	
	
	public void encodeFile ()
	{
		//read in file to code and add to input
		while (br.ready) 
		{
			br.read();
		}
		//save
		br.close();
	}
	

	
	

	
}
