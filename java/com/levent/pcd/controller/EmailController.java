/*package com.levent.pcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.levent.pcd.mail.Email;
import com.levent.pcd.mail.EmailComponent;
import com.levent.pcd.mail.EmailResponse;

@RestController
public class EmailController {

	@Autowired EmailComponent component;
	
	public EmailResponse sendMail(@RequestBody Email email) throws Exception {
		  
	    return component.send(email);
	}
}
*/