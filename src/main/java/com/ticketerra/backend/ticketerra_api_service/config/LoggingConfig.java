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

    @Bean // Define un bean que será gestionado por Spring
    public Logger configureLogger() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory(); // Obtiene el contexto de Logger
        Logger logger = context.getLogger("com.ticketerra.backend"); // Crea un logger para el paquete especificado

        // Establecer el nivel de log (DEBUG, INFO, WARN, ERROR, etc.)
        logger.setLevel(Level.DEBUG);

        // Configurar el formato del log
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"); // Define el formato del log
        encoder.start(); // Inicia el encoder

        // Configurar la rotación de logs (archivos que se crean diariamente)
        RollingFileAppender fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setFile("logs/ticketerra.log"); // Archivo de log principal

        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern("logs/ticketerra-%d{yyyy-MM-dd}.log"); // Patrón para los archivos de log rotados
        rollingPolicy.setMaxHistory(7); // Mantener logs de los últimos 7 días
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.start(); // Inicia la política de rotación

        fileAppender.setEncoder(encoder); // Asigna el encoder al appender
        fileAppender.setRollingPolicy(rollingPolicy); // Asigna la política de rotación al appender
        fileAppender.start(); // Inicia el appender

        logger.addAppender(fileAppender); // Asocia el appender al logger

        return logger; // Devuelve el logger configurado
    }
}