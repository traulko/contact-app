package com.traulka.contacttest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PHONE_NUMBER")
public class PhoneNumber extends PersonContactData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "PHONE")
    String phone;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    PersonContact contact;
}
