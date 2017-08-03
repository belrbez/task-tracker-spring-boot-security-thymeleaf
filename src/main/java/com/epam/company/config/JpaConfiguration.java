package com.epam.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
/*@EnableTransactionManagement
@EnableAsync
@PropertySource(value = {"classpath:application.yml"})*/
public class JpaConfiguration {

	@Autowired
	private Environment environment;
/*
	@Bean
	public DataSource dataSource() {
		*//*DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;*//*

		DataSource dataSource = new BasicDataSource();
		dataSource.setUrl(environment.getRequiredProperty("db.url"));
		dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
		dataSource.setUsername(environment.getRequiredProperty("db.username"));
		dataSource.setPassword(environment.getRequiredProperty("db.password"));

		dataSource.setInitialSize(Integer.valueOf(environment.getRequiredProperty("db.initialSize")));
		dataSource.setMinIdle(Integer.valueOf(environment.getRequiredProperty("db.minIdle")));
		dataSource.setMaxIdle(Integer.valueOf(environment.getRequiredProperty("db.maxIdle")));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(environment.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(environment.getRequiredProperty("db.minEvictableIdleTimeMillis")));
		dataSource.setTestOnBorrow(Boolean.valueOf(environment.getRequiredProperty("db.testOnBorrow")));
		dataSource.setValidationQuery(environment.getRequiredProperty("db.validationQuery"));

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.websystique.springmvc.model" });
		sessionFactory.setHibernateProperties(jpaProperties());
		return sessionFactory;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.devtrustcorp.company_demo" });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	*//*
	 * Provider specific adapter.
	 *//*
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}
*/
	/*
	 * Here you can specify any provider specific properties.
	 */
	@Bean
	public Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		return properties;
	}

	/*private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		return properties;
	}*/

	/*@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}*/

}
