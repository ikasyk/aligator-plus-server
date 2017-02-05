package com.aligatorplus.db;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Project AligatorPlus
 * Created by igor, 01.02.17 16:42
 */

@Configuration
@PropertySource("classpath:config/app.properties")
@EnableTransactionManagement
@EnableJpaRepositories("com.aligatorplus.db.repository")
public class JPAConfig {
    private static final String PROP_DATABASE_DRIVER = "app.db.driver";
    private static final String PROP_DATABASE_URL = "app.db.url";
    private static final String PROP_DATABASE_USERNAME = "app.db.username";
    private static final String PROP_DATABASE_PASSWORD = "app.db.password";
    private static final String PROP_HIBERNATE_DIALECT = "app.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOWSQL = "app.hibernate.show_sql";
    private static final String PROP_HIBERNATE_FORMATSQL = "app.hibernate.format_sql";
    private static final String PROP_HIBERNATE_AUTO = "app.hibernate.auto";

    private static final String PACKAGES_TO_SCAN = "com.aligatorplus.model";

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(getDataSource());
        em.setJpaProperties(getHibernateProperties());

        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setPackagesToScan(PACKAGES_TO_SCAN);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());

        return tm;
    }

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put("hibernate.show_sql", env.getRequiredProperty(PROP_HIBERNATE_SHOWSQL));
        properties.put("hibernate.format_sql", env.getRequiredProperty(PROP_HIBERNATE_FORMATSQL));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty(PROP_HIBERNATE_AUTO));
        properties.put("hibernate.enable_lazy_load_no_trans", true);
        return properties;
    }
}
