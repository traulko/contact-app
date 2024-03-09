package com.traulka.contacttest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePersonContactRequest {
    Long id;
    @Size(min = 2, max = 15, message = "firstName length must be between 2 and 15")
    @NotNull(message = "firstName must not be null")
    String firstName;
    @Size(min = 2, max = 15, message = "lastName length must be between 2 and 15")
    String lastName;
    LocalDate dateOfBirth;
    Set<CreatePersonAddressRequest> addresses;
    Set<PhoneNumberRequest> phones;
    Set<PersonEmailRequest> emails;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreatePersonAddressRequest {
        Long id;
        @Size(min = 2, max = 15, message = "city length must be between 2 and 15")
        String city;
        @Size(min = 2, max = 15, message = "street length must be between 2 and 15")
        String street;
        @Size(min = 2, max = 15, message = "postcode length must be between 2 and 15")
        String postcode;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PhoneNumberRequest {
        Long id;
        Boolean isPrimary;
        @Size(min = 2, max = 15, message = "phone length must be between 2 and 15")
        String phone;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PersonEmailRequest {
        Long id;
        Boolean isPrimary;
        @Size(min = 2, max = 15, message = "email length must be between 2 and 15")
        @Email
        String email;
    }
}
