package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MyAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAccountApplication.class, args);
	}
}

@RestController
class MessageRestController {

	private final RestTemplate restTemplate;
	
	@Value("${person.service.url}")
	private String myURL;

	MessageRestController(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@RequestMapping("/message/{personId}")
	String getMessage(@PathVariable("personId") Long personId) {
		System.out.println("*** myURL [" + myURL + "]");
		System.out.println("*** myURL replaced [" + myURL.replaceAll("\"", "") + "]");
		Person person = this.restTemplate.getForObject(myURL.replaceAll("\"", "") + "/person/{personId}", Person.class, personId);
		return "Hello " + person.getName() + " " + person.getSurname();
	}

}
