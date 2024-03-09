package com.traulka.contacttest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(setterPrefix = "set")
public class PersonContactDtoResponse {
    Long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    Set<PersonAddressDtoResponse> addresses;
    Set<PersonEmailDtoResponse> emails;
    Set<PhoneNumberDtoResponse> phones;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PersonAddressDtoResponse {
        Long id;
        String city;
        String street;
        String postcode;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(setterPrefix = "set")
    public static class PersonEmailDtoResponse {
        Long id;
        Boolean isPrimary;
        String email;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PhoneNumberDtoResponse {
        Long id;
        Boolean isPrimary;
        String phone;
    }
}
