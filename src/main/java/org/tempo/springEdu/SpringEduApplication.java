package org.tempo.springEdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:app.properties")
        ,@PropertySource(value = "classpath:app.local.properties",
                ignoreResourceNotFound = true)
})
public class SpringEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEduApplication.class, args);
    }

}
