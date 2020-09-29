import java.util.*;
import java.io.*;

public class lzwDecode
{
	private static HashMap<Character, String> table = new HashMap<Character, String>();
	private static LinkedList linkedList = new LinkedList();
	public static HashMap<Character,String> init(HashMap<Character,String> table)
	{
		// fill the table with the standard ascii 1-128
		for(int a = 0; a < 128; a++)
		{
			linkedList.add(""+(char)a);
			char current = (char)(a);
			// (corresponding ascii (char) + pattern (string))
			// reversed from prev bc i'm pretty sure containsValue is O(N) while containsKey is O(1)
			// also bc get is way easier to use than whatever we would need to use to find the matching key
			table.put(current,current+"");
		}
		return table;
	}

	public static void decode (String input, String output) 
	{
		
		// holds the decoded message
		StringBuilder decoding = new StringBuilder("");

		// fill the table with the standard ascii 1-128
		init(table);
		// the first available character
		int num = 128; 
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(input));
			int current = br.read();

			// java doesn't allow for empty chars, so we just treat 'a' as '' bc it gets overwritten first
			char prev='a';
			String decodeBlock = "";
			// the first char of the previous block, used for the special case when LZW doesn't work + adding to the table
			String prevChar = "";

			// check if the first char of the encoding is in the table
			// should always be there, just being safe (we have big problems if it isn't in the table)
			if(table.containsKey((char)current))
			{
				prev = ((char)current);
				// add to the decoding 
				decoding.append(table.get((char)current));
			}

			while(current != -1)
			{
				current = br.read();
				// until EOF
				if(current == -1)
					break;

				// current character isn't found in the table, weird lzw case
				if(!table.containsKey((char)current))
					// add the first char of the prev to the end of the decoded prev to find the missing value
					decodeBlock = table.get(prev) + prevChar;

				// current character is in the table!
				if(table.containsKey((char)current))
					//simply decode the current char
					decodeBlock = table.get((char)current);

				// add whatever was decoded to decoding
				decoding.append(decodeBlock);

				// save first char of the decoded block
				prevChar = decodeBlock.charAt(0)+"";
				
				// max 256 bc the extended ascii table ends at 255, so we can't represent anything past 255
				// update: changed it to 55296
				// max should be 65536 bc in utf - 8 1 char is maxxed at 65536, but bc of some weird utf rule, we're capped at 55296
				// add to the table
				if(num < 55296)
				{
					addCode(table.get(prev)+prevChar);
					table.put((char)num, table.get(prev)+prevChar);

				// increase the next available ascii/table slot
				num++;
				}
				else
				{
					table.remove(linkedList.get(0));
					linkedList.remove(0);
					addCode(table.get(prev)+prevChar);
					table.put((char)num, table.get(prev)+prevChar);
					num++;
				}
				// save the previous
				prev = (char)current;
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			// write out the decoding
			bw.write(decoding.toString());
			//System.out.println(decoding.toString());
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		
	}
	public static void addCode(String code)//checks the list to remove duplicates and adds the most recent code to the back
	{
		if (table.containsValue(code));//checks if their are duplicates
		{
			linkedList.remove(code);//removes duplicates
		}
		linkedList.add(code);
	}
}