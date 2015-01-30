package system.email;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendEmails
{    
	String MAIL_SMTP_HOST = "smtp.ukr.net", //"gmail.com";
		   MAIL_SMTP_PORT = "25";
	
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
        
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try 
        {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        }
        catch (AddressException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
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
    public void sendFiles(String from, String to, String subject, String text, String path)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", MAIL_SMTP_PORT);
        
        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);
        
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try 
        {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } 
        catch (AddressException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
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
        catch (MessagingException e) 
        {
            e.printStackTrace();
		} 
        catch (UnsupportedEncodingException e) 
        {
			e.printStackTrace();
		} 
    }
    
    public void sendText(String from, String to, String subject, String text)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", MAIL_SMTP_PORT);
        
        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);
        
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try 
        {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } 
        catch (AddressException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try 
        {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(text);            
            Transport.send(simpleMessage);          
        } 
        catch (MessagingException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
		} 
    }
}



