package com.leo.test.bookshelf.vm;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.model.SearchResponse;

import java.util.List;

import timber.log.Timber;

public class SearchBookDataSource extends PageKeyedDataSource<String, Book> {
    private final SearchViewModel model;
    private final String keyword;

    SearchBookDataSource(SearchViewModel model, String keyword) {
        this.model = model;
        this.keyword = keyword;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Book> callback) {
        if (keyword == null || keyword.isEmpty()) return;

        try {
            SearchResponse response = model.request(keyword);
            callback.onResult(response.getBooks(), null, getNextPage(response));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Book> callback) {
        Timber.d("++ After params.key : %s", params.key);
        if (keyword == null || keyword.isEmpty()) return;

        try {
            SearchResponse response = model.more(keyword, params.key);
            callback.onResult(response.getBooks(), getNextPage(response));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Book> callback) {
    }

    private String getNextPage(SearchResponse response) {
        int currentPage = Integer.valueOf(response.getPage() == null ? "1" : response.getPage());
        int nextPage = currentPage;
        List<Book> bookList = response.getBooks();
        int MAX_PAGE_SIZE = 100;
        if (!bookList.isEmpty() && currentPage <= MAX_PAGE_SIZE) {
            nextPage = currentPage + 1;
        }
        Timber.d("++ bookList size : %s, currentPage : %s, nextPage : %s", bookList.size(), currentPage, nextPage);
        return nextPage > currentPage ? String.valueOf(nextPage) : null;
    }
}
