package system.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile 
{
	/**
	 * Add new string to file
	 * @param fileName
	 * @param string
	 * @throws IOException
	 */
	public static void addString(String fileName, String string) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
		bw.write(string);
		bw.newLine();
		bw.close();	
	}
	
	/**
	 * Rewrites all file
	 * @param fileName
	 * @param string
	 * @throws IOException
	 */
	public static void reWrite(String fileName, String string) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
		bw.write(string);
		bw.close();	
	}
}
