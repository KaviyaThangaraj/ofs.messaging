package ofs.messaging.Persistence;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.naming.NamingException;

import ofs.messaging.testPublishingWithNewClientRegistration;
import ofs.messaging.Client.Exceptions.ClientIdDoesNotExistException;
import ofs.messaging.Client.Exceptions.ClientNotYetRegisteredException;
import ofs.messaging.Client.Helper.BrokerHelper;
import ofs.messaging.Models.ClientRegistration;
import ofs.messaging.Models.Event;
import ofs.messaging.Models.SubscriptionRegistration;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceManager {

  public static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

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

  public static void saveEvent(Event event) throws KeyManagementException, NoSuchAlgorithmException, IOException, NamingException, URISyntaxException {

   
    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    session.saveOrUpdate(event);
    tx.commit();
   
    

  }

  public static void saveClientRegistration(ClientRegistration clientRegistration) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    session.save(clientRegistration);
    tx.commit();



  }

  public static void saveSubscriptionRegistration(SubscriptionRegistration subscriptionRegistration) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    session.save(subscriptionRegistration);
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
    // /FIXME: override the equals method!. will work

    for (Event row : listEvents()) {
      if (row.getEventId().equalsIgnoreCase(eventId)) {
        return true;
      }
    }
    return false;
  }
//////////////////////////////////////////////////\
  
  public static boolean isEventNameExists(String eventName) {

	    //log.debug("-----" + eventName + "-------");

	    

	    for (Event row : listEvents()) {
	      if (row.getEventName().equalsIgnoreCase(eventName)) {
	        return true;
	      }
	    }
	    return false;
	  }

  /////////////////////////////////////////////////////////////////
  
  
  public static String getExangeIdFromClientIdAndEventId(String clientId, String eventId) {


    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientRegistrationId=? and clReg.eventId=?");
    q.setParameter(0, clientId);
    q.setParameter(1, eventId);
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

  public static String getQueueFromSubscriptionClientId(String clientId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + SubscriptionRegistration.class.getName()
            + " as subReg where subReg.clientSubscriptionId=? ");
    q.setParameter(0, clientId);
    List<SubscriptionRegistration> list = q.list();

    log.debug("Incoming clientID is==>" + clientId);

    if (list != null && list.size() > 0) {

      log.debug("List of Subscription Registration Query came back with " + list.size()
          + " results");
      // assumption is that there can be only one matching record and hence returning the first one!
      return list.get(0).getQueue().toString();

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
    session.createSQLQuery("drop table SUBSCRIPTIONREGISTRATION").executeUpdate();
    // session.createSQLQuery("drop table .NULL.EVENT").executeUpdate();
    session.createSQLQuery("drop table CLIENTREGISTRATION").executeUpdate();

    tx.commit();
  }

  public static String getRegistrationClientIdFromOtherDetails(String clientId, String businessUnit) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientName=? and clReg.businessUnit=?");
    q.setParameter(0, clientId);
    q.setParameter(1, businessUnit);

    List<ClientRegistration> list = q.list();
    if (list != null && list.size() == 1) {

      return list.get(0).getClientRegistrationId();

    }

    return null;

  }

  public static ClientRegistration getPublishingClientFromClientId(String clientId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientRegistrationId=? ");
    q.setParameter(0, clientId);
    List<ClientRegistration> list = q.list();
    if (list != null && list.size() > 0) {
      return list.get(0);
    }

    throw new ClientIdDoesNotExistException(
        "The client Id supplied does not exist. Please verify the input value");

  }

  public static SubscriptionRegistration getSubscriptionClientFromSubscpriptionId(String clientId) {

    Session session = initHibernate();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("from " + SubscriptionRegistration.class.getName()
            + " as subReg where subReg.clientSubscriptionId=? ");
    q.setParameter(0, clientId);
    List<SubscriptionRegistration> list = q.list();
    if (list != null && list.size() > 0) {
      return list.get(0);
    }

    throw new ClientIdDoesNotExistException(
        "The client Id supplied does not exist. Please verify the input value");

  }

  public static ClientRegistration getExangeIdFromOtherClientDetails(String clientId,
      String businessUnit, String eventId) {
    Session session = initHibernate();
    Transaction tx = session.beginTransaction();
    Query q =
        session.createQuery("from " + ClientRegistration.class.getName()
            + " as clReg where clReg.clientName=? and clReg.businessUnit=? and clReg.eventId=?");
    q.setParameter(0, clientId);
    q.setParameter(1, businessUnit);
    q.setParameter(2, eventId);

    List<ClientRegistration> list = q.list();
    if (list != null && list.size() == 1) {

      return list.get(0);

    }

    throw new ClientNotYetRegisteredException("Please check the client Id as this client has not "
        + "yet registered for publication of Messages");
  }
}
