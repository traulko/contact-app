package com.traulka.contacttest.mapper;

import com.traulka.contacttest.dto.CreatePersonContactRequest;
import com.traulka.contacttest.dto.PersonContactDtoResponse;
import com.traulka.contacttest.dto.UpdatePersonContactRequest;
import com.traulka.contacttest.entity.PersonContact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonContactMapper {
    PersonContactDtoResponse toDto(PersonContact personContact);

    PersonContact toEntity(CreatePersonContactRequest createPersonContactRequest);
    PersonContact toEntityUpdate(UpdatePersonContactRequest createPersonContactRequest);
}