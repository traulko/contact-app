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
@Table(name = "PERSON_EMAIL")
@Builder(setterPrefix = "set")
public class PersonEmail extends PersonContactData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "EMAIL")
    String email;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    PersonContact contact;
}
