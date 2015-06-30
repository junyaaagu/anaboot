package com.anaguchijunya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.repository.CustomerRepository;


@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void run(String... arg0) throws Exception {

		// 1件投入
		Customer customer = customerRepository.save(new Customer(null, "Hidetoshi", "Dekisugi"));
		System.out.println("customer=" + customer);

		// 全件表示する
		customerRepository.findAll().forEach(System.out::println);

	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
