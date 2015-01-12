/**
 * 
 */
package ofs.messaging.Client;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.ObjectFactory;

import ofs.messaging.Client.Impl.RabbitMQConnection;

/**
 * @author ramanann
 *
 */
public class MessagingContextFactory implements InitialContextFactory {

	private static final String[] defaultConnectionFactoryNames = { "Connection", };

	private String connectionPrefix = "connection.";

	public Context getInitialContext(Hashtable environment) throws NamingException {
		// lets create a factory
		Map data = new ConcurrentHashMap();
		String[] names = getConnectionFactoryNames(environment);
		for (int i = 0; i < names.length; i++) {
			RabbitMQConnection connection = null;
			String name = names[i];

			try {
				connection = createConnectionFactory(name, environment);
			} catch (Exception e) {
				throw new NamingException("Invalid broker URL");

			}

			data.put(name, connection);
		}

		return (Context) createContext(environment, data);
	}

	protected Context createContext(Hashtable environment, Map data) {
		return (Context) new MessagingContext(environment, data);
	}

	protected RabbitMQConnection createConnectionFactory(String name, Hashtable environment)
			throws URISyntaxException {
		Hashtable temp = new Hashtable(environment);
		String prefix = connectionPrefix + name + ".";
		for (Iterator iter = environment.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			if (key.startsWith(prefix)) {
				// Rename the key...
				temp.remove(key);
				key = key.substring(prefix.length());
				temp.put(key, entry.getValue());
			}
		}
		return createConnectionFactory(temp);
	}

	protected String[] getConnectionFactoryNames(Map environment) {
		String factoryNames = (String) environment.get("connectionFactoryNames");
		if (factoryNames != null) {
			List list = new ArrayList();
			for (StringTokenizer enumeration = new StringTokenizer(factoryNames, ","); enumeration
					.hasMoreTokens();) {
				list.add(enumeration.nextToken().trim());
			}
			int size = list.size();
			if (size > 0) {
				String[] answer = new String[size];
				list.toArray(answer);
				return answer;
			}
		}
		return defaultConnectionFactoryNames;
	}

	/**
	 * Factory method to create a new connection factory from the given environment
	 */
	protected RabbitMQConnection createConnectionFactory(Hashtable environment)
			throws URISyntaxException {
		RabbitMQConnection answer = new RabbitMQConnection();
		answer.setURI(environment.get(Context.PROVIDER_URL).toString());
		Properties properties = new Properties();
		properties.putAll(environment);
		answer.setProperties(properties);
		return answer;
	}

}
