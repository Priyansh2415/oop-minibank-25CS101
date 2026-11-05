import java.util.ArrayList;
import java.util.List;

// SERVICE layer - business rules, data kept in an ArrayList
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task add(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        Task t = new Task(nextId++, title.trim());
        tasks.add(t);
        return t;
    }

    public void remove(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("No task with id " + id);
        }
    }

    public List<Task> all() { return new ArrayList<>(tasks); }

    public int count()      { return tasks.size(); }
}
