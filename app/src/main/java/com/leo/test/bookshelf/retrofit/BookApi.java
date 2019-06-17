package com.leo.test.bookshelf.retrofit;

import com.leo.test.bookshelf.consts.StringSet;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.model.BookResponse;
import com.leo.test.bookshelf.model.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApi {

    @GET("new")
    Single<BookResponse> getNewBooks();

    @GET("books/{isbn13}")
    Single<Book> getBook(@Path(StringSet.isbn13) String isbn13);

    @GET("search/{query}")
    Single<SearchResponse> search(@Path(StringSet.query) String query);

    @GET("search/{query}/{page}")
    Single<SearchResponse> searchMore(@Path(StringSet.query) String query, @Path(StringSet.page) String page);
}
