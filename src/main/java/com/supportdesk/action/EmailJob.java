package com.supportdesk.action;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.supportdesk.service.EmailService;

public class EmailJob extends QuartzJobBean {

	EmailService emailService;
	
	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			emailService.sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
