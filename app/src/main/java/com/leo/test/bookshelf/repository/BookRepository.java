package com.leo.test.bookshelf.repository;

import androidx.lifecycle.LiveData;

import com.leo.test.bookshelf.App;
import com.leo.test.bookshelf.db.BookDao;
import com.leo.test.bookshelf.db.DB;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.model.SearchResponse;
import com.leo.test.bookshelf.retrofit.ApiException;
import com.leo.test.bookshelf.retrofit.BookApi;
import com.leo.test.bookshelf.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Single;

public class BookRepository {
    private BookApi api;
    private BookDao dao;

    private static class BookRepoHolder {
        static final BookRepository instance = new BookRepository();
    }

    public static BookRepository getInstance() {
        return BookRepoHolder.instance;
    }

    private BookRepository() {
        this.api = RetrofitService.cteateService(BookApi.class);
        this.dao = DB.getDB(App.getInstance()).getBookDao();
    }

    public Single<List<Book>> getNewBookList() {
        return api.getNewBooks()
                .map(bookResponse -> {
                    if (!bookResponse.isSuccess()) {
                        throw new ApiException();
                    }
                    return bookResponse.getBooks();
                });
    }

    public Single<Book> getBookDetail(String isbn13) {
        return api.getBook(isbn13)
                .map(book -> {
                    if (!book.isSuccess()) {
                        throw new ApiException();
                    }
                    return book;
                });
    }

    public Single<SearchResponse> search(final String keyword) {
        return api.search(keyword);
    }

    public Single<SearchResponse> searchMore(String keyword, String page) {
        return api.searchMore(keyword, page);
    }

    public void add(Book book) {
        dao.insert(book);
    }

    public void delete(Book book) {
        dao.delete(book);
    }

    public void update(Book book) {
        dao.update(book);
    }

    public LiveData<List<Book>> getAll() {
        return dao.getAllHistories();
    }

    public LiveData<List<Book>> getAllBookMark() {
        return dao.getAllBookMark();
    }

    public Single<Boolean> updateBookMark(Book book, boolean isBookMark, String memo) {
        return Single.fromCallable(() -> {
            dao.updateBookMark(book.getIsbn13(), isBookMark, memo);
            return true;
        });
    }

    public Book get(String isbn13) {
        return dao.findByIsbn13(isbn13);
    }
}
