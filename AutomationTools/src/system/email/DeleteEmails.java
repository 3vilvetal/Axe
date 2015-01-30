package system.email;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


/**
 * Delete all mails from inbox
 * @author Vitalik
 */
public class DeleteEmails
{
    String server = null, email = null, password = null; 
    Session session = null;
    Store store = null;
    Folder folder = null;
    Message[] messages = null;
    Message message = null;
    
    public DeleteEmails(String server, String email, String password) 
    {
        this.server = server;
        this.email = email;
        this.password = password;
    }
    
    public void deleteAll()
    {
        session = Session.getDefaultInstance(System.getProperties(), null);
        try 
        {
			store = session.getStore("pop3");
            store.connect(server, email, password);
            //Get a handle on the default folder
            folder = store.getDefaultFolder();
            // Retrieve the "Inbox"
            folder = folder.getFolder("inbox");
            //Reading the Email Index in Read / Write Mode
            folder.open(Folder.READ_WRITE);
            //Retrieve the messages
            messages = folder.getMessages();
            
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
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        } 
        catch (MessagingException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

