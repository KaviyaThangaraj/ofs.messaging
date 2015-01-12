package ofs.messaging.Client;

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

/**
 * A default implementation of {@link NameParser}
 *
 */
public class NameParserImpl implements NameParser {
	public Name parse(String name) throws NamingException {
		return new CompositeName(name);
	}
}