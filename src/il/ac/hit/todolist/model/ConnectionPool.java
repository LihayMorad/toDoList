package il.ac.hit.todolist.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Singleton that contains an instance of factory. Since we have 2 subclasses of APIToDoListDAO ( by now !)
 * it's critical to put factory instance into separate singleton , to make sure they use the same object
 */
public class ConnectionPool {

    private static volatile ConnectionPool uniqueInstance;
    private SessionFactory factory;
    private static final Object lock = new Object();

    // Constructor
    private ConnectionPool() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    /**
     * Static method to get an instance of singleton
     */
    public static ConnectionPool getInstance() {

        if (uniqueInstance == null) {
            synchronized (lock) {
                if (uniqueInstance == null)
                    uniqueInstance = new ConnectionPool();
            }
        }

        return uniqueInstance;
    }

    public SessionFactory getFactory() {
        return factory;
    }

}
