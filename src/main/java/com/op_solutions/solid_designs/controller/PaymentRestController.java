package com.op_solutions.solid_designs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.op_solutions.solid_designs.paymentFactory.Payment;
import com.op_solutions.solid_designs.paymentFactory.Refund;
import com.op_solutions.solid_designs.services.Aes256CbcService;
import com.op_solutions.solid_designs.services.EmailService;
import com.op_solutions.solid_designs.services.EncryptionService;
import com.op_solutions.sunsmart.SignatureGenerator;
import com.op_solutions.sunsmart.SunSmart;

@RestController
@RequestMapping("v1/payments")
public class PaymentRestController {

    private List<Payment> paymentList;
    private List<Refund> refundList;
    private EmailService emailService;
    @Autowired
    private SunSmart sunsmart;
    
    @Autowired
    private Aes256CbcService aes256CbcService;

    public PaymentRestController(List<Payment> paymentList, List<Refund> refundList, EmailService emailService) {
        this.paymentList = paymentList;
        this.emailService = emailService;
        this.refundList = refundList;
    }
    
    @GetMapping("/sunsmart")
   public String sunsmart()
   {
    	return sunsmart.callSunSmart();
   }
    
    @GetMapping("/CTOSsignature")
    public String encodeString()
    {
    	
    	String apiKey = "samplekey";
        String packageName = "ctos";
        String refId = "ctos123";
        String securityKey = "ctos883929";
        String requestTime = "2020-04-16 14:14:17";
        
        try {
			String signature = SignatureGenerator.generateSignature(apiKey, packageName, refId, securityKey, requestTime);
			 System.out.println("Generated Signature: " + signature);
			 
			 return signature;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
        
    	
    }

    @GetMapping("/ctos")
    public void callCTOS() throws Exception
    {
    	String apiKey = "sampleApiKey";
        String ciphertext = "sampleCiphertext";

        String jsonBody = "{\"name\":\"John Doe\",\"age\":30}";
        
        String encrypted = aes256CbcService.encrypt(jsonBody, apiKey, ciphertext);
        
        System.out.println("Encrypted data: " + encrypted);

        String decrypted = aes256CbcService.decrypt(encrypted, apiKey, ciphertext);
        System.out.println("Decrypted JSON: " + decrypted);
        
    }
    
    @GetMapping("/pay/{method}")
    public String processPayment(@PathVariable String method,@RequestParam double amount)
    {
        for (Payment payment : paymentList)
        {
            if(payment.getClass().getSimpleName().equalsIgnoreCase(method))
            {
                payment.paymentProcess(amount);
                emailService.sendEmail();
                return "Payment processed using method" + method;
            }
        }

        return  "Payment method not found for payment";
    }

    @GetMapping("/refund/{method}")
    public String processRefund(@PathVariable String method,@RequestParam double amount)
    {
        for (Refund refund : refundList)
        {
            if(refund.getClass().getSimpleName().equalsIgnoreCase(method))
            {
                refund.processRefund(amount);
                emailService.sendEmail();
                return "Payment refund initiated  using method" + method;
            }
        }

        return  "Payment method not found for processing refund";
    }
    
    @Autowired
    private EncryptionService encryptionService;

    public void sendEncryptedRequest() throws Exception {
    	
        String bodyJson = "{\"key\":\"value\"}"; // Your JSON string request body
        String apiKey = "CTOS_API_KEY";
        String ciphertext = "CTOS_CIPHERTEXT"; // IV provided by CTOS
        
        String encryptedData = encryptionService.encryptRequestBody(bodyJson, apiKey, ciphertext);

        // Format JSON request payload
        String requestPayload = "{\"data\":\"" + encryptedData + "\", \"api_key\":\"" + apiKey + "\"}";

        // Send this requestPayload as body of your API request
    }

}
