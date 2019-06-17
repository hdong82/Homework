package com.leo.test.bookshelf.vm;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.leo.test.bookshelf.consts.StringSet;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.model.SearchResponse;
import com.leo.test.bookshelf.repository.BookRepository;
import com.leo.test.bookshelf.widget.WaitingDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchViewModel extends BaseAndroidViewModel {

    private final SharedPreferences pref;
    private final Map<String, PagedList<Book>> cacheMap = new ConcurrentHashMap<>();
    private final BookRepository bookRepo = BookRepository.getInstance();
    private final MutableLiveData<String> keyword = new MutableLiveData<>();
    private final MediatorLiveData<PagedList<Book>> pagedListLiveData = new MediatorLiveData<>();
    private final Set<String> searchHistories;

    public SearchViewModel(@NonNull Application application, DialogProvider provider) {
        super(application, provider);
        pref = getApplication().getSharedPreferences(StringSet.PREF_SEARCH_HISTORY, Context.MODE_PRIVATE);
        searchHistories = pref.getStringSet(StringSet.KEY_SEARCH_HISTORY, new HashSet<>());

        LiveData<PagedList<Book>> pagedList = Transformations.switchMap(keyword, this::createLivePagedList);
        pagedListLiveData.addSource(pagedList, books -> {
            if (!books.isEmpty()) {
                Timber.d("++ add cache");
                cacheMap.put(Objects.requireNonNull(keyword.getValue()), books);
            }
            pagedListLiveData.setValue(books);
        });
    }

    private LiveData<PagedList<Book>> createLivePagedList(String keyword) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build();
        return new LivePagedListBuilder<>(new DataSource.Factory<String, Book>() {
            @NotNull
            @Override
            public DataSource<String, Book> create() {
                return new SearchBookDataSource(SearchViewModel.this, keyword);
            }
        }, config).build();
    }

    public void search(String keyword) {
        searchHistories.add(keyword);
        PagedList<Book> cache = cacheMap.get(keyword);
        if (cache != null && cache.size() > 0) {
            Timber.d("++ cache hit");
            pagedListLiveData.setValue(cache);
        } else {
            this.keyword.setValue(keyword);
        }
    }

    public LiveData<PagedList<Book>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    SearchResponse request(String keyword) {
        return bookRepo.search(keyword)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss)
                .blockingGet();
    }

    SearchResponse more(String keyword, String page) {
        return bookRepo.searchMore(keyword, page)
                .subscribeOn(Schedulers.io())
                .blockingGet();
    }

    public List<String> getSearchHistory() {
        return new ArrayList<>(searchHistories);
    }

    @Override
    public void onCleared() {
        saveHistories();
        super.onCleared();
    }

    private void saveHistories() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(StringSet.KEY_SEARCH_HISTORY, searchHistories);
        editor.apply();
    }
}
