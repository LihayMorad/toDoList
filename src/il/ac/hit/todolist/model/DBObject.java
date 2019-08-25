package il.ac.hit.todolist.model;

import java.io.Serializable;

/**
 * Abstract class, where we define polymorphic base for classes,
 * instances of which we gonna store in DB
 */
public abstract class DBObject {

    // Constructor
    public DBObject() {
    }

    //Declaration of method for getting primary key
    public abstract Serializable getUniqueParameter();

}
