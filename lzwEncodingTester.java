import java.io.*;
import java.lang.*;
public class lzwEncodingTester
{
	public static void main(String[] args) throws IOException
	{
		long start = System.currentTimeMillis();
		lzwEncoding test = new lzwEncoding();
		test.encode("lzw.txt", "lzwEncoded.txt");
		long end = System.currentTimeMillis();
		System.out.println(end-start);

		System.out.println();
		System.out.println();
		
		start = System.currentTimeMillis();
		lzwDecode test1 = new lzwDecode();
		test1.decode("lzwEncoded.txt", "lzwNew.txt");
		end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}