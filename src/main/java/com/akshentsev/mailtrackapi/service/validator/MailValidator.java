package com.akshentsev.mailtrackapi.service.validator;

import com.akshentsev.mailtrackapi.entity.MailEntity;
import org.springframework.stereotype.Service;

@Service
public class MailValidator {
    public void validate(MailEntity mail) {
        if(mail.getRecipientName().isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (mail.getRecipientName().length() < 3 || mail.getRecipientName().length() > 50) {
            throw new IllegalArgumentException("Длина имени должна быть от 3 до 50 символов");
        }
    }
}
