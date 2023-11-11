package pl.jewusiak.mwotodomvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MwoTodoMvcApplication {

    @Value("${api.url}")
    private String apiUrl;

    public static void main(String[] args) {
        SpringApplication.run(MwoTodoMvcApplication.class, args);
    }


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(apiUrl).build();
	}

}
