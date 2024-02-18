package ru.petrov.config;

import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.mapper.LogMapper;
import ru.petrov.util.mapper.MeasurementMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.yml")
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

    private final Environment environment;

    @Autowired
    public WebConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("db_user"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:db/changelog/changelog.xml");
        return liquibase;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LogMapper logMapper(UserRepository userRepository){
        return new LogMapper(userRepository);
    }

    @Bean
    public MeasurementMapper measurementMapper(UserRepository userRepository, TypeOfValueRepository typeOfValueRepository){
        return new MeasurementMapper(userRepository, typeOfValueRepository);
    }
}