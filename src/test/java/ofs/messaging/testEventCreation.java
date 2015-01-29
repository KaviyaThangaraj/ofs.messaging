package ofs.messaging;

import java.util.concurrent.ExecutionException;

import org.apache.commons.configuration.ConfigurationException;

import ofs.messaging.Models.Event;
import ofs.messaging.Persistence.PersistenceManager;

public class testEventCreation {

  public testEventCreation() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) throws ConfigurationException, InterruptedException,
      ExecutionException {
    Event e = new Event("CREATION");
    PersistenceManager.saveEvent(e);
    e = new Event("PONR");
    PersistenceManager.saveEvent(e);

    e = new Event("RELEASE");
    PersistenceManager.saveEvent(e);
    e = new Event("DISPATCH");
    PersistenceManager.saveEvent(e);

  }

}
