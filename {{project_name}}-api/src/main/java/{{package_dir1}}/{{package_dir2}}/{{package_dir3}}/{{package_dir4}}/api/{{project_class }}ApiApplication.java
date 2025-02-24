package {{package_name}}.api;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import {{package_name}}.core.{{ project_class }}CoreApplication;
import {{package_name}}.integration.{{ project_class }}IntegrationConfiguration;
import {{package_name}}.repository.{{ project_class }}RepositoryConfiguration;
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
    {{ project_class }}RepositoryConfiguration.class,
    {{ project_class }}IntegrationConfiguration.class,
    {{ project_class }}CoreApplication.class 
})
@PropertySource("classpath:env-application.properties")
@ComponentScan(basePackages = "com.coraool")
@EnableDubbo(scanBasePackages = {"{{package_name}}.core"})
@EnableScheduling
@NacosPropertySource(dataId = "nacos.coraool.{{nacos_data_id}}", groupId = "nacos.config", autoRefreshed = true)
public class {{ project_class }}ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run({{ project_class }}ApiApplication.class, args);
    }

}