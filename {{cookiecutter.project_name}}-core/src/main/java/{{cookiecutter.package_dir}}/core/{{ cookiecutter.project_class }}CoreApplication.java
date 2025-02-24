package {{cookiecutter.package_name}}.core;

import {{cookiecutter.package_name}}.integration.{{ cookiecutter.project_class }}IntegrationConfiguration;
import {{cookiecutter.package_name}}.repository.{{ cookiecutter.project_class }}RepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(value = {
    {{ cookiecutter.project_class }}RepositoryConfiguration.class, 
    {{ cookiecutter.project_class }}IntegrationConfiguration.class
})
public class {{cookiecutter.project_class}}CoreApplication {
}
