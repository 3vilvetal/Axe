package system.email;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;


/**
 * Read email messages from inbox
 * @author Vitalik
 */
public class ReadEmails
{
    String server = null, email = null, password = null; 
    
    public ReadEmails(String server, String email, String password) 
    {
    	this.server = server;
        this.email = email;
        this.password = password;
    }
     
    /**
     * Responsible for printing Data to Console
     * @param data
     */
    private void printData(String data) 
    {
       System.out.println(data);
    }
     
    /**
     * Process the email massage
     * @return
     */
    public String processMail() 
    {
       Session session = null;
       Store store = null;
       Folder folder = null;
       Message message = null;
       Message[] messages = null;
       Object messagecontentObject = null, messagecontentSecondObject = null;
       String sender = null, subject = null, contentType = null, body = "It is wrong email message, you need with ACTIVATION CODE...", secondContentType = null;
       Multipart multipart = null, secondMultipart = null;
       Part part = null, secondPart = null;
     
       try 
       {
          printData("--------------processing mails started-----------------");
          session = Session.getDefaultInstance(System.getProperties(), null);
     
          printData("getting the session for accessing email.");
          store = session.getStore("pop3");
          
          do
          {
          	    store.connect(server, email, password);
          	    //Get a handle on the default folder
          	    folder = store.getDefaultFolder();
          	    // Retrieve the "Inbox"
          	    folder = folder.getFolder("inbox");
          	    //Reading the Email Index in Read / Write Mode
                folder.open(Folder.READ_WRITE);
          	    //Retrieve the messages
                messages = folder.getMessages();
                    
                if (messages.length == 0)
                {
                	//Close the folder
                	folder.close(true);
                	//Close the message store
                	store.close();
                    Thread.sleep(5000);
                    System.out.println(messages.length + " waiting....");
                }
          } 
          while(messages.length == 0);
          
          // Loop over all of the messages
          for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) 
          {
          	   printData("--------------message # " + messageNumber +"-----------------");
               // Retrieve the next message to be read
          	   message = messages[messageNumber];
     
               if(message.getSubject().equals("Регистрация в Базовом блокноте на JOB.ukr.net") || message.getSubject().equals("Регистрация на сайте JOB.ukr.net"))
               {
//                  Retrieve the message content
               	    messagecontentObject = message.getContent();
      
               	    // Determine email type
               	    if (messagecontentObject instanceof Multipart) 
               	    {
               	    	printData("Found Email with Attachment");
               	    	sender = ((InternetAddress) message.getFrom()[0]).getPersonal();
      
               	    	// If the "personal" information has no entry, check the address for the sender information
               	    	printData("If the personal information has no entry, check the address for the sender information.");
               	    	
               	    	if (sender == null) 
               	    	{
               	    		sender = ((InternetAddress) message.getFrom()[0]).getAddress();
               	    		printData("sender in NULL. Printing Address:" + sender);
               	    	}
               	    	printData("Sender - " + sender);
      
               	    	// Get the subject information
               	    	subject = message.getSubject();
      
               	    	printData("subject=" + subject);
                    
               	    	// Retrieve the Multipart object from the message
               	    	multipart = (Multipart) message.getContent();
      
               	    	printData("Retrieve the Multipart object from the message");
      
               	    	// Loop over the parts of the email
               	    	for (int i = 0; i < multipart.getCount(); i++) 
               	    	{
               	    		// Retrieve the next part
               	    		part = multipart.getBodyPart(i);
      
               	    		// Get the content type
               	    		contentType = part.getContentType();
      
               	    		// Display the content type
               	    		printData("Content: " + contentType);
                         
               	    		// Read the message MULTIPART
               	    		messagecontentSecondObject = part.getContent();
               	    		// Determine email type
               	    		if (messagecontentSecondObject instanceof Multipart) 
               	    		{
               	    			secondMultipart = (Multipart) part.getContent();
                             
               	    			// Retrieve the next part
               	    			secondPart = secondMultipart.getBodyPart(0);
               	    			// Get the content type
               	    			secondContentType = secondPart.getContentType();
               	    			printData("Content type of multipart: " + secondContentType);
               	    			body = "";
               	    			if (secondContentType.startsWith("text/plain") || secondContentType.startsWith("text/html"))
               	    			{
               	    				InputStream is = part.getInputStream();
               	    				
               	    				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
               	    				String thisLine=reader.readLine();
               	    				
               	    				while (thisLine!=null)
               	    				{
               	    					body += thisLine;
               	    					thisLine=reader.readLine();
               	    				}
               	    			}
               	    			printData("Message body: " + body);
               	    		}
                         
               	    		if (contentType.startsWith("text/plain")) 
               	    		{
               	    			printData("reading content type text/plain  mail");
               	    		} 
               	    		else 
               	    		{
               	    			// Retrieve the file name
                                String fileName = part.getFileName();
                                printData("retrive the fileName="+ fileName);
               	    		}
               	    	}
               	    } 
               	    else 
               	    {
               	    	printData("Found Mail Without Attachment");
                        sender = ((InternetAddress) message.getFrom()[0]).getPersonal();
      
                        // If the "personal" information has no entry, check the address for the sender information
                        printData("If the personal information has no entry, check the address for the sender information.");
      
                        if (sender == null) 
                        {
                            sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                            printData("sender in NULL. Printing Address:" + sender);
                        }
                        // Get the subject information
                        subject = message.getSubject();
                        printData("subject=" + subject);
               	    }
                    //delete the message
               	    //message.setFlag(Flags.Flag.DELETED, true);
               }
          }
     
          // Close the folder
          folder.close(true);
     
          // Close the message store
          store.close();
      } 
      catch(AuthenticationFailedException e) 
      {
         printData("Not able to process the mail reading: AuthenticationFailedException");
         e.printStackTrace();
      } 
      catch(FolderClosedException e) 
      {
         printData("Not able to process the mail reading: FolderClosedException");
         e.printStackTrace();
      } 
      catch(FolderNotFoundException e) 
      {
         printData("Not able to process the mail reading: FolderNotFoundException");
         e.printStackTrace();
      }  
      catch(NoSuchProviderException e) 
      {
         printData("Not able to process the mail reading: NoSuchProviderException");
         e.printStackTrace();
      } 
      catch(ReadOnlyFolderException e) 
      {
         printData("Not able to process the mail reading: ReadOnlyFolderException");
         e.printStackTrace();
      } 
      catch(StoreClosedException e) 
      {
         printData("Not able to process the mail reading: StoreClosedException");
         e.printStackTrace();
      } 
      catch (Exception e) 
      {
         printData("Not able to process the mail reading: Exception");
         e.printStackTrace();
      }
      
      return body;
    }
}

