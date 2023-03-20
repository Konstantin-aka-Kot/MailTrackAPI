package com.akshentsev.mailtrackapi.service;

import com.akshentsev.mailtrackapi.dto.MailCreateRequest;
import com.akshentsev.mailtrackapi.dto.MailCreateResponse;
import com.akshentsev.mailtrackapi.dto.MailUpdateRequest;
import com.akshentsev.mailtrackapi.dto.MailUpdateResponse;


public interface MailService {
    MailCreateResponse createMail(MailCreateRequest mailCreateRequest);
    MailUpdateResponse updateMail(MailUpdateRequest mailUpdateRequest);

}
