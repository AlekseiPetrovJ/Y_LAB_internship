package ru.petrov.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.petrov.repository.TypeOfValueRepository;
import ru.petrov.repository.UserRepository;
import ru.petrov.util.mapper.LogRowMapper;
import ru.petrov.util.mapper.MeasurementRowMapper;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

    private final Environment environment;

    @Autowired
    public WebConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver")));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.db_user"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LogRowMapper logMapper(UserRepository userRepository){
        return new LogRowMapper(userRepository);
    }

    @Bean
    public MeasurementRowMapper measurementMapper(UserRepository userRepository, TypeOfValueRepository typeOfValueRepository){
        return new MeasurementRowMapper(userRepository, typeOfValueRepository);
    }
}