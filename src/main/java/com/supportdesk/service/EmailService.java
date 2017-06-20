package com.supportdesk.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.common.ExcelWriter;
import com.supportdesk.dao.ChangeRequestDAO;
import com.supportdesk.dao.IncidentSLMDAO;
import com.supportdesk.dao.IncidentSummaryDAO;
import com.supportdesk.dao.QueueDAO;
import com.supportdesk.dao.TaskDAO;
import com.supportdesk.dao.WorkOrderDAO;
import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.TaskEntity;
import com.supportdesk.entity.WorkOrderEntity;

@Service
public class EmailService extends AbstractService {

	@Autowired
	IncidentSLMDAO incidentSLMDAO;

	@Autowired
	IncidentSummaryDAO incidentSummaryDAO;

	@Autowired
	WorkOrderDAO 	workOrderDAO;

	@Autowired
	ChangeRequestDAO 	changeRequestDAO;

	@Autowired
	TaskDAO 	taskDAO;

	@Autowired
	QueueDAO  queueDAO;

	@Transactional
	public void sendMail() {
		List<IncidentSlmEntity> incidentList = incidentSLMDAO.getOpenIncidentList();
		List<WorkOrderEntity> workOrderList = workOrderDAO.getOpenWorkOrderList();
		List<ChangeRequestEntity> changeRequestList = changeRequestDAO.getOpenChangeRequestList();
		List<TaskEntity> taskList = taskDAO.getOpenTaskList();
		File file;

		ExcelWriter excelWriter = new ExcelWriter();
		try {
			excelWriter.setIncidentList(incidentList);
			excelWriter.setWorkOrderList(workOrderList);
			excelWriter.setChangeRequestList(changeRequestList);
			excelWriter.setTaskList(taskList);
			file = excelWriter.exportToXLS();
			generateAndSendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateAndSendMail()
	{

		// Recipient's email ID needs to be mentioned.
		String to = "user@com";

		// Sender's email ID needs to be mentioned
		String from = "user@com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		//properties.setProperty("mail.user", "myuser");
		//properties.setProperty("mail.password", "mypwd");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("This is message body");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// [START multipart_example]
			String htmlBody = "";          // ...
			byte[] attachmentData = null;  // ...

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html");
			multipart.addBodyPart(htmlPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "file.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart );

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println("Error Occurred: Unable to send email");
		}
	}

	private void sendMultipartMail() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "...";

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress("user@example.com", "Mr. User"));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText(msgBody);

			// [START multipart_example]
			String htmlBody = "";          // ...
			byte[] attachmentData = null;  // ...
			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html");
			mp.addBodyPart(htmlPart);

			MimeBodyPart attachment = new MimeBodyPart();
			InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
			attachment.setFileName("manual.pdf");
			attachment.setContent(attachmentDataStream, "application/pdf");
			mp.addBodyPart(attachment);

			msg.setContent(mp);
			// [END multipart_example]

			Transport.send(msg);

		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// ...
		}
	}
}
