package ofs.messaging;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.couchbase.client.CouchbaseClient;

public class testCouchbase {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ArrayList<URI> nodes = new ArrayList<URI>();

		// Add one or more nodes of your cluster (exchange the IP with yours)
		nodes.add(URI.create("http://127.0.0.1:8091/pools"));

		// Try to connect to the client
		CouchbaseClient client = null;
		try {
			client = new CouchbaseClient(nodes, "default", "");
		} catch (Exception e) {
			System.err.println("Error connecting to Couchbase: " + e.getMessage());
			System.exit(1);
		}

		// Set your first document with a key of "hello" and a value of "couchbase!"
		client.set("hello", "Ramanan Natarajan!").get();

		// Return the result and cast it to string
		String result = (String) client.get("hello");
		System.out.println(result);

		// Shutdown the client
		client.shutdown();
	}

}
