/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import ofs.messaging.Constants;
import ofs.messaging.test;

import com.couchbase.client.CouchbaseClient;
import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

/**
 * @author ramanann
 *
 */
public class RedundancyManager {

	public static final OFSPlatformLogger log = OFSPlatformLogger
			.getLogger(RedundancyManager.class);
	private static CouchbaseClient couchbaseClient = null;

	/**
	 * 
	 */
	private RedundancyManager() {

	}

	public static CouchbaseClient getInstance() throws InterruptedException, ExecutionException,
			ConfigurationException {

		if (couchbaseClient == null) {
			couchbaseClient = new RedundancyManager().setup();
			log.debug("client null, creating");
			return couchbaseClient;
		}

		return couchbaseClient;

	}

	public CouchbaseClient setup() throws InterruptedException, ExecutionException,
			ConfigurationException {

		ArrayList<URI> nodes = new ArrayList<URI>();

		// Add one or more nodes of your cluster (exchange the IP with yours)
		Configuration config = new PropertiesConfiguration("datastore.properties");
		String host = config.getString("couchbase.host");
		String port = config.getString("couchbase.port");
		String protocol = config.getString("couchbase.protocol");
		String url = protocol + Constants.COLON + Constants.SEPERATOR + Constants.SEPERATOR + host
				+ Constants.COLON + port + Constants.SEPERATOR + "pools";

		log.debug("URL Name: " + url);

		nodes.add(URI.create(url));

		// Try to connect to the client
		CouchbaseClient client = null;
		try {
			// FIXME: the bucket is hardcoded. modify it as appropriate
			client = new CouchbaseClient(nodes, "Messaging", "");
		} catch (Exception e) {
			log.error("Error connecting to Couchbase:", e);

		}

		return client;
	}
}
