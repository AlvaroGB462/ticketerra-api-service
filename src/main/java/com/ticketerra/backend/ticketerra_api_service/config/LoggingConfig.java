package com.ticketerra.backend.ticketerra_api_service.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

@Configuration
public class LoggingConfig {

    @Bean
    public Logger configureLogger() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("com.ticketerra.backend");

        // Establecer el nivel de log
        logger.setLevel(Level.DEBUG);

        // Configurar formato del log
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        // Configurar rotación de logs
        RollingFileAppender fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setFile("logs/ticketerra.log");

        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern("logs/ticketerra-%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(7);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.start();

        fileAppender.setEncoder(encoder);
        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();

        logger.addAppender(fileAppender);

        return logger;
    }
}
