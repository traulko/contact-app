package com.traulka.contacttest.service;

import com.traulka.contacttest.dto.CreatePersonContactRequest;
import com.traulka.contacttest.entity.PersonContact;
import com.traulka.contacttest.entity.PersonEmail;
import com.traulka.contacttest.exception.DataNotFoundException;
import com.traulka.contacttest.repository.PersonContactRepository;
import com.traulka.contacttest.service.impl.PersonContactServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class PersonContactServiceTest {

    @InjectMocks
    PersonContactServiceImpl service;

    @Mock
    PersonContactRepository repository;

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    public void findById_successful(Long contactId) {
        when(repository.findById(contactId)).thenReturn(Optional.of(PersonContact.builder().setId(contactId).build()));

        PersonContact personContact = service.findById(contactId);

        verify(repository, times(1)).findById(contactId);
        assertEquals(contactId, personContact.getId());
    }

    @Test
    public void findById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        verify(repository, times(0)).findById(1L);
        assertThrows(DataNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void findAll_successful() {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("id"));

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(PersonContact.builder().setId(1L).build(),
                        PersonContact.builder().setId(2L).build())));

        Page<PersonContact> measurementDtoPage = service.findAll(pageable);

        verify(repository, times(1)).findAll(pageable);
        assertEquals(measurementDtoPage.getTotalElements(), 2);
        assertEquals(measurementDtoPage.getTotalPages(), 1);
    }


    @Test
    public void createNewContact_successful() {
        PersonContact saved = PersonContact.builder()
                .setId(1L)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonEmail.builder()
                        .setId(1L)
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();


        when(repository.save(saved)).thenReturn(saved);

        PersonContact result = service.save(saved);

        verify(repository, times(1)).save(saved);
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void removeById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        verify(repository, times(0)).findById(1L);
        assertThrows(DataNotFoundException.class, () -> service.removeById(1L));
    }

}