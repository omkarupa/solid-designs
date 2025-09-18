package com.op_solutions.solid_designs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail()
    {
        logger.info("send email from Email Service");
    }
}
