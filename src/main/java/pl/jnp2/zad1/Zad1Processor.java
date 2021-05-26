package pl.jnp2.zad1;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.springframework.stereotype.Component;

import java.lang.*;

import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class Zad1Processor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String content = exchange.getIn().getBody(String.class);

		Pattern definitionRegex = Pattern.compile("\"definition\":\"([^\"]+)\"");
		Matcher definitionMatcher = definitionRegex.matcher(content);

		Pattern wordRegex = Pattern.compile("\"word\":\"([^\"]+)\"");
		Matcher wordMatcher = wordRegex.matcher(content);

		boolean definitionFound = definitionMatcher.find();
		boolean wordFound = wordMatcher.find();

		if (!definitionFound || !wordFound) {
			exchange.getIn().setBody("There was an unexpected error.");
			return;
		}

		String result =
			"Word of the day: " +
			wordMatcher.group(1) +
			".\nDefinition: " +
			definitionMatcher.group(1);

		Pattern bracketRegex = Pattern.compile("[\\[\\]]");
		Matcher bracketMatcher = bracketRegex.matcher(result);

		result = bracketMatcher.replaceAll("");

		exchange.getIn().setBody(result);
    }
}
