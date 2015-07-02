package com.anaguchijunya.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GetPassword {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("demo"));
	}

}
