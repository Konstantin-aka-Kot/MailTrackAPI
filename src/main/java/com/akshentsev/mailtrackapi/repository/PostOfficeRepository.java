package com.akshentsev.mailtrackapi.repository;

import com.akshentsev.mailtrackapi.entity.PostOfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOfficeEntity, Long> {
    Optional<PostOfficeEntity> findByPostalCode(String postalCode);
}
