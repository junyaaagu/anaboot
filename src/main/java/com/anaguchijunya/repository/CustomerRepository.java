package com.anaguchijunya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anaguchijunya.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	// インターフェースを作っておくとインスタンスをコンテナ（Spring)が作ってくれる
}
