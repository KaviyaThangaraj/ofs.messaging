package ofs.messaging.Persistence;


import java.nio.file.Paths;
import java.util.List;

import ofs.messaging.test;
import ofs.messaging.Models.ClientRegistration;
import ofs.messaging.Models.Event;
import ofs.messaging.Models.Registration;
import ofs.messaging.Models.SubscriptionRegistration;

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

  public static void saveClientRegistration(Registration clientRegistration) {


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

    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientRegistrationId=? ");
    q.setParameter(0, clientId);
    List<ClientRegistration> list = q.list();
    log.debug("List of Client Registration Query came back with "
        + (list != null ? list.size() : "list empty") + " results");
    log.debug("Incoming clientID is==>" + clientId);

    if (list != null && list.size() > 0) {

      // assumption is that there can be only one matching record and hence returning the first one!
      return list.get(0).getExchangeId();

    }
    return null;

  }

  public static boolean isAlreadyRegistered(String clientName, String businessUnit, String eventId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientName=? and clReg.businessUnit=? and clReg.eventId=?");
    q.setParameter(0, clientName);
    q.setParameter(1, businessUnit);
    q.setParameter(2, eventId);

    List<ClientRegistration> list = q.list();
    log.debug("List of Client Registration Query came back with "
        + (list != null ? list.size() : "list empty") + " results");


    if (list != null && list.size() == 1) {

      // assumption is that there can be only one matching record and hence returning the first one!
      return true;

    }
    return false;
  }

  public static boolean isAlreadySubscribed(String clientName, String businessUnit, String eventId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session
            .createQuery("from "
                + SubscriptionRegistration.class.getName()
                + " as subReg where subReg.clientName=? and subReg.businessUnit=? and subReg.eventId=?");
    q.setParameter(0, clientName);
    q.setParameter(1, businessUnit);
    q.setParameter(2, eventId);

    List<ClientRegistration> list = q.list();
    log.debug("List of subscription Registration Query came back with "
        + (list != null ? list.size() : "list empty") + " results");


    if (list != null && list.size() == 1) {

      // assumption is that there can be only one matching record and hence returning the first one!
      return true;

    }

    return false;
  }

  public static String getQueueFromClientId(String clientId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + SubscriptionRegistration.class.getName()
            + " as subReg where subReg.clientSubscriptionId=? ");
    q.setParameter(0, clientId);
    List<ClientRegistration> list = q.list();

    log.debug("Incoming clientID is==>" + clientId);

    if (list != null && list.size() > 0) {

      log.debug("List of Subscription Registration Query came back with " + list.size()
          + " results");
      // assumption is that there can be only one matching record and hence returning the first one!
      return list.get(0).getExchangeId();

    }
    log.debug("List of Subscription Registration Query came back with Empty results");
    return null;
  }

  public static void cleanUp() {
    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    // session.createQuery("delete from " +
    // SubscriptionRegistration.class.getName()).executeUpdate();
    // session.createQuery("delete from " + ClientRegistration.class.getName()).executeUpdate();
    // session.createQuery("delete from " + Registration.class.getName()).executeUpdate();
    session.createSQLQuery("drop table CLIENTREGISTRATION").executeUpdate();
    session.createSQLQuery("drop table REGISTRATION").executeUpdate();
    session.createSQLQuery("drop table SUBSCRIPTIONREGISTRATION").executeUpdate();

    tx.commit();
  }
}
