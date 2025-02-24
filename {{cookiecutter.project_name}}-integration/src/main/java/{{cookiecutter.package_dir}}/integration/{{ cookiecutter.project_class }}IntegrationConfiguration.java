package {{cookiecutter.package_name}}.integration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@Configuration
@PropertySource("classpath:{cookiecutter.slug}-integration.properties")
public class {{ cookiecutter.project_class }}IntegrationConfiguration {
}
