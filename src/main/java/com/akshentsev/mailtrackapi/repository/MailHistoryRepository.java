package com.akshentsev.mailtrackapi.repository;

import com.akshentsev.mailtrackapi.entity.MailEntity;
import com.akshentsev.mailtrackapi.entity.MailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailHistoryRepository extends JpaRepository<MailHistoryEntity, Long> {
    List<MailHistoryEntity> findAllByMailEntity(MailEntity mail);
}
