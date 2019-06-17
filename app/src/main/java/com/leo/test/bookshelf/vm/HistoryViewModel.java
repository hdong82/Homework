package com.leo.test.bookshelf.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.repository.BookRepository;
import com.leo.test.bookshelf.widget.WaitingDialog;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HistoryViewModel extends BaseViewModel {

    private final BookRepository bookRepo = BookRepository.getInstance();

    public HistoryViewModel(DialogProvider provider) {
        super(provider);
    }

    public LiveData<List<Book>> getHistoryList() {
        return bookRepo.getAll();
    }

    public void removeHistory(Book book) {
        addDisposable(Completable.fromCallable((Callable<Boolean>) () -> {
            bookRepo.delete(book);
            return true;
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss).subscribe());
    }
}
