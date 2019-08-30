package com.levent.pcd.mail;


public interface HtmlEmailSender {
	
	public EmailResponse send(final Email email) throws Exception;

	public void setEmailConfig(EmailConfig emailConfig);

}
