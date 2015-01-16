/**
 * 
 */
package ofs.messaging.Client.Impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

	public static CouchbaseClient getInstance() throws InterruptedException, ExecutionException {

		if (couchbaseClient == null) {
			couchbaseClient = new RedundancyManager().setup();
			log.debug("client null, creating");
			return couchbaseClient;
		}

		return couchbaseClient;

	}

	public CouchbaseClient setup() throws InterruptedException, ExecutionException {
		ArrayList<URI> nodes = new ArrayList<URI>();

		// Add one or more nodes of your cluster (exchange the IP with yours)
		nodes.add(URI.create("http://127.0.0.1:8091/pools"));

		// Try to connect to the client
		CouchbaseClient client = null;
		try {
			client = new CouchbaseClient(nodes, "Messaging", "");
		} catch (Exception e) {
			log.error("Error connecting to Couchbase:", e);
			// System.exit(1);
		}

		return client;
	}

}
