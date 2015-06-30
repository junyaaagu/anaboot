package com.anaguchijunya.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anaguchijunya.domain.Customer;

@Repository
@Transactional
public class CustomerRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	SimpleJdbcInsert insert ;

	private static final RowMapper<Customer> customerRowMapper = (rs, i) -> {
		return new Customer(
				rs.getInt("id"),
				rs.getString("first_name"),
				rs.getString("last_name"));
	};

	@PostConstruct
	public void init() {
		insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
				.withTableName("customers")
				.usingGeneratedKeyColumns("id");
	}

	/**
	 * 全件検索
	 * @return
	 */
	public List<Customer> findAll() {
		List<Customer> customers = jdbcTemplate.query(
				"SELECT id, first_name, last_name FROM customers ORDER BY id",
				customerRowMapper);
		return customers;

	}

	/**
	 * 1件検索
	 * @param id
	 * @return
	 */
	public Customer findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return jdbcTemplate.queryForObject(
				"SELECT id, first_name, last_name FROM customers WHERE id = :id",
				param,
				customerRowMapper);
	}

	/**
	 * 1件保存
	 * @param customer
	 * @return
	 */
	public Customer save(Customer customer) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(customer);

		if (customer.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			customer.setId(key.intValue());
		} else {
			jdbcTemplate.update("UPDATE customers SET first_name = :firstNname, last_name = :lastName WHERE id = :id", param);
		}
		return customer;
	}

	/**
	 * 1件削除
	 * @param id
	 */
	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update("DELETE FROM customers WHERE id = :id", param);
	}

}
