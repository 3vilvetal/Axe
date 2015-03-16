package system.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Email {
		String MAIL_SMTP_HOST = "smtp.ukr.net",
			   MAIL_SMTP_PORT = "25";
		
		/**
		 * Sample
		 * @param args
		 */
		public static void main (String [] args) {
	    	Email email = new Email();
	    	System.out.println(email.readEmail("pop3.ukr.net", "test@ukr.net", "*********"));
		}
	
		/**
		 * Sends file in email message
		 * @param from
		 * @param to
		 * @param subject
		 * @param text
		 * @param file
		 */
		public void sendFile(String from, String to, String subject, String text, File file)
		{
			Properties props = new Properties();
	        props.put("mail.smtp.host", MAIL_SMTP_HOST);
	        props.put("mail.smtp.port", MAIL_SMTP_PORT);
	        
	        Session mailSession = Session.getDefaultInstance(props);
	        Message simpleMessage = new MimeMessage(mailSession);
	        
	        try 
	        {
	        	InternetAddress fromAddress = new InternetAddress(from);
	        	InternetAddress toAddress = new InternetAddress(to);
	            
	            simpleMessage.setFrom(fromAddress);
	            simpleMessage.setRecipient(RecipientType.TO, toAddress);
	            simpleMessage.setSubject(subject);
	            simpleMessage.setText(text);
	            
	            Multipart mp = new MimeMultipart();            
	            MimeBodyPart mbpp = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(file);
	            
	            mbpp = new MimeBodyPart();
	            mbpp.setDataHandler(new DataHandler(fds));
	            mbpp.setFileName(MimeUtility.encodeText(file.getName().toString(), "UTF-8", null));
	            mbpp.setHeader("Content-Type", "text/html; charset=UTF-8");
	            mp.addBodyPart(mbpp);
	   
	            simpleMessage.setContent(mp,"text/html; charset=UTF-8");
	            Transport.send(simpleMessage);          
	        }
	        catch (AddressException e) 
	        {
	            e.printStackTrace();
	        }
	        catch (MessagingException e) 
	        {
	            e.printStackTrace();
			} 
	        catch (UnsupportedEncodingException e) 
	        {
				e.printStackTrace();
			} 
		}
		
		/**
		 * Sends the list of files in one folder
		 * @param from
		 * @param to
		 * @param subject
		 * @param text
		 * @param path
		 */
	    public void sendFolder(String from, String to, String subject, String text, String path)
	    {
	        Properties props = new Properties();
	        props.put("mail.smtp.host", MAIL_SMTP_HOST);
	        props.put("mail.smtp.port", MAIL_SMTP_PORT);
	        
	        Session mailSession = Session.getDefaultInstance(props);
	        Message simpleMessage = new MimeMessage(mailSession);
	        
	        try 
	        {
	        	InternetAddress fromAddress = new InternetAddress(from);
	        	InternetAddress toAddress = new InternetAddress(to);
	            
	            simpleMessage.setFrom(fromAddress);
	            simpleMessage.setRecipient(RecipientType.TO, toAddress);
	            simpleMessage.setSubject(subject);
	            simpleMessage.setText(text);
	            
	            //check folder exist
	            new File(path).mkdir();
	            
	            File file = new File(path);
	            File[] files = file.listFiles();
	            
	            Multipart mp = new MimeMultipart();            
	            
	            for (int i = 0; i < files.length; i++)
	            {
	                    MimeBodyPart [] mbpp = new MimeBodyPart [files.length];
	                    
	                    for (int j = 0; j < files.length; j++)
	                    {
	                            FileDataSource fds = new FileDataSource(files[j]);
	                            mbpp[j] = new MimeBodyPart();
	                            mbpp[j].setDataHandler(new DataHandler(fds));
	                            mbpp[j].setFileName(MimeUtility.encodeText(files[j].getName().toString(), "UTF-8", null));
	                            mbpp[j].setHeader("Content-Type", "text/html; charset=UTF-8");
	                            mp.addBodyPart(mbpp[j]);
	                    }
	            }   
	            simpleMessage.setContent(mp,"text/html; charset=UTF-8");
	            Transport.send(simpleMessage); 
	        } 
	        catch (AddressException e) 
	        {
	            e.printStackTrace();
	        }
	        catch (MessagingException e) 
	        {
	            e.printStackTrace();
			} 
	        catch (UnsupportedEncodingException e) 
	        {
				e.printStackTrace();
			} 
	    }
	    
	    /**
	     * Send text message
	     * @param from
	     * @param to
	     * @param subject
	     * @param text
	     */
	    public void sendText(String from, String to, String subject, String text)
	    {
	        Properties props = new Properties();
	        props.put("mail.smtp.host", MAIL_SMTP_HOST);
	        props.put("mail.smtp.port", MAIL_SMTP_PORT);
	        
	        Session mailSession = Session.getDefaultInstance(props);
	        Message simpleMessage = new MimeMessage(mailSession);
	        
	        try 
	        {
	        	InternetAddress fromAddress = new InternetAddress(from);
	        	InternetAddress toAddress = new InternetAddress(to);
	            
	            simpleMessage.setFrom(fromAddress);
	            simpleMessage.setRecipient(RecipientType.TO, toAddress);
	            simpleMessage.setSubject(subject);
	            simpleMessage.setText(text);    
	            
	            Transport.send(simpleMessage);   
	        } 
	        catch (AddressException e) 
	        {
	            e.printStackTrace();
	        }
	        catch (MessagingException e) 
	        {
	            e.printStackTrace();
			} 
	    }
	    
	    /**
	     * Remove all messages from Inbox folder
	     * @param server
	     * @param email
	     * @param password
	     */
	    public void deleteAll(String server, String email, String password)
	    {
	        Session session = Session.getDefaultInstance(System.getProperties(), null);
	        try 
	        {
	        	Store store = session.getStore("pop3");
	            store.connect(server, email, password);
	            
	            //Get a handle on the default folder
	            Folder folder = store.getDefaultFolder();
	            
	            // Retrieve the "Inbox"
	            folder = folder.getFolder("inbox");
	            
	            //Reading the Email Index in Read / Write Mode
	            folder.open(Folder.READ_WRITE);
	            
	            //Retrieve the messages
	            Message[] messages = folder.getMessages();
	            Message message = null;
	            
	            for (int i = 0; i < messages.length; i++)
	            {
	                message = messages[i];
	            	message.setFlag(Flags.Flag.DELETED, true);
	            }
	            
	            // Close the folder
	            folder.close(true);
	            // Close the message store
	            store.close();
	        } 
	        catch (NoSuchProviderException e) 
	        {
	        	e.printStackTrace();
	        } 
	        catch (MessagingException e) 
	        {
				e.printStackTrace();
			}
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
	     * Process the email massages in the Inbox folder
	     * @return
	     */
	    public String readEmail(String server, String email, String password) 
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
	          	   printData("--------------message # " + messageNumber + "-----------------");
	               // Retrieve the next message to be read
	          	   message = messages[messageNumber];
	     
	          	   //Retrieve the message content
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
