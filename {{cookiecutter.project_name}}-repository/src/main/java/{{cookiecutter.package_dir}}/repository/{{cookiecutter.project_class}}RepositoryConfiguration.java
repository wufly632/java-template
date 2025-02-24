package {{cookiecutter.package_name}}.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jeff.yu
 */
@Configuration
@ComponentScan
@PropertySource("classpath:{{cookiecutter.project_name}}-repository.properties")
@MapperScan(basePackages = "{{cookiecutter.package_name}}.repository.mybatis.mapper,{{cookiecutter.package_name}}.repository.mybatis.hologresmapper")

public class {{cookiecutter.project_class}}RepositoryConfiguration {
    
}
