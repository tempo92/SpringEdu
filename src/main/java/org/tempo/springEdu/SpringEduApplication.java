package org.tempo.springEdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource(value = "classpath:application.local.properties", ignoreResourceNotFound = true)
})
public class SpringEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEduApplication.class, args);
    }

}
