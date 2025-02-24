package {{cookiecutter.package_name}}.api;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import {{cookiecutter.package_name}}.core.{{ cookiecutter.project_class }}CoreApplication;
import {{cookiecutter.package_name}}.integration.{{ cookiecutter.project_class }}IntegrationConfiguration;
import {{cookiecutter.package_name}}.repository.{{ cookiecutter.project_class }}RepositoryConfiguration;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@SpringBootApplication
@ServletComponentScan
@Import(value = { 
    {{ cookiecutter.project_class }}RepositoryConfiguration.class,
    {{ cookiecutter.project_class }}IntegrationConfiguration.class,
    {{ cookiecutter.project_class }}CoreApplication.class 
})
@PropertySource("classpath:env-application.properties")
@ComponentScan(basePackages = "com.coraool")
@EnableDubbo(scanBasePackages = {"{{cookiecutter.package_name}}.core"})
@EnableScheduling
@NacosPropertySource(dataId = "nacos.coraool.{{ cookiecutter.project_name.lower().replace('-', '.') }}", groupId = "nacos.config", autoRefreshed = true)
public class {{ cookiecutter.project_class }}ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run({{ cookiecutter.project_class }}ApiApplication.class, args);
    }

}