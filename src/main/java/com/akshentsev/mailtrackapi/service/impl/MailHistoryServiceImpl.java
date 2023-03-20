package com.akshentsev.mailtrackapi.service.impl;

import com.akshentsev.mailtrackapi.dto.MailHistoryResponse;
import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import com.akshentsev.mailtrackapi.repository.MailHistoryRepository;
import com.akshentsev.mailtrackapi.repository.MailRepository;
import com.akshentsev.mailtrackapi.service.MailHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MailHistoryServiceImpl implements MailHistoryService {
    private final MailHistoryRepository mailHistoryRepository;
    private final MailRepository mailRepository;

    @Override
    public MailHistoryResponse getHistory(String trackingCode) {
        MailEntity mail = mailRepository.findByTrackingCode(trackingCode).orElseThrow();
        MailHistoryResponse mailHistoryResponse = new MailHistoryResponse();
        mailHistoryResponse.setHistory(mailHistoryRepository.findAllByMailEntity(mail));
        return mailHistoryResponse;
    }
}
