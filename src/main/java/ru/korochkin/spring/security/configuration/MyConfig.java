package ru.korochkin.spring.security.configuration;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
@Configuration
@ComponentScan("ru.korochkin.spring.security")
@EnableWebMvc
public class MyConfig {

    @Bean
    public ViewResolver viewResolver()  {

        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();

        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;

    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {

            dataSource.setDriverClass("${sql.driver}");
            dataSource.setJdbcUrl("${jdbc.url}" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow");
            dataSource.setUser("${user.name}");
            dataSource.setPassword("${user.pas}");

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

}
