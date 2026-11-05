import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private final TaskService service = new TaskService();

    @Test
    void addingATaskIncreasesTheCount() {
        assertEquals(0, service.count());
        service.add("Write report");
        service.add("Revise OOP notes");
        assertEquals(2, service.count());
    }

    @Test
    void blankTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.add("   "));
    }

    @Test
    void removingUnknownIdThrowsException() {
        service.add("Only task");
        assertThrows(IllegalArgumentException.class, () -> service.remove(99));
    }

    @Test
    void removingATaskDecreasesTheCount() {
        Task t = service.add("Temp task");
        service.remove(t.getId());
        assertEquals(0, service.count());
    }
}
