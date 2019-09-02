package com.levent.pcd.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.levent.pcd.mail.Email;
import com.levent.pcd.mail.EmailComponent;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.model.UserInfo;
import com.levent.pcd.repository.OrderRepository;
import com.levent.pcd.service.PayPalClient;

@Controller
public class PaymentController {

	@Autowired
	EmailComponent emailComponent;
	@Autowired PayPalClient payPalClient;
	
	@Autowired ShoppingCartMap shoppingCartMap;


	@Autowired UserEntry entry;
	@Autowired OrderRepository rep;
	@GetMapping("/payment_start")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public String processPaymentStart( @RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId,  @RequestParam("sum") String sum) throws Exception {
		
		return "redirect:/complete_payment?paymentId="+ paymentId+"&PayerID="+payerId+"&sum="+sum;
	}
	
	@GetMapping("/payment_success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public String sendPaymentSuccessMail( ) throws Exception {
		
		UserInfo user= entry.getUser();
		Map<String, String> templateTokens = new HashMap<String, String>();
		templateTokens.put("LABEL_HI", "Hello");
		templateTokens.put("EMAIL_STORE_NAME", user.getUsername());

		templateTokens.put("EMAIL_ORDER_DATE", LocalDate.now().toString());
		templateTokens.put("EMAIL_ORDER_THANKS", "Thanks for your purchase!");
		templateTokens.put("EMAIL_ORDER_DETAILS_TITLE", "Your order details are:");
		templateTokens.put("ADDRESS_BILLING_TITLE", "Address details:");
		templateTokens.put("EMAIL_ORDER_NUMBER",Math.random()+"");
		templateTokens.put("ADDRESS_BILLING", user.getAddresses().get(0));
		templateTokens.put("ADDRESS_DELIVERY_TITLE", "Address details:");
		templateTokens.put("EMAIL_CUSTOMER_FIRSTNAME",user.getNickname());
		templateTokens.put("ADDRESS_DELIVERY", user.getAddresses().get(0));
		templateTokens.put("ORDER_STATUS", "Payment Success!");
		templateTokens.put("EMAIL_DISCLAIMER", "@shoppersClub");
		templateTokens.put("LOGOPATH","Shopper's Club");
		templateTokens.put("EMAIL_FOOTER_COPYRIGHT", "@Copyright");
		templateTokens.put("EMAIL_CUSTOMER_CONTACT","Shopper's Club: 637736336");
		templateTokens.put("EMAIL_CONTACT_NAME_LABEL","Shopper's Club");
		templateTokens.put("EMAIL_CONTACT_NAME","Shoppers Club");
		templateTokens.put("ORDER_PRODUCTS_DETAILS",shoppingCartMap.getCartItems().values().toString());
		System.out.println("shoppingCart Map"+shoppingCartMap.getCartItems().values());
		Email email = new Email();
		email.setFrom("Default store");
		email.setFromEmail("jahanvi.bansal@gmail.com");
		email.setSubject("Contact");
		email.setTo("payal@rjtcompuquest.com");
		email.setTemplateName("email_template_checkout.ftl");
		email.setTemplateTokens(templateTokens);

		emailComponent.send(email);
		System.out.println("sent!");
		
		return "products";
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/payment_failure")
	public String processPaymentFailure() throws Exception {
		UserInfo user= entry.getUser();
		Map<String, String> templateTokens = new HashMap<String, String>();
		templateTokens.put("LABEL_HI", "Hello");
		templateTokens.put("EMAIL_STORE_NAME", user.getUsername());

		templateTokens.put("EMAIL_ORDER_DATE", LocalDate.now().toString());
		templateTokens.put("EMAIL_ORDER_THANKS", "Thanks for your purchase!");
		templateTokens.put("EMAIL_ORDER_DETAILS_TITLE", "Your order details are:");
		templateTokens.put("ADDRESS_BILLING_TITLE", "Address details:");
		templateTokens.put("EMAIL_ORDER_NUMBER",Math.random()+"");
		templateTokens.put("ADDRESS_BILLING", user.getAddresses().get(0));
		templateTokens.put("ADDRESS_DELIVERY_TITLE", "Address details:");
		templateTokens.put("EMAIL_CUSTOMER_FIRSTNAME",user.getNickname());
		templateTokens.put("ADDRESS_DELIVERY", user.getAddresses().get(0));
		templateTokens.put("ORDER_STATUS", "Failed!!");
		templateTokens.put("EMAIL_DISCLAIMER", "@shoppersClub");
		templateTokens.put("LOGOPATH","Shopper's Club");
		templateTokens.put("EMAIL_FOOTER_COPYRIGHT", "@Copyright");
		templateTokens.put("EMAIL_CUSTOMER_CONTACT","Shopper's Club: 637736336");
		templateTokens.put("EMAIL_CONTACT_NAME_LABEL","Shopper's Club");
		templateTokens.put("EMAIL_CONTACT_NAME","Shoppers Club");
		templateTokens.put("ORDER_PRODUCTS_DETAILS",shoppingCartMap.getCartItems()+"");
		Email email = new Email();
		email.setFrom("Default store");
		email.setFromEmail("jahanvi.bansal@gmail.com");
		email.setSubject("Contact");
		email.setTo("payal@rjtcompuquest.com");
		email.setTemplateName("email_template_checkout.ftl");
		email.setTemplateTokens(templateTokens);

		emailComponent.send(email);
		System.out.println("sent!");
		return "redirect:/products";
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping( "/make_payment")
	public String makePayment(@RequestParam("sum") String sum, HttpServletRequest request) {
		 return payPalClient.createPayment(sum, request.getContextPath(), entry.getUser().getUsername());
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/complete_payment")
	public String completePayment( @RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId , @RequestParam("sum") String sum) {
		return payPalClient.completePayment(paymentId, payerId,entry.getUser().getUsername(), sum);
	}
}
