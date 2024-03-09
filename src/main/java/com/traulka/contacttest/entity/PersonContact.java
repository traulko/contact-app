package com.traulka.contacttest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PERSON_CONTACT")
@Builder(setterPrefix = "set")
public class PersonContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "DATE_OF_BIRTH")
    LocalDate dateOfBirth;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    Set<PersonAddress> addresses;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    Set<PersonEmail> emails;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    Set<PhoneNumber> phones;
}
