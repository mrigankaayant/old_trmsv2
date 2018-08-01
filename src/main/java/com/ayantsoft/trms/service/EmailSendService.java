package com.ayantsoft.trms.service;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSendService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8110469505293479160L;

	public void sendMail(String host, String port, final String userName, final String password, String toAddress, String subject, String emailMessage,List<String> filePaths,List<String> ccList,List<String> bccList)
	{

		new Thread( new Runnable() {
			@Override
			public void run() {
				try{
					Properties properties = new Properties();
					properties.put("mail.smtp.host", host);
					properties.put("mail.smtp.port", port);
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.starttls.enable", "true");


					Authenticator auth = new Authenticator() {
						public PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName, password);
						}
					};

					Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator()
					{  
						protected PasswordAuthentication getPasswordAuthentication() {  
							return new PasswordAuthentication(userName,password); 
						}  
					});


					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(userName));

					InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
					msg.setRecipients(Message.RecipientType.TO, toAddresses);
					if(ccList != null && ccList.size()>0){
						for(String cc:ccList){
							msg.addRecipient(RecipientType.CC, new InternetAddress(cc));
						}
					}
					if(bccList != null && bccList.size()>0){
						for(String bcc:bccList){
							msg.addRecipient(RecipientType.BCC, new InternetAddress(bcc));
						}

					}
					msg.setSubject(subject);
					msg.setSentDate(new Date());
					msg.setText(emailMessage);  

					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(emailMessage, "text/html");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);

					if (filePaths != null && filePaths.size() > 0) {
						for (String filePath : filePaths) {
							MimeBodyPart attachPart = new MimeBodyPart();
							try {
								attachPart.attachFile(filePath);
							}catch (IOException ex){
								ex.printStackTrace();
							}

							multipart.addBodyPart(attachPart);
						}
					}

					msg.setContent(multipart);

					Transport.send(msg);

				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();

	}

}