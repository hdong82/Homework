package com.leo.test.bookshelf.model;

import java.util.List;

public class BookResponse {
    private final String error;
    private final String total;
    private final List<Book> books;

    BookResponse(String error, String total, List<Book> books) {
        this.error = error;
        this.total = total;
        this.books = books;
    }

    public boolean isSuccess() {
        return error != null && error.equals("0");
    }

    public String getTotal() {
        return total;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "error='" + error + '\'' +
                ", total='" + total + '\'' +
                ", books=" + books +
                '}';
    }
}
