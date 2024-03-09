package com.traulka.contacttest.controller;

import com.traulka.contacttest.dto.CreatePersonContactRequest;
import com.traulka.contacttest.dto.PersonContactDtoResponse;
import com.traulka.contacttest.dto.UpdatePersonContactRequest;
import com.traulka.contacttest.entity.PersonContact;
import com.traulka.contacttest.mapper.PersonContactMapper;
import com.traulka.contacttest.service.PersonContactService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonContactController {

    PersonContactService personContactService;
    PersonContactMapper personContactMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get a person contact by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payer"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<PersonContactDtoResponse> findById(@PathVariable final Long id) {
        PersonContact personFromDB = personContactService.findById(id);
        return ResponseEntity.ok(personContactMapper.toDto(personFromDB));
    }

    @GetMapping
    @Operation(summary = "Get a person contact list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payer list"),
            @ApiResponse(responseCode = "500", description = "Some paging arg is not correct", content = @Content)
    })
    public ResponseEntity<Page<PersonContactDtoResponse>> findAllPaging(
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "5") final Integer size,
            @RequestParam(required = false, defaultValue = "id") final String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(personContactService.findAll(pageable)
                .map(personContactMapper::toDto));
    }

    @PostMapping
    @Operation(summary = "Register a new person contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concact was registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<PersonContactDtoResponse> createNewPersonContact(
            @Valid @RequestBody final CreatePersonContactRequest requestDto) {
        PersonContact personToSave = personContactMapper.toEntity(requestDto);
        PersonContact savedPerson = personContactService.save(personToSave);
        return ResponseEntity.ok(personContactMapper.toDto(savedPerson));
    }

    @PutMapping
    @Operation(summary = "Update a person contact",
            description = "If contact/address/phone/email not found by id, then a new one will be created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact was updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<PersonContactDtoResponse> changePersonContactData(
            @Valid @RequestBody final UpdatePersonContactRequest requestDto) {
        PersonContact personToSave = personContactMapper.toEntityUpdate(requestDto);
        PersonContact updatedContact = personContactService.save(personToSave);
        return ResponseEntity.ok(personContactMapper.toDto(updatedContact));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person contact by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person was deleted"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public void removeById(@PathVariable final Long id) {
        personContactService.removeById(id);
    }
}
