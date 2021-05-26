package pl.jnp2.zad1;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Zad1Router extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("timer:scheduler?period={{check.interval}}")
			.setHeader(Exchange.HTTP_METHOD).constant("GET")
			.to("https://api.urbandictionary.com/v0/random")
			.process(new Zad1Processor())
			.setHeader("to", simple("{{mail.list}}"))
			.setHeader("Content-Type", constant("text/plain"))
			.to("smtps://smtp.gmail.com?username={{mail.user}}&password={{mail.password}}&subject=Word of the day")
			.log("Word of the day sent.");
	}
}
