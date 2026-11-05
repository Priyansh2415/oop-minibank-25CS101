import java.util.EmptyStackException;

// generic stack backed by an array that grows when full
class ArrayStack<T> {
    private T[] items;
    private int size = 0;

    @SuppressWarnings("unchecked")
    ArrayStack(int capacity) {
        items = (T[]) new Object[capacity];
    }

    void push(T item) {
        if (size == items.length) {
            items = java.util.Arrays.copyOf(items, size * 2);   // grow
            System.out.println("   (stack grown to capacity " + items.length + ")");
        }
        items[size++] = item;
    }

    T pop() {
        if (isEmpty()) throw new EmptyStackException();         // sensible empty-case handling
        T item = items[--size];
        items[size] = null;                                     // avoid memory leak
        return item;
    }

    T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return items[size - 1];
    }

    boolean isEmpty() { return size == 0; }
    int size()        { return size; }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        ArrayStack<Integer> ints = new ArrayStack<>(2);
        System.out.println("Integer stack:");
        for (int i = 1; i <= 4; i++) { ints.push(i * 10); System.out.println("  push " + (i * 10)); }
        System.out.println("  peek = " + ints.peek() + ", size = " + ints.size());
        while (!ints.isEmpty()) System.out.println("  pop  " + ints.pop());

        ArrayStack<String> strs = new ArrayStack<>(2);
        System.out.println("\nString stack:");
        strs.push("java"); strs.push("generics");
        System.out.println("  pop  " + strs.pop());
        System.out.println("  pop  " + strs.pop());

        System.out.println("\nPopping from an empty stack:");
        try {
            strs.pop();
        } catch (EmptyStackException e) {
            System.out.println("  caught EmptyStackException - stack is empty");
        }
    }
}
