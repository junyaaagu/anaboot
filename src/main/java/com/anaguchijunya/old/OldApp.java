package com.anaguchijunya.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class OldApp implements CommandLineRunner {

	@Autowired
	ArgumentResolver resolver;

	@Autowired
	Calculator calculator;

	public static void main(String[] args) {
		SpringApplication.run(OldApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Enter 2 numbers like 'a b ' :");
		Argument argument = resolver.resolve(System.in);
		int result = calculator.calc(argument.getA(), argument.getB());
		System.out.println(result);
	}
}
