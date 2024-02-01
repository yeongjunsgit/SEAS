package com.ssafy.seas.config;

import jakarta.xml.bind.DataBindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:seas-config.yml")
public class DBConfiguration {
    @Autowired
    private Environment env;
    private String DB_DRIVER;
    private String DB_URL;
    private String DB_HOST;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    private String DB_PORT;
    private String DB_NAME;

    @Bean
    public DataSource datasource() {
        try {

            DB_HOST = env.getProperty("DB_HOST");
            DB_PORT = env.getProperty("DB_PORT");
            DB_NAME = env.getProperty("DB_NAME");
            DB_DRIVER = env.getProperty("DB_DRIVER");
            DB_USERNAME = env.getProperty("DB_USERNAME");
            DB_PASSWORD = env.getProperty("DB_PASSWORD");

            DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";

            return DataSourceBuilder.create()
                    .driverClassName(DB_DRIVER)
                    .url(DB_URL)
                    .username(DB_USERNAME)
                    .password(DB_PASSWORD)
                    .build();

        } catch (Exception e) {
            System.out.println("CAUSE : " + e.getCause());
            System.out.println("MESSAGE : " + e.getMessage());
            System.out.println("TRACE : " + Arrays.toString(e.getStackTrace()));

            throw new DataBindingException(new Throwable(e.getMessage()));
        }
    }
}