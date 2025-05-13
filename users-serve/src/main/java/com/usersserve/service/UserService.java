package com.usersserve.service;



import com.usersserve.dto.ChangePasswordDto;
import com.usersserve.dto.UserRequestDto;
import com.usersserve.dto.UserResponseDto;
import com.usersserve.entity.Users;
import com.usersserve.shared.Status;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    String updateUser(UserRequestDto userRequestDto, Long userId);

    UserResponseDto getUserById(Long userId);

    UserResponseDto getUserByEmail(String email);

    String deleteUser(Long userId);

    List<UserResponseDto> getAllUsers();

    String changeStatusToActive(Long cid);

    List<UserResponseDto> findByStatus(Status status);

    List<UserResponseDto> findUserByTime(Date startDate, Date endDate);

    void sendVerification(Users customer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String code);

    String changePassword(String email, ChangePasswordDto request);

    Map<String,Integer> getUserByCount();
}
