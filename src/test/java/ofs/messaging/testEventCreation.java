package ofs.messaging;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.Models.Event;
import ofs.messaging.Persistence.PersistenceManager;

public class testEventCreation {

  public testEventCreation() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) throws ConfigurationException, InterruptedException,
      ExecutionException, KeyManagementException, NoSuchAlgorithmException, IOException, NamingException, URISyntaxException {
    Event e = new Event("CREATION ");
    
    
    e = new Event("PONR");
    

    e = new Event("RELEASE");
    
    e = new Event(" DISPATCH");
    

  }

}
