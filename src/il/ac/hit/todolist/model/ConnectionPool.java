package il.ac.hit.todolist.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class ConnectionPool {


    private static volatile ConnectionPool uniqueInstance;
    private SessionFactory factory;
    private static final Object lock = new Object();

    private ConnectionPool() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

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
