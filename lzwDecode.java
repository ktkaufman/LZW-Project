import java.util.*;
import java.io.*;

public class lzwDecode
{
	public static HashMap<Character,String> init(HashMap<Character,String> table)
	{
		// fill the table with the standard ascii 1-128
		for(int a = 0; a < 128; a++)
		{
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
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<Character, String> table = new HashMap<Character, String>();
		// holds the encoded message
		StringBuilder decoding = new StringBuilder("");

		// fill the table with the standard ascii 1-128
		init(table);
		int num = 128;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(input));
			int current = br.read();

			char prev='a';
			String decodeBlock = "";
			String holderChar = "";
			if(table.containsKey((char)current))
			{
				prev = ((char)current);
				//System.out.println("print: "+prev);
				decoding.append(table.get((char)current));
			}

			while(current != -1)
			{
				current = br.read();
				if(current == -1)
					break;
				//System.out.println("current: "+current);
				// current character isn't found in the table
				// 
				if(!table.containsKey((char)current))
				{
					//System.out.println(prev+" "+holderChar);
					decodeBlock = table.get(prev) + holderChar;
				}
				if(table.containsKey((char)current))
					decodeBlock = table.get((char)current);

				//System.out.println("print: "+decodeBlock);
				decoding.append(decodeBlock);

				holderChar = decodeBlock.charAt(0)+"";
				//System.out.println("add: "+num+" "+(prev+holderChar));
				if(num < 256)
					table.put((char)num, table.get(prev)+holderChar);

				// increase the next available ascii/table slot
				num++;
				prev = (char)current;
				//System.out.println("prev: "+current);
				
			}
			//System.out.println(table.size());
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
}