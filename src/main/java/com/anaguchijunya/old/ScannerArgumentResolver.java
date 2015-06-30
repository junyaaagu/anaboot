/**
 *
 */
package com.anaguchijunya.old;

import java.io.InputStream;
import java.util.Scanner;

import org.springframework.stereotype.Component;

/**
 * @author ts-junya.anaguchi
 *
 */
@Component
public class ScannerArgumentResolver implements ArgumentResolver {

	/* (非 Javadoc)
	 * @see com.anaguchijunya.app.ArgumentResolver#resolve(java.io.InputStream)
	 */
	@Override
	public Argument resolve(InputStream stream) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		return new Argument(a, b);
	}

}
