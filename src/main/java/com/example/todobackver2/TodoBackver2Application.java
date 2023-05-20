package com.example.todobackver2;

import com.example.todobackver2.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TodoBackver2Application {

    public static void main(String[] args) {
        SpringApplication.run(TodoBackver2Application.class, args);
    }

//
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        rabbitTemplate.setExchange(topicExchangeName);
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext stringApplicationContext(){
        return  new SpringApplicationContext();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties(){
        return  new AppProperties();
    }
}
