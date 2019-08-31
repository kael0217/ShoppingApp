package com.levent.pcd.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.levent.pcd.mail.Email;
import com.levent.pcd.mail.EmailComponent;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.model.UserInfo;
import com.levent.pcd.service.PayPalClient;

@Controller
public class PaymentController {

	@Autowired
	EmailComponent emailComponent;
	private final PayPalClient payPalClient;

	@Autowired
	PaymentController(PayPalClient payPalClient) {
		this.payPalClient = payPalClient;
	}

	@PostMapping(value = "/make/payment")
	public Map<String, Object> makePayment(@RequestParam("sum") String sum) {
		return payPalClient.createPayment(sum);
	}

	@PostMapping(value = "/complete/payment")
	public Map<String, Object> completePayment(HttpServletRequest request, @RequestParam("paymentId") String paymentId,
			@RequestParam("payerId") String payerId) {
		return payPalClient.completePayment(request);
	}

	@GetMapping("/payment_success")
	public String processPaymentSuccess(HttpSession session) throws Exception {
		System.out.println("get*********");
		UserEntry entry = (UserEntry) session.getAttribute("userEntry");
		UserInfo user = entry.getUser();
		Map<String, String> templateTokens = new HashMap<String, String>();
		templateTokens.put("LABEL_HI", "Hello");
		templateTokens.put("EMAIL_STORE_NAME", user.getUsername());

		templateTokens.put("EMAIL_ORDER_DATE", LocalDate.now().toString());
		templateTokens.put("EMAIL_ORDER_THANKS", "Thanks for your purchase!");
		templateTokens.put("EMAIL_ORDER_DETAILS_TITLE", "Your order details are:");
		templateTokens.put("ADDRESS_BILLING_TITLE", "Address details:");

		templateTokens.put("ADDRESS_BILLING", user.getAddresses().get(0));
		templateTokens.put("ADDRESS_DELIVERY_TITLE", "Address details:");

		templateTokens.put("ADDRESS_DELIVERY", user.getAddresses().get(0));
		templateTokens.put("ORDER_STATUS", "success!");
		templateTokens.put("EMAIL_DISCLAIMER", "@shoppersClub");

		templateTokens.put("EMAIL_FOOTER_COPYRIGHT", "@Copyright");

		Email email = new Email();
		email.setFrom("Default store");
		email.setFromEmail("jahanvi.bansal@gmail.com");
		email.setSubject("Contact");
		email.setTo("payal@rjtcompuquest.com");
		email.setTemplateName("email_template_contact.ftl");
		email.setTemplateTokens(templateTokens);

		emailComponent.send(email);
		System.out.println("sent!");
		return "redirect:/products";
	}

	@GetMapping("/payment_failure")
	public String processPaymentFailure(HttpSession session) throws Exception {
		UserEntry entry = (UserEntry) session.getAttribute("userEntry");
		UserInfo user = entry.getUser();
		Map<String, String> templateTokens = new HashMap<String, String>();
		templateTokens.put("LABEL_HI", "Hello");
		templateTokens.put("EMAIL_STORE_NAME", user.getUsername());

		templateTokens.put("EMAIL_ORDER_DATE", LocalDate.now().toString());
		templateTokens.put("EMAIL_ORDER_THANKS", "Thanks for your purchase!");
		templateTokens.put("EMAIL_ORDER_DETAILS_TITLE", "Your order details are:");
		templateTokens.put("ADDRESS_BILLING_TITLE", "Address details:");

		templateTokens.put("ADDRESS_BILLING", user.getAddresses().get(0));
		templateTokens.put("ADDRESS_DELIVERY_TITLE", "Address details:");

		templateTokens.put("ADDRESS_DELIVERY", user.getAddresses().get(0));
		templateTokens.put("ORDER_STATUS", "Failed!");
		templateTokens.put("EMAIL_DISCLAIMER", "@shoppersClub");

		templateTokens.put("EMAIL_FOOTER_COPYRIGHT", "@Copyright");

		Email email = new Email();
		email.setFrom("Default store");
		email.setFromEmail("jahanvi.bansal@gmail.com");
		email.setSubject("Contact");
		email.setTo("payal@rjtcompuquest.com");
		email.setTemplateName("email_template_contact.ftl");
		email.setTemplateTokens(templateTokens);

		emailComponent.send(email);
		System.out.println("sent!");
		return "redirect:/products";
	}
}
