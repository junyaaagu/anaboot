/**
 *
 */
package com.anaguchijunya.old;

import java.io.InputStream;

/**
 * @author ts-junya.anaguchi
 *
 */
public interface ArgumentResolver {

	Argument resolve(InputStream stream);
}
