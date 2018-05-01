package com.cherkasov;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Sets the Hibernate properties and beans for JPA and EntityManager.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@Profile({"prod"})
@PropertySource(value = {"classpath:db.properties"})
public class HibernateConfig {

  private static final String PACKAGE_FOR_ENTITY = "com.cherkasov";

  @Autowired
  private Environment environment;

  @Bean
  public DataSource configureDataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
    config.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
    config.setUsername(environment.getRequiredProperty("jdbc.username"));
    config.setPassword(environment.getRequiredProperty("jdbc.password"));

    log.trace("DataSource configured with Hikari");
    return new HikariDataSource(config);
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
    properties.put("hibernate.jdbc.fetch_size", environment.getRequiredProperty("hibernate.jdbc.fetch_size"));
    properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

    factoryBean.setDataSource(dataSource);

    //scan for beans with @Entity classes
    factoryBean.setPackagesToScan(PACKAGE_FOR_ENTITY);
    factoryBean.setPersistenceUnitName("RegisterStore");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(true);

    factoryBean.setJpaVendorAdapter(vendorAdapter);
    factoryBean.setJpaProperties(hibernateProperties());

    return factoryBean;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}

