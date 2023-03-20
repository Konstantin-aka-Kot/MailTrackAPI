package com.akshentsev.mailtrackapi.dto;

import com.akshentsev.mailtrackapi.model.MailType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailCreateRequest {
    private MailType type;
    private String recipientName;
    private String recipientAddress;
    private String postalCodeTo;
    private String postalCodeFrom;
}
