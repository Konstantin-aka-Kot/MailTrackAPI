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
public class MailDepartService implements MailUpdateService {
    private final MailHistoryRepository mailHistoryRepository;
    private final PostOfficeRepository postOfficeRepository;

    @Override
    public MailUpdateType getType() {
        return MailUpdateType.DEPART;
    }

    @Override
    public MailStatus update(MailEntity mail, MailUpdateRequest mailUpdateRequest) {
        PostOfficeEntity postOfficeTo = postOfficeRepository.findByPostalCode(mailUpdateRequest.getPostalCodeTo())
                .orElseThrow();
        PostOfficeEntity postOfficeFrom = postOfficeRepository.findByPostalCode(mailUpdateRequest.getPostalCodeFrom())
                .orElseThrow();

        MailHistoryEntity mailHistoryFrom = MailHistoryEntity.builder()
                .status(MailStatus.DEPARTURE_FROM_POSTAL_OFFICE)
                .mailEntity(mail)
                .postOffice(postOfficeFrom)
                .dateTime(LocalDateTime.now())
                .build();
        mailHistoryRepository.save(mailHistoryFrom);

        MailHistoryEntity mailHistoryTo = MailHistoryEntity.builder()
                .status(MailStatus.IN_TRANSIT)
                .mailEntity(mail)
                .postOffice(postOfficeTo)
                .dateTime(LocalDateTime.now())
                .build();
        mailHistoryRepository.save(mailHistoryTo);

        return MailStatus.DEPARTURE_FROM_POSTAL_OFFICE;
    }
}
