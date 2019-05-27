package il.ac.hit.todolist.model;


//Abstract class , where we define polymorphic base for classes , instances of which we gonna store in DB

public abstract class DBObject {

   public  DBObject() {}

    public abstract String getUniqueParameter();


}
