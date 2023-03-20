package com.akshentsev.mailtrackapi.config;

import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import com.akshentsev.mailtrackapi.entity.PostOfficeEntity;
import com.akshentsev.mailtrackapi.model.MailStatus;
import com.akshentsev.mailtrackapi.model.MailType;
import com.akshentsev.mailtrackapi.repository.MailHistoryRepository;
import com.akshentsev.mailtrackapi.repository.MailRepository;
import com.akshentsev.mailtrackapi.repository.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DatabaseInitializer implements CommandLineRunner {


    private final MailHistoryRepository mailHistoryRepository;


    private final MailRepository mailRepository;


    private final PostOfficeRepository postOfficeRepository;

    @Override
    @Transactional
    public void run(String... args) {
        createAndSavePostOffices();
        createAndSaveMailEntities();
        createMailHistory();
    }

    private void createAndSavePostOffices() {
        List<PostOfficeEntity> postOffices = Arrays.asList(
                new PostOfficeEntity(null, "101000", "Московский Главпочтамт", "Москва, ул. Мясницкая, 26"),
                new PostOfficeEntity(null, "190000", "Санкт-Петербургский Главпочтамт", "Санкт-Петербург, ул. Почтамтская, 9"),
                new PostOfficeEntity(null, "420000", "Казанский Главпочтамт", "Казань, ул. Кремлевская, 35"),
                new PostOfficeEntity(null, "630000", "Новосибирский Главпочтамт", "Новосибирск, ул. Советская, 37"),
                new PostOfficeEntity(null, "620000", "Екатеринбургский Главпочтамт", "Екатеринбург, ул. Малышева, 31"),
                new PostOfficeEntity(null, "450000", "Уфимский Главпочтамт", "Уфа, ул. Ленина, 9"),
                new PostOfficeEntity(null, "614000", "Пермский Главпочтамт", "Пермь, ул. Ленина, 49"),
                new PostOfficeEntity(null, "344000", "Ростовский Главпочтамт", "Ростов-на-Дону, ул. Большая Садовая, 33"),
                new PostOfficeEntity(null, "680000", "Хабаровский Главпочтамт", "Хабаровск, ул. Муравьева-Амурского, 13"),
                new PostOfficeEntity(null, "690000", "Владивостокский Главпочтамт", "Владивосток, ул. Светланская, 35")
        );

        postOfficeRepository.saveAll(postOffices);
    }

    private void createAndSaveMailEntities() {
        List<MailEntity> mailItems = new ArrayList<>();

        List<PostOfficeEntity> postOffices = postOfficeRepository.findAll();

        int mailCounter = 1;
        for (PostOfficeEntity postOffice : postOffices) {
            for (int i = 0; i < 10; i++) {
                MailEntity mail = MailEntity.builder()
                        .id(null)
                        .type(MailType.values()[new Random().nextInt(MailType.values().length)])
                        .recipientName("Recipient Name " + mailCounter)
                        .recipientAddress("Recipient Address " + mailCounter)
                        .recipientOffice(postOffice)
                        .trackingCode("TRACK" + mailCounter)
                        .build();
                mailItems.add(mail);
                mailCounter++;
            }
        }

        mailRepository.saveAll(mailItems);
    }


    private void createMailHistory() {
        List<MailEntity> mailEntities = mailRepository.findAll();
        List<PostOfficeEntity> postOffices = postOfficeRepository.findAll();
        Random random = new Random();

        for (MailEntity mailEntity : mailEntities) {
            PostOfficeEntity postOffice = postOffices.stream()
                    .filter(p -> p.getId().equals(mailEntity.getRecipientOffice().getId()))
                    .findFirst()
                    .orElse(null);

            if (postOffice != null) {
                MailHistoryEntity mailHistoryEntity = new MailHistoryEntity();
                mailHistoryEntity.setMailEntity(mailEntity);
                mailHistoryEntity.setPostOffice(postOffice);
                mailHistoryEntity.setDateTime(LocalDateTime.now().minusDays(random.nextInt(30)));
                mailHistoryEntity.setStatus(MailStatus.REGISTERED);

                mailHistoryRepository.save(mailHistoryEntity);
            }
        }
    }
}

