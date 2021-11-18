package com.example.final_project.service;

import com.example.final_project.pojo.EmailMessage;

public interface EmailMessageSender {

    void sendEmail(EmailMessage emailMessage);
}
