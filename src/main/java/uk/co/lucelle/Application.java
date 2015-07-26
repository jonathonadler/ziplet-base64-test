package uk.co.lucelle;

import java.util.Arrays;

import com.github.ziplet.filter.compression.CompressingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean () {
        CompressingFilter compressingFilter = new CompressingFilter();

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("debug", "true");
        registrationBean.addInitParameter("compressionThreshold", "0");

        registrationBean.setFilter(compressingFilter);
        return registrationBean;
    }
}
