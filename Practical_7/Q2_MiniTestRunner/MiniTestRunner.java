import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run { }

class CalculatorTests {
    @Run
    public void testAddition() {
        if (2 + 3 != 5) throw new AssertionError("2 + 3 should be 5");
    }

    @Run
    public void testDivision() {
        if (10 / 2 != 5) throw new AssertionError("10 / 2 should be 5");
    }

    @Run
    public void testDeliberateFailure() {
        if (1 + 1 != 3) throw new AssertionError("1 + 1 is not 3");
    }

    public void helperMethod() {            // not annotated -> must NOT run
        System.out.println("helperMethod should never be called");
    }

    public void notATest() { }              // not annotated -> must NOT run
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        Class<?> cls = CalculatorTests.class;
        Object instance = cls.getDeclaredConstructor().newInstance();

        int ran = 0, passed = 0, failed = 0;
        for (Method m : cls.getDeclaredMethods()) {
            if (!m.isAnnotationPresent(Run.class)) continue;     // only @Run methods
            ran++;
            try {
                m.invoke(instance);
                passed++;
                System.out.println("PASS  " + m.getName());
            } catch (InvocationTargetException e) {
                failed++;
                System.out.println("FAIL  " + m.getName() + " -> " + e.getCause().getMessage());
            }
        }
        System.out.println("\nMethods run: " + ran + " (passed " + passed + ", failed " + failed + ")");
    }
}
