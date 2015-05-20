
public class TodoItem {

    private final String id;
    private final String user;
    private final String todo;

    public TodoItem(String id, String user, String todo) {
        this.id = id;
        this.user = user;
        this.todo = todo;
    }

    public TodoItem(String user, String todo) {
        this(null, user, todo);
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getTodo() {
        return todo;
    }
    
}
