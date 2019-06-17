package com.leo.test.bookshelf.model;

import java.util.List;

public class SearchResponse {
    private final String error;
    private final String total;
    private final String page;
    private final List<Book> books;

    SearchResponse(String error, String total, String page, List<Book> books) {
        this.error = error;
        this.total = total;
        this.page = page;
        this.books = books;
    }

    public boolean isSuccess() {
        return error != null && error.equals("0");
    }

    public String getTotal() {
        return total;
    }

    public String getPage() {
        return page;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "error='" + error + '\'' +
                ", total='" + total + '\'' +
                ", page='" + page + '\'' +
                ", books=" + books +
                '}';
    }
}
