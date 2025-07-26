package com.example.taskmanager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.taskmanager.db.seeds.UserSeeder;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataSeederConfig implements CommandLineRunner {

    @Autowired
    private UserSeeder userSeeder;

    @Override
    public void run(String... args) {
        System.out.println("⏳ Seeding data...");

        userSeeder.seed();

        System.out.println("✅ Done seeding.");
    }
}
