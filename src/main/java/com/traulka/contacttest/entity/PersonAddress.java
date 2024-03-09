package com.traulka.contacttest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PERSON_ADDRESS")
public class PersonAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "CITY")
    String city;

    @Column(name = "STREET")
    String street;

    @Column(name = "POSTCODE")
    String postcode;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    PersonContact contact;
}
