package system.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile 
{
	static int BUFFER_SIZE = 1024;
	
	/**
	 * Copies files
	 * @param from
	 * @param to
	 */
	public static void copy(String from, String to)
	{
		try
		{
			InputStream in = new FileInputStream(from);
		    OutputStream out = new FileOutputStream(to);
	
		    // Transfer bytes from in to out
		    byte[] buffer = new byte[BUFFER_SIZE];
		    int length;
		    
		    while ((length = in.read(buffer)) > 0) 
		    {
		        out.write(buffer, 0, length);
		    }
		    in.close();
		    out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
