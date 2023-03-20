package com.akshentsev.mailtrackapi.dto;

import com.akshentsev.mailtrackapi.model.MailStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailUpdateResponse {
    private String trackingCode;
    private MailStatus mailStatus;
}
