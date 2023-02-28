package com.arims.web.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String gender;
    private String phone;





}
