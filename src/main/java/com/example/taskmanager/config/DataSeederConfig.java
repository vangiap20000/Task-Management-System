package com.example.taskmanager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.taskmanager.db.seeds.UserSeeder;

@Component
public class DataSeederConfig implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("⏳ Seeding data...");

        UserSeeder userSeeder = new UserSeeder();
        userSeeder.seed();

        System.out.println("✅ Done seeding.");
    }
}
