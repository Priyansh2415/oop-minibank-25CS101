package com.library;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {

    @Test
    void savedDataReloadsUnchanged() throws Exception {
        LibraryService original = new LibraryService();
        original.add(new Book("Clean Code", "Robert Martin"));
        original.add(new Book("Effective Java", "Joshua Bloch"));

        Path file = Files.createTempFile("library", ".dat");
        original.save(file.toString());

        LibraryService reloaded = new LibraryService();
        reloaded.load(file.toString());
        Files.deleteIfExists(file);

        assertEquals(original.count(), reloaded.count());
        assertEquals(original.all(), reloaded.all());      // same books, same order
    }

    @Test
    void addingBookIncreasesCount() {
        LibraryService lib = new LibraryService();
        lib.add(new Book("Head First Java", "Kathy Sierra"));
        assertEquals(1, lib.count());
    }

    @Test
    void loadingMissingFileThrowsException() {
        LibraryService lib = new LibraryService();
        assertThrows(java.io.IOException.class, () -> lib.load("no-such-file.dat"));
    }
}
