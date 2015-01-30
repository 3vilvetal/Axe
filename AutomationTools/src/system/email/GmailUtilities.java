package system.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

public class GmailUtilities 
{
	/**
	 * Method return text from all message in current user (gmail)
	 */
	public String reciveEmail(String username, String password)
	{
		try
		{	
			String host = "pop.gmail.com";
			Session session = Session.getInstance(new Properties(), null);
			Store store = session.getStore("pop3s");
			store.connect(host, username, password);
			Folder folder = store.getFolder("inbox");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();
			
			for(int i = 0, n = message.length; i < n; i++)
			{
				String ans = "y";
				if ("y".equals(ans)) 
				{
					Object content = message[i].getContent();
					if (content instanceof Multipart) 
					{
						return handleMultipart((Multipart)content);
					} 
					else 
					{
						return handlePart(message[i]);
					}
				}
				else 
					if ("q".equals(ans)) 
					{
						break;
					}
			}
			folder.close(false);
			store.close();
		}
		catch (Exception e) 
		{
            e.printStackTrace();
		}
		
		return "";
	}
	
	public String handleMultipart(Multipart multipart) throws MessagingException, IOException
	{
		String text = "";
		for (int i=0, n=multipart.getCount(); i < n; i++)
			text += handlePart(multipart.getBodyPart(i));
		return text;
	}
	
	public String handlePart(Part part)  throws MessagingException, IOException 
	{
		String dposition = part.getDisposition();
		String cType = part.getContentType();
		String text = "";
		if (dposition == null) 
		{ 
			text += "Null: "  + cType;
			if ((cType.length() >= 10) && (cType.toLowerCase().substring(0, 10).equals("text/plain"))) {
				InputStream is = part.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String thisLine=reader.readLine();
				String body = "";
				while (thisLine!=null)
				{
					body += thisLine;
					thisLine=reader.readLine();
				}
				text += body;
			}
			else 
			{
				text += "Other body: " + cType;
				InputStream is = part.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String thisLine=reader.readLine();
				String body = "";
				while (thisLine!=null)
				{
					body += thisLine;
					thisLine=reader.readLine();
				}
				text += body;
			}
		}
		return text; 
	}
	public void deleteAllMail(String username,String password)
	{
		try
		{
			String host = "pop.gmail.com";
			Session session = Session.getInstance(new Properties(), null);
			Store store = session.getStore("pop3s");
			store.connect(host, username, password);
			Folder folder = store.getFolder("Inbox");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();
			for(int i=0, n=message.length; i< n; i++)
			{
				Message messageDelete = message[i];
				messageDelete.setFlag(Flags.Flag.DELETED, true);
			}
			// Close the folder
            folder.close(true);
            // Close the message store
            store.close();
		} 
		catch (Exception e) {
            e.printStackTrace();
		}		
	}
}
