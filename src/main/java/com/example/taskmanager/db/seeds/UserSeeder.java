package com.example.taskmanager.db.seeds;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seed() {
        if (userRepository.count() == 0) {
            String passwordHash = passwordEncoder.encode("password");

            User user = new User();
            user.setName("user1");
            user.setPasswordHash(passwordHash);
            user.setEmail("user@gmail.com");

            userRepository.save(user);
        }
    }
}
