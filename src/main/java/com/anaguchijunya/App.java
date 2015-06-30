package com.anaguchijunya;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.repository.CustomerRepository;


@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// 追加
		customerRepository.save(new Customer(null, "Hidetoshi", "Dekisugi"));

		// 全件名前順で表示する
//		customerRepository.findAllOrderByName().forEach(c -> System.out.println("名前順：" + c.toString()));

		// ページング処理(全件、カスタマイズなし）
		Pageable pagable = new PageRequest(0, 3);
		Page<Customer> page = customerRepository.findAll(pagable);
		page.getContent().forEach(System.out::println);

		// ページング処理（全件、カスタマイズ）
		Pageable pagable2 = new PageRequest(0, 3);
		List<Customer> page2 = customerRepository.findAllOrderByName(pagable2);
		page2.forEach(System.out::println);

	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
