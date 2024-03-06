package com.example.pmbakanov.configurations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Конфигурация планировщика заданий.
 * Этот класс конфигурации активирует планировщик заданий Spring и условно включает его
 * в зависимости от настройки "jobs.enabled" в файле application.properties.
 * По умолчанию планировщик заданий включен, если свойство не установлено.
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "jobs.enabled", matchIfMissing = true, havingValue = "true")
public class SchedulerConfig {

}
