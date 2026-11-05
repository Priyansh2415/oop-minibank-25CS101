package com.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books = new ArrayList<>();

    public void add(Book b)       { books.add(b); }
    public List<Book> all()       { return new ArrayList<>(books); }
    public int count()            { return books.size(); }

    // persistence: write the list to a file
    public void save(String file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(books);
        }
    }

    // persistence: read the list back
    @SuppressWarnings("unchecked")
    public void load(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            books = (List<Book>) in.readObject();
        }
    }
}
