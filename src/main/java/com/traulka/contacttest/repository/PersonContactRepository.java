package com.traulka.contacttest.repository;

import com.traulka.contacttest.entity.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonContactRepository extends JpaRepository<PersonContact, Long> {
}
