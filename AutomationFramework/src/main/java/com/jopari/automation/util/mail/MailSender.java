package com.jopari.automation.util.mail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.selenium.logging.ErrorEvent;
import com.jopari.automation.selenium.logging.InfoEvent;
import com.jopari.automation.selenium.logging.TestLogger;
import com.jopari.automation.util.exception.ExceptionUtil;

/**
 * @author savita.choudhary
 * 
 */
public class MailSender implements Runnable
{

	private String smtpServer;
	private String smtpAuthUser;
	private String smtpAuthPwd;
	private String emailFrom;
	private String[] emailTo;
	private String emailSubject;
	private String emailBody;
	private List<String> listOFFilesToAttach;
	private String defaultEmailFrom = Constants.DEFAULT_EMAIL_FROM;

	/**
	 * Constructor to set values for all the parameters
	 * 
	 * @param smtp_server
	 *            name of SMTP server
	 * @param authUser
	 *            userName for authenticating mail server before sending mail
	 * @param authPwd
	 *            password for authentication mail server before sending mail
	 * @param emailFrom
	 *            email address from which mail will be send
	 * @param emailTo
	 *            email address to which mail will be send
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 * @param listOfFiles
	 *            list of files to attach in mail
	 */
	public MailSender(String smtp_server, String authUser, String authPwd, String emailFrom, String emailTo[], String emailSubject,
			String emailBody, List<String> listOfFiles) {
		this.smtpServer = smtp_server;
		this.smtpAuthUser = authUser;
		this.smtpAuthPwd = authPwd;
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		this.listOFFilesToAttach = listOfFiles;
	}

	/**
	 * Constructor to call when values for smtp-server,smtp-auth-user,
	 * smtp-auth-pwd,email-from,email-to are to read form properties file and
	 * then set the values in all the fields
	 * 
	 * @param properties
	 *            properties file having attributes required for sending email
	 *            like SMTP server name , from email address , to email address
	 *            , username and password for authentication
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 * @param listOfFiles
	 *            list of files to attach while sending email
	 */
	public MailSender(Properties properties, String emailSubject, String emailBody, List<String> listOfFiles) {

		this(null, null, null, null, null, emailSubject, emailBody, listOfFiles);
		if (properties == null)
		{
			TestLogger.log(new InfoEvent(MailSender.class, "Properties file can not be null " + properties));
		} else
		{
			smtpServer = properties.getProperty(Constants.SMTP_SERVER);
			smtpAuthUser = properties.getProperty(Constants.SMTP_AUTH_USER);
			smtpAuthPwd = properties.getProperty(Constants.SMTP_AUTH_PWD);
			emailFrom = properties.getProperty(Constants.EMAIL_FROM);
		}
		if (emailFrom == null)
			emailFrom = defaultEmailFrom;
		if (properties.getProperty(Constants.EMAIL_TO) != null)
			emailTo = properties.getProperty(Constants.EMAIL_TO).split(",");
	}

	/**
	 * Constructor to call when authentication is not required and default from
	 * address to be used and no attachment to send
	 * 
	 * @param smtp_server
	 *            name of SMTP server
	 * @param emailTo
	 *            email address to which mail will be send
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 */
	public MailSender(String smtp_server, String emailTo[], String emailSubject, String emailBody) {
		this(smtp_server, null, null, null, emailTo, emailSubject, emailBody, null);
		this.emailFrom = defaultEmailFrom;
	}

	/**
	 * Constructor to call when authentication is not required and no attachment
	 * to send
	 * 
	 * @param smtp_server
	 *            name of SMTP server
	 * @param emailFrom
	 *            email address from which mail will be send
	 * @param emailTo
	 *            email address to which mail will be send
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 */
	public MailSender(String smtp_server, String emailFrom, String emailTo[], String emailSubject, String emailBody) {
		this(smtp_server, null, null, emailFrom, emailTo, emailSubject, emailBody, null);
	}

	/**
	 * Constructor to call when authentication is not required and default from
	 * address to be used
	 * 
	 * @param smtp_server
	 *            name of SMTP server
	 * @param emailTo
	 *            email address to which mail will be sen
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 * @param listOfFiles
	 *            list of files to attach while sending email
	 */
	public MailSender(String smtp_server, String emailTo[], String emailSubject, String emailBody, List<String> listOfFiles) {
		this(smtp_server, null, null, null, emailTo, emailSubject, emailBody, listOfFiles);
		this.emailFrom = defaultEmailFrom;
	}

	/**
	 * Constructor to call when authentication is not required
	 * 
	 * @param smtp_server
	 *            name of SMTP server
	 * @param emailFrom
	 *            email address from which mail will be send
	 * @param emailTo
	 *            email address to which mail will be send
	 * @param emailSubject
	 *            subject of email
	 * @param emailBody
	 *            body of email
	 * @param listOfFiles
	 *            list of files to attach while sending email
	 */
	public MailSender(String smtp_server, String emailFrom, String emailTo[], String emailSubject, String emailBody,
			List<String> listOfFiles) {
		this(smtp_server, null, null, emailFrom, emailTo, emailSubject, emailBody, listOfFiles);
	}

	public void run()
	{
		try
		{
			Authenticator auth = null;
			Properties props = new Properties();
			InternetAddress[] addressTo = null;
			if (smtpServer == null || smtpServer.trim().equals(""))
			{
				throw new MessagingException("SMTP server not found, Please give the SMTP server");
			}
			props.put("mail.smtp.host", smtpServer);
			Session session = null;

			if ((smtpAuthUser != null && !smtpAuthUser.trim().equals("")) && (smtpAuthPwd != null && !smtpAuthPwd.trim().equals("")))
			{
				props.put("mail.smtp.auth", "true");
				auth = new SMTPAuthenticator();
				session = Session.getDefaultInstance(props, auth);
			} else
			{
				props.put("mail.smtp.auth", "false");
				session = Session.getDefaultInstance(props);
			}

			// create a message
			Message msg = new MimeMessage(session);

			// set the from and to address
			if (emailFrom == null || emailFrom.trim().equals(""))
			{
				emailFrom = defaultEmailFrom;
			}
			InternetAddress addressFrom = new InternetAddress(emailFrom);
			msg.setFrom(addressFrom);

			// Set recipients
			if ((emailTo == null || Arrays.toString(emailTo).trim().equals("")))
			{
				throw new AddressException("Email-To not found,Please give the Email-to address");
			} else
			{
				addressTo = new InternetAddress[emailTo.length];
				for (int i = 0; i < emailTo.length; i++)
				{
					addressTo[i] = new InternetAddress(emailTo[i], true);
				}
				msg.setRecipients(Message.RecipientType.TO, addressTo);
			}

			// Create the message part
			MimeBodyPart messageForBody = new MimeBodyPart();
			messageForBody.setText(emailBody);
			Multipart multipart = new MimeMultipart();
			if (listOFFilesToAttach != null)
			{
				for (String filePath : listOFFilesToAttach)
				{
					// Create the message part
					MimeBodyPart messageForFile = new MimeBodyPart();
					messageForFile.attachFile(new File(filePath));
					multipart.addBodyPart(messageForFile);
				}
			}

			// Setting the Subject and Content Type
			msg.setSubject(emailSubject);
			multipart.addBodyPart(messageForBody);
			msg.setContent(multipart);
			Transport.send(msg);
		} catch (Exception e)
		{
			TestLogger.log(new ErrorEvent(MailSender.class, "Failed in sending mails: " + ExceptionUtil.stackTraceToString(e)));
		}
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		public PasswordAuthentication getPasswordAuthentication()
		{
			String username = smtpAuthUser;
			String password = smtpAuthPwd;
			return new PasswordAuthentication(username, password);
		}
	}
}
