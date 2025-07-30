package com.example.taskmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "uploads";
        Path uploadPhotoDir = Paths.get(location);

        String uploadPhotoPath = uploadPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + location + "/**")
                .addResourceLocations("file:" + uploadPhotoPath + "/");
    }
}


