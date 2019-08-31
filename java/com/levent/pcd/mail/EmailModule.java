package com.levent.pcd.mail;

public interface EmailModule {
  
  public EmailResponse send(final Email email) throws Exception;

  public void setEmailConfig(EmailConfig emailConfig);

}
