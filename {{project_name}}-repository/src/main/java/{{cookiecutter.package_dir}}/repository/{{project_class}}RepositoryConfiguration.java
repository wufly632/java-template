package {{package_name}}.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jeff.yu
 */
@Configuration
@ComponentScan
@PropertySource("classpath:{{project_name}}-repository.properties")
@MapperScan(basePackages = "{{package_name}}.repository.mybatis.mapper,{{package_name}}.repository.mybatis.hologresmapper")

public class {{project_class}}RepositoryConfiguration {
    
}
