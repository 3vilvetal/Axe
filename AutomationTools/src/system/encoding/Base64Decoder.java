package system.encoding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;

public class Base64Decoder 
{
	public static void main(String args[])
	{
		System.out.println(decodeFileBase64("Base64.txt"));
	}
	
	/**
	 * Decodes string from Base64
	 * @param string
	 * @return
	 */
	public static String decodeBase64(String string)
	{
		String decodedString = null;
		byte[] decoded = Base64.decodeBase64(string.getBytes());
		try 
		{
			decodedString = new String(decoded, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return decodedString;
	}
	
	/**
	 * Decodes file with base64 text
	 * @param fileName
	 * @return
	 */
	
	public static String decodeFileBase64(String fileName)
	{
		String string = "";
		File file = new File (fileName);

		BufferedReader input;
		String data = "", temp = ""; 
	        
		try 
		{
			input = new BufferedReader(new FileReader(file));
			while ((temp = input.readLine())!= null)
			{
				data += temp;
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
		
		data = data.replace("=?utf-8?B?", " ").replace("=?=", "").replace("  ", " ");
		
		StringTokenizer st = new StringTokenizer(data);
		data = "";
		while (st.hasMoreTokens()) 
		{
	      	   string += decodeBase64(st.nextToken());
		}
		
		return string;
	}
}

