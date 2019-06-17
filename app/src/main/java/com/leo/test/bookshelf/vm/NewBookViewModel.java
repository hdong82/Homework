package com.leo.test.bookshelf.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.repository.BookRepository;
import com.leo.test.bookshelf.widget.WaitingDialog;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewBookViewModel extends BaseViewModel {

    private final BookRepository bookRepo = BookRepository.getInstance();
    public NewBookViewModel(DialogProvider provider) {
        super(provider);
    }

    public LiveData<List<Book>> getNewBooks() {
        final MutableLiveData<List<Book>> newBooks = new MutableLiveData<>();
        addDisposable(bookRepo.getNewBookList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss)
                .subscribe(books -> {
                    Timber.d("success : %s", books);
                    newBooks.postValue(books);
                }, throwable -> {
                    Timber.e("error occur %s", throwable.getMessage());
                }));
        return newBooks;
    }
}
