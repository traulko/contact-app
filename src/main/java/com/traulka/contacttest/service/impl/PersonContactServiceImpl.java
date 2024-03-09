package com.traulka.contacttest.service.impl;

import com.traulka.contacttest.entity.PersonContact;
import com.traulka.contacttest.exception.DataNotFoundException;
import com.traulka.contacttest.repository.PersonContactRepository;
import com.traulka.contacttest.service.PersonContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonContactServiceImpl implements PersonContactService {

    PersonContactRepository repository;

    @Override
    @Transactional(readOnly = true)
    public PersonContact findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Person concat not found"));
    }

    @Override
    @Transactional
    public PersonContact save(PersonContact personContact) {
        prepareToSave(personContact);
        return repository.save(personContact);
    }

    private void prepareToSave(PersonContact personContact) {
        if (personContact.getAddresses() != null) {
            personContact.getAddresses().forEach(address -> address.setContact(personContact));
        }
        if (personContact.getEmails() != null) {
            personContact.getEmails().forEach(email -> email.setContact(personContact));
        }
        if (personContact.getPhones() != null) {
            personContact.getPhones().forEach(phoneNumber -> phoneNumber.setContact(personContact));
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        PersonContact contactToRemove = findById(id);
        repository.delete(contactToRemove);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonContact> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
