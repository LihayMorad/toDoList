package il.ac.hit.todolist.model;

//Abstract class , where we define polymorphic base for classes , instances of which we gonna store in DB

import java.io.Serializable;

public abstract class DBObject {

    public DBObject() {
    }

    //Declaration of method for getting primary key
    public abstract Serializable getUniqueParameter();

}
