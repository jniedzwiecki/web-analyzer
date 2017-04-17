package com.jani.webanalyzer.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import static com.jani.webanalyzer.utils.FluentBuilder.with
/**
 * Created by jacekniedzwiecki on 04.04.2017.
 */
@Configuration
@PropertySource("classpath:application.properties")
@CompileStatic
class StorageConfig {

    @Bean
    DataSource dataSource(Environment env) {
        with(new HikariConfig())
                .op { [it.setDriverClassName(env.getRequiredProperty("db.driver")),
                       it.setUsername(env.getRequiredProperty("db.username")),
                       it.setPassword(env.getRequiredProperty("db.password")),
                       it.setJdbcUrl(env.getRequiredProperty("db.url"))
        ]}
        .map { new HikariDataSource(it) }
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        def props = with(new Properties())
                .op { [ it.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect")),
                        it.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto")),
                        it.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy")),
                        it.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql")),
                        it.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"))
        ]}


        with(new LocalContainerEntityManagerFactoryBean())
                .lastOp { [ it.setDataSource(dataSource),
                            it.packagesToScan = 'com.jani.webanalyzer.model.request',
                            it.setJpaVendorAdapter(new HibernateJpaVendorAdapter()),
                            it.setJpaProperties(props.get())
        ]}
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        with(new JpaTransactionManager()).lastOp { it.setEntityManagerFactory(entityManagerFactory) }
    }
}
