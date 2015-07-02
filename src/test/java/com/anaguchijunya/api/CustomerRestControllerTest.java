package com.anaguchijunya.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.anaguchijunya.App;
import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.repository.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0", 
	"spring.dataSource.url:jdbc:h2:mem:bookmark;DB_CLOSE_ON_EXIT=FALSE"})
public class CustomerRestControllerTest {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Value("${local.server.port}")
	int port;
	
	String apiEndPoint;
	RestTemplate restTemplate = new TestRestTemplate();
	Customer customer1;
	Customer customer2;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Page<T> {
		private List<T> content;
		private int numberOfElements;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// データの投入
		customerRepository.deleteAll();
		customer1 = new Customer();
		customer1.setFirstName("Taro");
		customer1.setLastName("Yamada");
		
		customer2 = new Customer();
		customer2.setFirstName("Ichiro");
		customer2.setLastName("Suzuki");
		
		customerRepository.save(Arrays.asList(customer1, customer2));
		
		// apiのエンドポイントを作成
		apiEndPoint = "http://localhost:" + port + "/api/customers";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCustomers() {
		ResponseEntity<Page<Customer>> response = restTemplate.exchange(
				apiEndPoint,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Page<Customer>>() {
				});
		
		// HTTPステータスと要素の数
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().getNumberOfElements(), is(2));
		
		// 内容
		Customer c1 = response.getBody().getContent().get(0);
		assertThat(c1.getId(), is(customer2.getId()));
		assertThat(c1.getLastName(), is(customer2.getLastName()));
		assertThat(c1.getFirstName(), is(customer2.getFirstName()));

		Customer c2 = response.getBody().getContent().get(1);
		assertThat(c2.getId(), is(customer1.getId()));
		assertThat(c2.getLastName(), is(customer1.getLastName()));
		assertThat(c2.getFirstName(), is(customer1.getFirstName()));
	}

	@Test
	public void testPostCustomers() {
		Customer customer3 = new Customer();
		customer3.setFirstName("Nobita");
		customer3.setLastName("Nobi");
		
		ResponseEntity<Customer> response = restTemplate.exchange(
				apiEndPoint,
				HttpMethod.POST,
				new HttpEntity<>(customer3),
				Customer.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		
		Customer actual = response.getBody();
		assertThat(actual.getId(), is(notNullValue()));
		assertThat(actual.getFirstName(), is(customer3.getFirstName()));
		assertThat(actual.getLastName(), is(customer3.getLastName()));
		
		assertThat(
				restTemplate
					.exchange(
							apiEndPoint,
							HttpMethod.GET,
							null,
							new ParameterizedTypeReference<Page<Customer>>() {
							})
								.getBody()
									.getNumberOfElements()
										, is(3));
									
	}

	@Test
	public void testPutCustomer() {
	}

	@Test
	@Ignore
	public void testDeleteCustomer() {
		ResponseEntity<Void> response = restTemplate.exchange(
				apiEndPoint,
				HttpMethod.DELETE,
				null,
				Void.class,
				Collections.singletonMap("id", customer1.getId()));
		
		//assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
		
		assertThat(
				restTemplate
					.exchange(
							apiEndPoint,
							HttpMethod.GET,
							null,
							new ParameterizedTypeReference<Page<Customer>>() {
							})
								.getBody()
									.getNumberOfElements()
										, is(1));
	}

}
