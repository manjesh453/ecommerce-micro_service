package com.usersserve.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String status;
    private String contactNumber;
    private String address;
}
