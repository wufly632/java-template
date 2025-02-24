package {{package_name}}.core;

import {{package_name}}.integration.{{ cookiecutter.project_class }}IntegrationConfiguration;
import {{package_name}}.repository.{{ cookiecutter.project_class }}RepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(value = {
    {{ cookiecutter.project_class }}RepositoryConfiguration.class, 
    {{ cookiecutter.project_class }}IntegrationConfiguration.class
})
public class {{project_class}}CoreApplication {
}
