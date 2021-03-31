package com.copy.reddit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${app.path.upload.img}")
    private String pathUploadImg;

    /**
     * Настраивает статические папки
     * при запросе /img/** ищутся файлы в папке ${app.path.upload.img}
     * при запросе /** ищет в папке static
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectDir = System.getProperty("user.dir");
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:/" + projectDir + "/" + pathUploadImg + "/");

        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
