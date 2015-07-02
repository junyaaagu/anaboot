/**
 * 
 */
package com.anaguchijunya.service;

import lombok.Data;

import org.springframework.security.core.authority.AuthorityUtils;

import com.anaguchijunya.domain.User;

/**
 * @author anaguchijunya
 *
 */
@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = 1L;
	
	private final User user;
	
	/**
	 * コンストラクタ
	 * 認可用のロールを設定する
	 * 
	 * @param user
	 */
	public LoginUserDetails(User user) {
		super(user.getUsername(), user.getEncodedPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}
	
}
