package com.anaguchijunya.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anaguchijunya.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	// インターフェースを作っておくとインスタンスをコンテナ（Spring)が作ってくれる.
	// 自分でカスタマイズしたい場合は以下のようにQueryアノテーションを付けてJPQLを記述する

	@Query("SELECT x FROM Customer x ORDER BY x.firstName, x.lastName")
	List<Customer> findAllOrderByName() ;

	@Query("SELECT x FROM Customer x ORDER BY x.firstName, x.lastName")
	List<Customer> findAllOrderByName(Pageable pageable) ;


}
