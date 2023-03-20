package com.akshentsev.mailtrackapi.entity;

import com.akshentsev.mailtrackapi.model.MailStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mail_history")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mail_item_id", nullable = false)
    private MailEntity mailEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_office_id", nullable = false)
    private PostOfficeEntity postOffice;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailStatus status;
}
