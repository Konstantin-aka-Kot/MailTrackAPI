package com.akshentsev.mailtrackapi.service.impl;

import com.akshentsev.mailtrackapi.dto.MailUpdateRequest;
import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import com.akshentsev.mailtrackapi.entity.PostOfficeEntity;
import com.akshentsev.mailtrackapi.model.MailStatus;
import com.akshentsev.mailtrackapi.model.MailUpdateType;
import com.akshentsev.mailtrackapi.repository.MailHistoryRepository;
import com.akshentsev.mailtrackapi.repository.PostOfficeRepository;
import com.akshentsev.mailtrackapi.service.MailUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MailReceiveService implements MailUpdateService {
    private final MailHistoryRepository mailHistoryRepository;
    private final PostOfficeRepository postOfficeRepository;
    @Override
    public MailUpdateType getType() {
        return MailUpdateType.RECEIVE;
    }

    @Override
    public MailStatus update(MailEntity mail, MailUpdateRequest mailUpdateRequest) {
        PostOfficeEntity postOfficeFrom = postOfficeRepository.findByPostalCode(mailUpdateRequest.getPostalCodeFrom())
                .orElseThrow();
        MailStatus mailStatus;
        if (!postOfficeFrom.getPostalCode().equals(mail.getRecipientOffice().getPostalCode())) {
            mailStatus = MailStatus.FAILED_DELIVERY;
        } else {
            mailStatus = MailStatus.DELIVERED;
        }

        MailHistoryEntity mailHistory = MailHistoryEntity.builder()
                .status(mailStatus)
                .mailEntity(mail)
                .postOffice(postOfficeFrom)
                .dateTime(LocalDateTime.now())
                .build();
        mailHistoryRepository.save(mailHistory);

        return mailStatus;
    }
}
