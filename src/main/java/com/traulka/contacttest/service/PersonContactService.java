package com.traulka.contacttest.service;

import com.traulka.contacttest.entity.PersonContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonContactService {
    PersonContact findById(Long id);

    PersonContact save(PersonContact personContact);

    void removeById(Long id);

    Page<PersonContact> findAll(Pageable pageable);
}
