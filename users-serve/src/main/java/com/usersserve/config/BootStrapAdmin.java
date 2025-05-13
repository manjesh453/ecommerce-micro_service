package com.usersserve.config;

import com.userservice.entity.Users;
import com.userservice.repo.UserRepo;
import com.userservice.shared.Role;
import com.userservice.shared.Status;
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
