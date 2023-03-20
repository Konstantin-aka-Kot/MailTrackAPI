package com.akshentsev.mailtrackapi.entity;

import com.akshentsev.mailtrackapi.model.MailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mail")
public class MailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailType type;
    private String recipientName;
    private String recipientAddress;
    @ManyToOne
    @JoinColumn(name = "post_office_id")
    private PostOfficeEntity recipientOffice;

    private String trackingCode;
}
