package com.levent.pcd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.levent.pcd.model.Order;
import com.levent.pcd.model.Order.OrderStatus;
import com.levent.pcd.model.ShoppingCartMap;
import com.levent.pcd.repository.OrderRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Component
	public class PayPalClient {

	@Autowired OrderRepository rep;
	    String clientId = "ASTze2vhR30SxI6vOYNPVLfy_F9KmqxIagySXObBtmbAyXJYMxvwBWNQHv42uYRqLrHdLxm-IHV95C5Y";
	    String clientSecret = "EHN-bcn02PKbwlEkBWtiMXTPMF0F39pLl_N-qy2rI4LzBcepeJlFkCL1bKOYwWJLT69tgYz-2OqLpfr1";

	    @Autowired ShoppingCartMap cart;
	    String orderId;
	    
	    @Autowired ProductService pService;
//	    PayPalClient(){}

	    public String createPayment(String sum, String string, String username){
	        Map<String, Object> response = new HashMap<String, Object>();
	        Amount amount = new Amount();
	        amount.setCurrency("INR");
	        amount.setTotal(sum);
	        Transaction transaction = new Transaction();
	        transaction.setAmount(amount);
	        List<Transaction> transactions = new ArrayList<Transaction>();
	        transactions.add(transaction);

	        Payer payer = new Payer();
	        payer.setPaymentMethod("paypal");

	        Payment payment = new Payment();
	        payment.setIntent("sale");
	        payment.setPayer(payer);
	        payment.setTransactions(transactions);
	        Order o=rep.insert(Order.builder().date(LocalDateTime.now()).status(OrderStatus.ORDER_INITIATED).username(username).build());
	         orderId=o.getOrderId();
	        RedirectUrls redirectUrls = new RedirectUrls();
	        redirectUrls.setCancelUrl("http://localhost:9000/payment_failure");
	        redirectUrls.setReturnUrl("http://localhost:9000/payment_start?sum="+sum);
	        payment.setRedirectUrls(redirectUrls);
	        Payment createdPayment;
	        String redirectUrl = "";
	        try {
	          
	            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
	            createdPayment = payment.create(context);
	            if(createdPayment!=null){
	                List<Links> links = createdPayment.getLinks();
	                for (Links link:links) {
	                    if(link.getRel().equals("approval_url")){
	                        redirectUrl = link.getHref();
	                        break;
	                    }
	                }
	                System.out.println(createdPayment);
	                response.put("status", "success");
	                response.put("redirect_url", redirectUrl);
	                
	            }
	        } catch (PayPalRESTException e) {
	            System.out.println("Error happened during payment creation!");
	            return "redirect:/payment_failure";
	        }
	        rep.save(Order.builder().orderId(orderId).date(LocalDateTime.now()).status(OrderStatus.PAYMENT_INITIATED).username(username).build());
	        
	        return "redirect:"+redirectUrl;
	    }


	    public String completePayment(String paymentId, String payerId, String username, String sum){
	        Map<String, Object> response = new HashMap();
	        Payment payment = new Payment();
	        payment.setId(paymentId);
	        PaymentExecution paymentExecution = new PaymentExecution();
	        paymentExecution.setPayerId(payerId);
	        try {
	            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
	            Payment createdPayment = payment.execute(context, paymentExecution);
	            if(createdPayment!=null){
	                response.put("status", "success");
	                response.put("payment", createdPayment);
	            }
	        } catch (PayPalRESTException e) {
	            System.err.println(e.getDetails());
	            return "redirect:/payment_failure";
	        }
	        rep.save(Order.builder().orderId(orderId).date(LocalDateTime.now()).status(OrderStatus.PAYMENT_SUCCESS).username(username).build());
	        
	        
	        pService.updateProductsRemained(orderId,cart.getCartItems().values(),username);
	        return "redirect:/payment_success";
	    }



	
}
