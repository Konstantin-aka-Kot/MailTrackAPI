package com.akshentsev.mailtrackapi.service;

import com.akshentsev.mailtrackapi.dto.MailHistoryResponse;

public interface MailHistoryService {
    MailHistoryResponse getHistory(String trackingCode);
}
