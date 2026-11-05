package com.library;

import java.io.Serializable;

public record Book(String title, String author) implements Serializable { }
