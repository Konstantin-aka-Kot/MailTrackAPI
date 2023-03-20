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
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MailArriveService implements MailUpdateService {
    private final MailHistoryRepository mailHistoryRepository;
    private final PostOfficeRepository postOfficeRepository;
    @Override
    public MailUpdateType getType() {
        return MailUpdateType.ARRIVE;
    }

    @Override
    @Transactional
    public MailStatus update(MailEntity mail, MailUpdateRequest mailUpdateRequest) {
        PostOfficeEntity postOfficeTo = postOfficeRepository.findByPostalCode(mailUpdateRequest.getPostalCodeTo())
                .orElseThrow();
        MailStatus mailStatus;
        if (!postOfficeTo.getPostalCode().equals(mail.getRecipientOffice().getPostalCode())) {
            mailStatus = MailStatus.ARRIVED_AT_TRANSIT;
        } else {
            mailStatus = MailStatus.ARRIVED_AT_DESTINATION;
        }

        MailHistoryEntity mailHistory = MailHistoryEntity.builder()
                .status(mailStatus)
                .mailEntity(mail)
                .postOffice(postOfficeTo)
                .dateTime(LocalDateTime.now())
                .build();
        mailHistoryRepository.save(mailHistory);

        return mailStatus;
    }
}
