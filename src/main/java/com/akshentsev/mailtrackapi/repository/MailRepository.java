package com.akshentsev.mailtrackapi.repository;

import com.akshentsev.mailtrackapi.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {
    Optional<MailEntity> findByTrackingCode(String trackingCode);
}
