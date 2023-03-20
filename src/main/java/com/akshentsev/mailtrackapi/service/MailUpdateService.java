package com.akshentsev.mailtrackapi.service;

import com.akshentsev.mailtrackapi.dto.MailUpdateRequest;
import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.model.MailStatus;
import com.akshentsev.mailtrackapi.model.MailUpdateType;
import jakarta.validation.Valid;

public interface MailUpdateService {
    MailUpdateType getType();
    MailStatus update(MailEntity mail, @Valid MailUpdateRequest mailUpdateRequest);
}
