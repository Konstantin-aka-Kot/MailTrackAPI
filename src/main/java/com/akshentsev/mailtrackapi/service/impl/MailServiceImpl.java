package com.akshentsev.mailtrackapi.service.impl;

import com.akshentsev.mailtrackapi.dto.MailCreateRequest;
import com.akshentsev.mailtrackapi.dto.MailCreateResponse;
import com.akshentsev.mailtrackapi.dto.MailUpdateRequest;
import com.akshentsev.mailtrackapi.dto.MailUpdateResponse;
import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import com.akshentsev.mailtrackapi.entity.PostOfficeEntity;
import com.akshentsev.mailtrackapi.model.MailStatus;
import com.akshentsev.mailtrackapi.model.MailUpdateType;
import com.akshentsev.mailtrackapi.repository.MailHistoryRepository;
import com.akshentsev.mailtrackapi.repository.MailRepository;
import com.akshentsev.mailtrackapi.repository.PostOfficeRepository;
import com.akshentsev.mailtrackapi.service.MailService;
import com.akshentsev.mailtrackapi.service.MailUpdateService;
import com.akshentsev.mailtrackapi.service.validator.MailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final MailHistoryRepository mailHistoryRepository;
    private final Map<MailUpdateType,MailUpdateService> mailUpdateServicesByType;
    private final MailValidator mailValidator;

    public MailServiceImpl(MailRepository mailRepository,
                           PostOfficeRepository postOfficeRepository,
                           MailHistoryRepository mailHistoryRepository,
                           MailValidator mailValidator,
                           List<MailUpdateService> mailUpdateServices) {
        this.mailRepository = mailRepository;
        this.postOfficeRepository = postOfficeRepository;
        this.mailHistoryRepository = mailHistoryRepository;
        this.mailUpdateServicesByType = mailUpdateServices.stream()
                .collect(Collectors.toMap(MailUpdateService::getType, Function.identity()));
        this.mailValidator = mailValidator;
    }

    @Override
    @Transactional
    public MailCreateResponse createMail(MailCreateRequest mailCreateRequest) {


        PostOfficeEntity postOfficeTo = postOfficeRepository.findByPostalCode(mailCreateRequest.getPostalCodeTo())
                .orElseThrow();
        PostOfficeEntity postOfficeFrom = postOfficeRepository.findByPostalCode(mailCreateRequest.getPostalCodeFrom())
                .orElseThrow();
        String trackingCode = UUID.randomUUID().toString();

        MailEntity mailEntity = MailEntity.builder()
                .type(mailCreateRequest.getType())
                .recipientName(mailCreateRequest.getRecipientName())
                .recipientAddress(mailCreateRequest.getRecipientAddress())
                .recipientOffice(postOfficeTo)
                .trackingCode(trackingCode)
                .build();
        mailValidator.validate(mailEntity);
        mailRepository.save(mailEntity);

        MailHistoryEntity mailHistory = MailHistoryEntity.builder()
                .status(MailStatus.REGISTERED)
                .mailEntity(mailEntity)
                .postOffice(postOfficeFrom)
                .dateTime(LocalDateTime.now())
                .build();
        mailHistoryRepository.save(mailHistory);

        MailCreateResponse mailCreateResponse = new MailCreateResponse();
        mailCreateResponse.setTrackingCode(trackingCode);
        return mailCreateResponse;
    }

    @Override
    @Transactional
    public MailUpdateResponse updateMail(MailUpdateRequest mailUpdateRequest) {
        MailEntity mailEntity = mailRepository.findByTrackingCode(mailUpdateRequest.getTrackingCode())
                .orElseThrow();
        MailStatus status = mailUpdateServicesByType.get(mailUpdateRequest.getType())
                .update(mailEntity, mailUpdateRequest);

        MailUpdateResponse mailUpdateResponse = new MailUpdateResponse();
        mailUpdateResponse.setTrackingCode(mailEntity.getTrackingCode());
        mailUpdateResponse.setMailStatus(status);
        return mailUpdateResponse;
    }
}


