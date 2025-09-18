package com.op_solutions.solid_designs.controller;

import com.op_solutions.solid_designs.paymentFactory.Payment;
import com.op_solutions.solid_designs.paymentFactory.Refund;
import com.op_solutions.solid_designs.services.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/payments")
public class PaymentRestController {

    private List<Payment> paymentList;
    private List<Refund> refundList;
    private EmailService emailService;

    public PaymentRestController(List<Payment> paymentList, List<Refund> refundList, EmailService emailService) {
        this.paymentList = paymentList;
        this.emailService = emailService;
        this.refundList = refundList;
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
}
