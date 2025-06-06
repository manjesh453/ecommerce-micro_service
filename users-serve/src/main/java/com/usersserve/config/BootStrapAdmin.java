package com.usersserve.config;


import com.usersserve.entity.Users;
import com.usersserve.repo.UserRepo;
import com.usersserve.shared.Role;
import com.usersserve.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class BootStrapAdmin implements CommandLineRunner {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            Users user = Users.builder()
                    .firstname("Super")
                    .lastname("Admin")
                    .email("superadmin@gmail.com")
                    .fullname("Super Admin")
                    .lastPasswordChanged(new Date())
                    .password(passwordEncoder.encode("superadmin@1234"))
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .contactNumber("9999999999")
                    .address("UK")
                    .lastPasswordChanged(new Date())
                    .build();

            userRepository.save(user);
        }
    }
}
