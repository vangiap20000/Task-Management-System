package com.example.taskmanager.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.beans.factory.annotation.Value;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Value("${file.upload-dir}")
//     private String uploadDir;

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry.addResourceHandler("/uploads/**")
//                 .addResourceLocations("file:///media/giappc/01D5ACCB78B5DA20/Task-Management-System/uploads/");
//     }
// }



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/media/giappc/01D5ACCB78B5DA20/Task-Management-System/uploads")
                .setCachePeriod(0);
    }
}