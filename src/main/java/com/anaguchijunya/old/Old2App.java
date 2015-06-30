package com.anaguchijunya.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.service.CustomerService;

@EnableAutoConfiguration
@ComponentScan
public class Old2App implements CommandLineRunner {

	@Autowired
	CustomerService customerService;

	@Override
	public void run(String... arg0) throws Exception {
		customerService.save(new Customer(1, "Nobita", "Nobi"));
		customerService.save(new Customer(2, "Takeshi", "Goda"));
		customerService.save(new Customer(3, "Suneo", "Honekawa"));

		// print customers
		customerService.findAll().forEach(System.out::println);

	}

	public static void main(String[] args) {
		SpringApplication.run(Old2App.class, args);
	}





}
