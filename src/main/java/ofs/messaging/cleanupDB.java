package ofs.messaging;

import ofs.messaging.Persistence.PersistenceManager;

public class cleanupDB {

  public cleanupDB() {

  }

  public static void main(String[] args) {
    try {
      PersistenceManager.cleanUp();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
