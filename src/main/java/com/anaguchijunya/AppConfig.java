package com.anaguchijunya;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class AppConfig {

	/**
	 * application.ymlに設定したプロパティを格納したクラスがインジェクトされる
	 */
	@Autowired
	DataSourceProperties dataSourceProperties;

	DataSource dataSource;

	@Bean(destroyMethod = "close")
	DataSource realDataSource() throws URISyntaxException {
		String url;
		String username;
		String password;
		
		String databaseUrl = System.getenv("DATABASE_URL");
		if (databaseUrl != null) {
			URI dburi = new URI(databaseUrl);
			url = new StringBuilder("jdbc:postgresql://")
				.append(dburi.getHost())
				.append(dburi.getPath())
				.append(":")
				.append(dburi.getPort())
				.append(dburi.getPath())
				.toString();
			username = dburi.getUserInfo().split(":")[0];
			password = dburi.getUserInfo().split(":")[1];
		} else {
			url = this.dataSourceProperties.getUrl();
			username = this.dataSourceProperties.getUsername();
			password = this.dataSourceProperties.getPassword();
		}
		
		DataSourceBuilder factory =  DataSourceBuilder
				.create(this.dataSourceProperties.getClassLoader())
				.url(url)
				.username(username)
				.password(password);
		this.dataSource = factory.build();
		return this.dataSource;
	}

	@Bean
	DataSource dataSource() {
		return new Log4jdbcProxyDataSource(this.dataSource);
	}
	
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	CharacterEncodingFilter charactoerEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
}
