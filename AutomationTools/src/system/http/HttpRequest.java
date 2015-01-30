package system.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class HttpRequest 
{
	
	String USER_AGENT = "Mozilla 5.0 (Windows; U; " + "Windows NT 5.1; en-US; rv:1.8.0.11) ";
	
	/**
	 * Gets HTML with set Cookies -- used for pages that required login
	 * @param address
	 * @return
	 */
	public Vector<String> getResponceCookie(String address, String cookies) {
		
		String text;
		Vector<String> htmlVector = new Vector<String>(); 
		
		try {
			cookies = "sessionid=" + cookies;
			
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//add request header
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Cookie", cookies);
			conn.connect();
			
			//Read HTML output
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			
			while ((text = reader.readLine()) != null) {
				htmlVector.add(text);
			}
			conn.disconnect();
					
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Some HTTP Exeption...");
		}
		return htmlVector;
	}
	
	/**
	 * This function returns the String line of HTML text
	 * @param address
	 * @return
	 */
	public String getResponce(String address)
	{
		String text, line = null;
		
		try
		{
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.connect();
	        
			//Read HTML output
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			line = "";
			
			while ((text = reader.readLine()) != null) 
			{
				line += text;
			}
			conn.disconnect();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Some HTTP Exeption...");
		}
		return line;
	}
	
	/**
	 * This function returns the Array of HTML text lines 
	 * @param address
	 * @return
	 */
	public String [] getArrayResponce(String address)
	{
		ArrayList<String> arrayList = new ArrayList<String>(); 
		String [] array = null;
		String text;
		int N;
		
		try
		{
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestMethod("GET");
	
			conn.connect();
			
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			while ((text = reader.readLine()) != null) 
			{
				arrayList.add(text);
			}
			conn.disconnect();	
			
			N = arrayList.size();
			array = new String [N];
			
			for (int i = 0; i < N; i++)
			{
				array [i] = arrayList.get(i);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Some HTTP Exeption...");
		}
		return array;
	}
	
	
	/**
	 * Returns byte response
	 * @param address
	 * @return
	 */
	public byte [] getByteResponse(String address)
	{
		InputStream in = null;
		HttpURLConnection conn = null;
		byte[] bytes = null;
		
		try
		{
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", USER_AGENT);
	
			conn.connect();
			in = conn.getInputStream();
			
			// Create the byte array to hold the data
	        bytes = new byte[(int)10000];
	    
	        // Read in the bytes
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length-offset)) >= 0) 
	        {
	            offset += numRead;
	        }
			
			conn.disconnect();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Some HTTP Exeption...");
		}
		return bytes;
	}
}

