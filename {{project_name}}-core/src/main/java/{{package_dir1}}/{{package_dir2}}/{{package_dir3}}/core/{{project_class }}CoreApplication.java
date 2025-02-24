package {{package_name}}.core;

import {{package_name}}.integration.{{ project_class }}IntegrationConfiguration;
import {{package_name}}.repository.{{ project_class }}RepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(value = {
    {{ project_class }}RepositoryConfiguration.class, 
    {{ project_class }}IntegrationConfiguration.class
})
public class {{project_class}}CoreApplication {
}
