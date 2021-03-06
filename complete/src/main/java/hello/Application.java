package hello;

import static reactor.event.selector.Selectors.$;

import java.util.concurrent.CountDownLatch;

import org.springframework.autoconfigure.EnableAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.bootstrap.CommandLineRunner;
import org.springframework.bootstrap.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import reactor.core.Reactor;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application implements CommandLineRunner {
	
	@Autowired
	private Reactor reactor;
	
	@Autowired
	private Receiver receiver;
	
	@Autowired
	private Publisher publisher;
	
	@Bean
	Integer numberOfJokes() {
		return 10;
	}
	
	@Bean
	public CountDownLatch latch(Integer numberOfJokes) {
		return new CountDownLatch(numberOfJokes);
	}
	
	@Override
	public void run(String... args) throws Exception {		
		reactor.on($("jokes"), receiver);
		publisher.publishJokes();
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
	}

}
