package com.anaguchijunya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.repository.CustomerRepositoryOld;

@Service
public class CustomerService {

	@Autowired
	CustomerRepositoryOld repository;

	public Customer save(Customer customer) {
		return repository.save(customer);
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

}
