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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import ofs.messaging.Constants;
import ofs.messaging.testPublishingWithNewClientRegistration;

import com.couchbase.client.CouchbaseClient;


/**
 * @author ramanann
 *
 */
// /XXX: this class is similar to datastoremanager,except the bucket name. work on cleaning this up
// later
public class DatastoreManager {

  public static final Logger log = LoggerFactory.getLogger(DatastoreManager.class);
  private static CouchbaseClient couchbaseClient = null;

  /**
	 * 
	 */
  private DatastoreManager() {

  }

  public static CouchbaseClient getInstance() throws InterruptedException, ExecutionException,
      ConfigurationException {

    if (couchbaseClient == null) {
      couchbaseClient = new DatastoreManager().setup();
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
    String url =
        protocol + Constants.COLON + Constants.SEPERATOR + Constants.SEPERATOR + host
            + Constants.COLON + port + Constants.SEPERATOR + "pools";

    log.debug("URL Name: " + url);

    nodes.add(URI.create(url));

    String messagingRedundancyBucketName =
        config.getString("couchbase.messagingredundancy.bucketname");
    // Try to connect to the client
    CouchbaseClient client = null;
    try {
      // FIXME: See if pwd is required and how to do it
      // FIXME: Bucket needs to exist. can we do something around check exists and if not
      // create?
      client = new CouchbaseClient(nodes, messagingRedundancyBucketName, "");
    } catch (Exception e) {
      log.error("Error connecting to Couchbase:", e);

    }

    return client;
  }
}
