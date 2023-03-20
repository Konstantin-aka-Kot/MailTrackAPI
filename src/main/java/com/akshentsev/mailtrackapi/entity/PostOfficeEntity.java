package com.akshentsev.mailtrackapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "post_offices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOfficeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;
}
