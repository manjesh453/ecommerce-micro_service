package com.usersserve.controller;


import com.usersserve.dto.ChangePasswordDto;
import com.usersserve.dto.UserRequestDto;
import com.usersserve.dto.UserResponseDto;
import com.usersserve.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/update/{userId}")
    public String updateUser(@RequestBody UserRequestDto user, @PathVariable Long userId) {
        return userService.updateUser(user, userId);
    }

    @GetMapping("/delete/{userId}")
    public String deleteUserStatus(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getById/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/changePassword/{email}")
    public String changePassword(@PathVariable String email, @RequestBody ChangePasswordDto dto) {
        return userService.changePassword(email, dto);
    }

    @GetMapping("/findUserByEmail/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/admin/countOfUsers")
    public Map<String,Integer> getAllUserByCount() {
        return userService.getUserByCount();
    }
}
