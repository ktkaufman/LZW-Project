import java.util.*;
import java.io.*;

public class LZWTester
{
	Encoder jake = new Encoder();
	jake.encode("inputTest.txt");
	lzwDecode(String,String) alsoJake=new lzwDecode("testencoded","testdecoded");
}