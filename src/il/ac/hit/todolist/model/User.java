package il.ac.hit.todolist.model;


public class User extends DBObject  {

    private String username;
    private String password;
    private int listID;

   /* @Transient
    private List<DBObject> taskLog;*/

    public User() {
    }

    public User(String username, String password, int listID) {
        this.setUsername(username);
        this.setPassword(password);
        this.setListID(listID);
    }

    public String getUsername() {
        return username;
    }

    //public List<DBObject> getTaskLog() { return taskLog; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    @Override
    public String getUniqueParameter(){
        return getUsername();
    }

}
