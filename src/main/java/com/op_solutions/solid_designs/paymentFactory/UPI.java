package com.op_solutions.solid_designs.paymentFactory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UPI implements Payment{

    Logger logger = LoggerFactory.getLogger(UPI.class);


    @Override
    public void paymentProcess(double amount) {
        logger.info("Payment method selected {} with payment amount {}",getClass().getSimpleName(),amount);
    }
}
