package com.anaguchijunya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.domain.User;
import com.anaguchijunya.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAllOrderByName(pageable);
	}
	
	public Customer findOne(Integer id) {
		return customerRepository.findOne(id);
	}
	
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer create(Customer customer, User user) {
		customer.setUser(user);
		return customerRepository.save(customer);
	}
	
	public Customer update(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer update(Customer customer, User user) {
		customer.setUser(user);
		return customerRepository.save(customer);
	}
	
	public void delete(Integer id) {
		customerRepository.delete(id);
	}

}
