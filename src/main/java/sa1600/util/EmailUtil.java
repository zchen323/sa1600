package sa1600.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	public static void sendSimpleMail(){
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String msgBody = "....";
		
		try {
			 Message msg = new MimeMessage(session);
		      msg.setFrom(new InternetAddress("zchen323@gmail.com", "Example.com Admin"));
		      msg.addRecipient(Message.RecipientType.TO,
		                       new InternetAddress("zchen323@yahoo.com", "Mr. User"));
		      msg.setSubject("Your Example.com account has been activated");
		      msg.setText(msgBody);

		      // [START multipart_example]
//		      String htmlBody = "";          // ...
//		      byte[] attachmentData = null;  // ...
//		      Multipart mp = new MimeMultipart();
//
//		      MimeBodyPart htmlPart = new MimeBodyPart();
//		      htmlPart.setContent(htmlBody, "text/html");
//		      mp.addBodyPart(htmlPart);
//
//		      MimeBodyPart attachment = new MimeBodyPart();
//		      InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
//		      attachment.setFileName("manual.pdf");
//		      attachment.setContent(attachmentDataStream, "application/pdf");
//		      mp.addBodyPart(attachment);
//
//		      msg.setContent(mp);
		      
		      Transport.send(msg);
		      
		      System.out.println("==== send messge: " + msg);
		      
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
