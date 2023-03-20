package com.akshentsev.mailtrackapi.dto;

import com.akshentsev.mailtrackapi.model.MailUpdateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailUpdateRequest {
    private String trackingCode;
    private String postalCodeTo;
    private String postalCodeFrom;
    private MailUpdateType type;
}
