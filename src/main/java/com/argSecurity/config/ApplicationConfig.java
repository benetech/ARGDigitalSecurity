/**
 * Loads the beans, properties, spring secufity xml file  for the application
 * 
 * and load all the database configuration for the app
 * @author daniela.depablos
 *
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity.config;

import java.sql.Date;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.argSecurity.utility.DozerUtil;


@Configuration
@ComponentScan(basePackageClasses = ApplicationConfig.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class))
@ImportResource(value = { "classpath:META-INF/applicationContextSecurity.xml" })
@EnableJpaRepositories(basePackages="com.argSecurity.repository")
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationConfig {

	@Autowired
	private Environment environment;
  /**
   * datasource for the benetech app	
   * @return
   */
  @Bean
  public DataSource dataSource() {
	  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment
				.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource
				.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource
				.setPassword(environment.getRequiredProperty("jdbc.password"));
		
		
		return dataSource;
  }

  /**
   * Bean definition for the EntityManagerFactory
   * @return EntityManagerFactory bean
   */
  @Bean
  //@PersistenceContext(unitName="bigbrother", type=PersistenceContextType.EXTENDED)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.POSTGRESQL);
    vendorAdapter.setGenerateDdl(true);
    
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.argSecurity.model");
    factory.setDataSource(dataSource());
    

    return factory;
  }

  /**
   * Bean definition for the Spring Transaction Manager
   * @return TransactionManager bean
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return txManager;
  }
  
  /**
   * mapper to convert dto vs model
   * @return
   */
  @Bean(name="dozerMapper")
  public DozerBeanMapper dozerMapper(){
	  DozerBeanMapper mapper = DozerUtil.getInstance();
	  return mapper;
  }
  /**
   * handling instance date of the system
   * @return
   */
  @Bean(name="dateSystem")
  public Date dateSystem(){
	  return new Date(System.currentTimeMillis());
  }
  
	
}
