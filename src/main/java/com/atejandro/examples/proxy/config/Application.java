package com.atejandro.examples.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(value = "com.atejandro.examples.proxy.*")
@PropertySource("classpath:proxy.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CubeSummationConfig cubeSummationConfig(@Value("${"+ParameterNames.CUBE_CLIENT_URL+"}") String url,
                                                   @Value("${"+ParameterNames.CUBE_CLIENT_PORT+"}") int port){
        return new CubeSummationConfig(url, port);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}