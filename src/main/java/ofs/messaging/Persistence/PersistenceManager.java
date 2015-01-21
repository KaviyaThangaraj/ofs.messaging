package ofs.messaging.Persistence;


import java.nio.file.Paths;
import java.util.List;

import ofs.messaging.Event;
import ofs.messaging.test;
import ofs.messaging.Models.ClientRegistration;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.tesco.ofs.platform.trace.logger.OFSPlatformLogger;

public class PersistenceManager {

  public static final OFSPlatformLogger log = OFSPlatformLogger.getLogger(PersistenceManager.class);

  private PersistenceManager() {

  }

  private static Session initHibernate() {

    final Configuration configuration =
        new Configuration().configure("hibernate-ofs-derby.cfg.xml");
    log.info("Connecting hibernate to URL=" + configuration.getProperty("hibernate.connection.url")
        + " as user=" + configuration.getProperty("hibernate.connection.username"));
    // XXX: change the below deprecated method usage to the new way of consumption
    return configuration.buildSessionFactory().getCurrentSession();
  }

  public static void saveEvent(Event event) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    session.saveOrUpdate(event);
    tx.commit();

  }

  public static void saveClientRegistration(ClientRegistration clientRegistration) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    session.saveOrUpdate(clientRegistration);
    tx.commit();

  }


  public static List<Event> listEvents() {
    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    Query q = session.createQuery("from " + Event.class.getName());
    List<Event> list = q.list();
    log.debug("List of Events Query came back with " + list.size() + " results");
    for (Event e : list) {
      log.debug(e.getEventId() + "\n");
    }
    return list;
  }

  public static boolean isEventExists(String eventId) {

    log.debug("-----" + eventId + "-------");

    // /FIXME: this returns false, event though the value is in the list. debug!!
    // return listEvents().contains(eventId);

    for (Event row : listEvents()) {
      if (row.getEventId().equalsIgnoreCase(eventId)) {
        return true;
      }
    }
    return false;
  }

  public static String getExangeIdFromClientId(String clientId) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    Query q = session.createQuery("from " + ClientRegistration.class.getName());
    List<ClientRegistration> list = q.list();
    log.debug("List of Client Registration Query came back with " + list.size() + " results");
    log.debug("Incoming clientID is==>" + clientId);

    for (ClientRegistration row : list) {
      if (row.getClientRegistrationId().toString().equalsIgnoreCase(clientId)) {
        return row.getExchangeId();
      }
    }
    return null;

  }
}
