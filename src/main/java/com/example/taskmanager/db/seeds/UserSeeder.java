package com.example.taskmanager.db.seeds;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class UserSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void seed() {
        long count = userRepository.count();
        System.out.println("Seeding users. Number user: " + count);

        if (count == 0) {
            String passwordHash = passwordEncoder.encode("password");

            User user = new User();
            user.setName("user1");
            user.setPasswordHash(passwordHash);
            user.setEmail("user@gmail.com");

            userRepository.save(user);
        }
    }
}
