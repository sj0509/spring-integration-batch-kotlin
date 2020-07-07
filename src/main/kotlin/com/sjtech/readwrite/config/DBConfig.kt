package com.sjtech.readwrite.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@Component
@EnableJpaRepositories(
        entityManagerFactoryRef = "serviceEntityManagerFactory",
        basePackages = ["com.sjtech.readwrite.db.repository"]
)
class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties("service.datasource.trade")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["serviceDataSource"])
    @Primary
    fun dataSource(): DataSource {
        val password: String = System.getProperty("DB_PWD")
        return hikariDataSource(password)
    }

    private fun hikariDataSource(password: String): DataSource {
        val hikariDataSource = dataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource::class.java).password(password)
                .build() as HikariDataSource

        hikariDataSource.connectionTestQuery = "SELECT 1"
        return hikariDataSource
    }

    @Bean
    @Qualifier("jpaTransactionManager")
    fun jpaTransactionManager(): PlatformTransactionManager? {
        //Try the first one if the second one doesnt work
//        return JpaTransactionManager(serviceEntityManagerFactory(null).`object`)
        return serviceEntityManagerFactory(null).`object`?.let { JpaTransactionManager(it) }
    }

    @Bean(name = ["serviceEntityManagerFactory"])
    fun serviceEntityManagerFactory(builder: EntityManagerFactoryBuilder?): LocalContainerEntityManagerFactoryBean {
        return builder!!
                .dataSource(dataSource())
                .packages("com.sjtech.readwrite.db.entity")
                .build()
    }
}