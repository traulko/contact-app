package com.traulka.contacttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traulka.contacttest.dto.CreatePersonContactRequest;
import com.traulka.contacttest.dto.PersonContactDtoResponse;
import com.traulka.contacttest.entity.PersonContact;
import com.traulka.contacttest.entity.PersonEmail;
import com.traulka.contacttest.exception.DataNotFoundException;
import com.traulka.contacttest.mapper.PersonContactMapper;
import com.traulka.contacttest.service.PersonContactService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonContactController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class PersonContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonContactService service;

    @MockBean
    PersonContactMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturn200CodeWhenContactSuccessfullyFound() throws Exception {
        PersonContact contact = PersonContact.builder()
                .setId(1L)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonEmail.builder()
                        .setId(1L)
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        PersonContactDtoResponse response = PersonContactDtoResponse.builder()
                .setId(1L)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonContactDtoResponse.PersonEmailDtoResponse.builder()
                        .setId(1L)
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        when(service.findById(1L)).thenReturn(contact);

        when(mapper.toDto(contact)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contact/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.emails.length()").value(1));
    }

    @Test
    public void shouldReturn404CodeWhenContactNotFound() throws Exception {
        when(service.findById(1L)).thenThrow(DataNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contact/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200CodeWhenFindAllContacts() throws Exception {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("id"));

        PersonContact contact1 = PersonContact.builder()
                .setId(1L)
                .build();

        PersonContact contact2 = PersonContact.builder()
                .setId(2L)
                .build();

        PersonContactDtoResponse response1 = PersonContactDtoResponse.builder()
                .setId(1L)
                .build();

        PersonContactDtoResponse response2 = PersonContactDtoResponse.builder()
                .setId(2L)
                .build();

        when(service.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(
                contact1, contact2)));

        when(mapper.toDto(contact1)).thenReturn(response1);
        when(mapper.toDto(contact2)).thenReturn(response2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contact")
                        .param("page", String.valueOf(1))
                        .param("size", String.valueOf(5))
                        .param("sort", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    public void shouldReturn200CreateNewContactSuccessfully() throws Exception {
        CreatePersonContactRequest contact = CreatePersonContactRequest.builder()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(CreatePersonContactRequest.PersonEmailRequest.builder()
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        PersonContact contactAfterMapping = PersonContact.builder()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonEmail.builder()
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        PersonContact saved = PersonContact.builder()
                .setId(1L)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonEmail.builder()
                        .setId(1L)
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        PersonContactDtoResponse response = PersonContactDtoResponse.builder()
                .setId(1L)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmails(Set.of(PersonContactDtoResponse.PersonEmailDtoResponse.builder()
                        .setId(1L)
                        .setEmail("1234@gmail.com")
                        .build()))
                .build();

        when(mapper.toEntity(contact)).thenReturn(contactAfterMapping);

        when(service.save(contactAfterMapping)).thenReturn(saved);

        when(mapper.toDto(saved)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.emails.length()").value(1));
    }

    @Test
    public void shouldReturn400CreateNewContactWithoutRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}