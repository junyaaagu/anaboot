/**
 * 
 */
package com.anaguchijunya.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.service.CustomerService;

/**
 * @author anaguchijunya
 *
 */
@RestController
@RequestMapping("api/customers")
public class CustomerRestController {
	
	@Autowired
	CustomerService customerService;
	
	/**
	 * ページ数と１ページあたりの件数を指定して顧客を全件取得する
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	Page<Customer> getCustomers(@PageableDefault Pageable pageable) {
		Page<Customer> customers = customerService.findAll(pageable);
		return customers;
	}
	
	/**
	 * 顧客を１件取得する
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	Customer getCustomer(@PathVariable Integer id) {
		Customer customer = customerService.findOne(id);
		return customer;
	}
	
	/**
	 * 顧客を新規作成し、そのロケーションをHTTPヘッダに付加する
	 * @param customer
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Customer> postCustomers(@RequestBody Customer customer,
			UriComponentsBuilder uriBuilder) {
		// 新規作成する
		Customer createdCustomer = customerService.create(customer);
		
		// 新規作成した顧客のURLを組み立てる
		URI location = uriBuilder.path("api/customer/{id}")
				.buildAndExpand(createdCustomer.getId())
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		
		return new ResponseEntity<Customer>(createdCustomer, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 顧客を１件更新する
	 * @param id
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	Customer putCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
		customer.setId(id);
		return customerService.update(customer);
	}
	
	/**
	 * 顧客を１件削除する
	 * @param id
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteCustomer(@PathVariable Integer id) {
		customerService.delete(id);
	}
}
