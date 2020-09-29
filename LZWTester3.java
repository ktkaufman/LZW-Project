import java.util.*;
import java.io.*;

public class LZWTester3 {
	public static void main (String [] args) throws IOException
	{
		Encoder jake = new Encoder();
		jake.encode("test");
		lzwDecode alsoJake = new lzwDecode();
		alsoJake.decode("test","testdecoded");
	}
}